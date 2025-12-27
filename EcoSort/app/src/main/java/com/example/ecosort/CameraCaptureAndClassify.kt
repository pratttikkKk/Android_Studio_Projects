package com.example.ecosort

import android.graphics.Bitmap
import android.util.Log
import android.view.ViewGroup
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.example.ecosort.ml.Classifier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.ExecutorService
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun CameraCaptureAndClassify(cameraExecutor: ExecutorService) {
    val context = LocalContext.current
    var imageCapture by remember { mutableStateOf<ImageCapture?>(null) }
    var busy by remember { mutableStateOf(false) }
    var resultLabel by remember { mutableStateOf<String?>(null) }
    var resultConf by remember { mutableStateOf(0f) }
    val classifier = remember { Classifier(context) } // loads model.tflite from assets

    Box(modifier = Modifier.fillMaxSize()) {
        // Camera preview
        AndroidView(factory = { ctx ->
            val previewView = PreviewView(ctx).apply {
                layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            }
            val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)
            cameraProviderFuture.addListener({
                val cameraProvider = cameraProviderFuture.get()
                val preview = Preview.Builder().build().also { it.setSurfaceProvider(previewView.surfaceProvider) }
                val ic = ImageCapture.Builder().setTargetRotation(previewView.display.rotation).build()
                imageCapture = ic
                val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
                try {
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle((ctx as androidx.fragment.app.FragmentActivity), cameraSelector, preview, ic)
                } catch (e: Exception) {
                    Log.e("EcoSort", "Camera bind failed", e)
                }
            }, ContextCompat.getMainExecutor(ctx))
            previewView
        }, modifier = Modifier.fillMaxSize())

        // Overlay UI
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (busy) {
                CircularProgressIndicator(modifier = Modifier.size(56.dp))
                Spacer(modifier = Modifier.height(8.dp))
                Text("Classifying...")
            } else if (resultLabel != null) {
                ResultView(label = resultLabel ?: "Unknown", confidence = resultConf) {
                    resultLabel = null
                }
            } else {
                Button(onClick = {
                    val ic = imageCapture ?: return@Button
                    busy = true
                    // Capture image in memory (no file saved)
                    ic.takePicture(ContextCompat.getMainExecutor(context), object : ImageCapture.OnImageCapturedCallback() {
                        override fun onCaptureSuccess(imageProxy: ImageProxy) {
                            // convert to bitmap (in-memory)
                            val bitmap: Bitmap = imageProxy.toBitmap()
                            imageProxy.close()
                            // run inference off main thread
                            kotlinx.coroutines.CoroutineScope(Dispatchers.Main).launch {
                                val (label, conf) = withContext(Dispatchers.Default) {
                                    classifier.classify(bitmap)
                                }
                                resultLabel = label
                                resultConf = conf
                                busy = false
                            }
                        }

                        override fun onError(exception: ImageCaptureException) {
                            busy = false
                            Log.e("EcoSort", "Capture failed: ${exception.message}")
                        }
                    })
                }) {
                    Text("Capture & Classify")
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}
//package com.example.ecosort


@Composable
fun ResultView(label: String, confidence: Float, onDone: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        Text(text = "Result: $label")
        Spacer(modifier = Modifier.height(6.dp))
        Text(text = "Confidence: ${(confidence * 100).toInt()}%")
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = when (label) {
            "Organic" -> "Compost or green bin"
            "Recyclable" -> "Rinse and put in recycling"
            "NonRecyclable", "Non-Recyclable" -> "Dispose in regular waste"
            else -> "Try again with a clearer photo"
        })
        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = onDone) { Text("Done") }
    }
}
