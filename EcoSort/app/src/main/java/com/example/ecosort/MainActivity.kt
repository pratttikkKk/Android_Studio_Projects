package com.example.ecosort

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.Surface
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.example.ecosort.ml.Classifier
import com.example.ecosort.ui.theme.EcoSortTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

private const val TAG = "EcoSortMain"

class MainActivity : ComponentActivity() {
    private lateinit var cameraExecutor: ExecutorService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cameraExecutor = Executors.newSingleThreadExecutor()

        setContent {
            EcoSortTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    MainContent(cameraExecutor = cameraExecutor)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }
}

@Composable
fun MainContent(cameraExecutor: ExecutorService) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val scaffoldHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var hasPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted -> hasPermission = granted }
    )

    LaunchedEffect(Unit) {
        if (!hasPermission) permissionLauncher.launch(Manifest.permission.CAMERA)
    }

    Scaffold(snackbarHost = { SnackbarHost(scaffoldHostState) }) { padding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(padding)
        ) {
            if (!hasPermission) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Camera permission required to use EcoSort.")
                    Spacer(Modifier.height(12.dp))
                    Button(onClick = { permissionLauncher.launch(Manifest.permission.CAMERA) }) {
                        Text("Request Camera Permission")
                    }
                }
            } else {
                // show camera UI
                CameraPreviewAndCapture(
                    lifecycleOwnerAvailability = lifecycleOwner,
                    cameraExecutor = cameraExecutor,
                    onError = { msg ->
                        scope.launch { scaffoldHostState.showSnackbar(msg) }
                        Log.e(TAG, msg)
                    },
                    onMessage = { msg ->
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }
    }
}

@Composable
fun CameraPreviewAndCapture(
    lifecycleOwnerAvailability: androidx.lifecycle.LifecycleOwner,
    cameraExecutor: ExecutorService,
    onError: (String) -> Unit,
    onMessage: (String) -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = lifecycleOwnerAvailability
    val scope = rememberCoroutineScope()
    var imageCapture by remember { mutableStateOf<ImageCapture?>(null) }
    var busy by remember { mutableStateOf(false) }
    var showResult by remember { mutableStateOf<Pair<String, Float>?>(null) }

    // classifier state: null until loaded
    val classifierState = remember { mutableStateOf<Classifier?>(null) }

    // Load classifier lazily and safely
    LaunchedEffect(Unit) {
        try {
            // load model on background thread
            withContext(Dispatchers.Default) {
                val c = Classifier(context)
                classifierState.value = c
            }
            onMessage("Model loaded")
        } catch (e: Exception) {
            val msg = "Model load failed: ${e.message}"
            onError(msg)
            Log.e(TAG, "Model init error", e)
        }
    }

    // Ensure classifier is closed when composable disposed
    DisposableEffect(Unit) {
        onDispose {
            try {
                classifierState.value?.close()
            } catch (e: Exception) {
                Log.w(TAG, "Error closing classifier: ${e.message}")
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Camera preview
        AndroidView(factory = { ctx ->
            val previewView = PreviewView(ctx).apply {
                layoutParams = android.view.ViewGroup.LayoutParams(
                    android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                    android.view.ViewGroup.LayoutParams.MATCH_PARENT
                )
            }

            val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)
            cameraProviderFuture.addListener({
                try {
                    val cameraProvider = cameraProviderFuture.get()
                    val preview = androidx.camera.core.Preview.Builder().build().also { p ->
                        p.setSurfaceProvider(previewView.surfaceProvider)
                    }
                    val ic = ImageCapture.Builder().build()
                    imageCapture = ic
                    val cameraSelector = androidx.camera.core.CameraSelector.DEFAULT_BACK_CAMERA

                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, preview, ic)
                } catch (e: Exception) {
                    onError("Camera start failed: ${e.message}")
                    Log.e(TAG, "Camera bind failed", e)
                }
            }, ContextCompat.getMainExecutor(ctx))
            previewView
        }, modifier = Modifier.fillMaxSize())

        // Controls overlay
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (busy) {
                CircularProgressIndicator()
                Spacer(modifier = Modifier.height(8.dp))
                Text("Classifying...")
            } else if (showResult != null) {
                val (label, conf) = showResult!!
                ResultView(label = label, confidence = conf) {
                    showResult = null
                }
            } else {
                Button(onClick = {
                    val ic = imageCapture ?: run {
                        onError("Camera not ready")
                        return@Button
                    }

                    val classifier = classifierState.value
                    if (classifier == null) {
                        onError("Model not loaded yet")
                        return@Button
                    }

                    busy = true
                    ic.takePicture(ContextCompat.getMainExecutor(context), object : ImageCapture.OnImageCapturedCallback() {
                        override fun onCaptureSuccess(imageProxy: ImageProxy) {
                            val bitmap: Bitmap = try {
                                imageProxy.toBitmap()
                            } catch (e: Exception) {
                                imageProxy.close()
                                busy = false
                                onError("Image conversion failed: ${e.message}")
                                return
                            }
                            imageProxy.close()

                            scope.launch {
                                try {
                                    val (label, conf) = withContext(Dispatchers.Default) {
                                        classifier.classify(bitmap)
                                    }
                                    showResult = Pair(label, conf)
                                    onMessage("Detected: $label (${(conf * 100).toInt()}%)")
                                } catch (e: Exception) {
                                    onError("Inference failed: ${e.message}")
                                    Log.e(TAG, "Inference exception", e)
                                } finally {
                                    busy = false
                                }
                            }
                        }

                        override fun onError(exception: ImageCaptureException) {
                            busy = false
                            onError("Capture failed: ${exception.message}")
                        }
                    })
                }) {
                    Text("Capture & Classify")
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}
