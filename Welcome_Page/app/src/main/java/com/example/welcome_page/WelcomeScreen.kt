package com.example.smartmechanic

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SmartMechanicWelcomeScreen(
    onEnterApp: () -> Unit
) {

    // 🔹 Animation phase
    var animationPhase by remember { mutableStateOf(0) }

    // 🔹 Coroutine scope for suspend functions
    val coroutineScope = rememberCoroutineScope()

    // 🔹 Infinite gradient animation
    val infiniteTransition = rememberInfiniteTransition(label = "gradient")

    val gradientShift by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(12000, easing = LinearEasing)
        ),
        label = "gradientShift"
    )

    // 🔹 Mechanic icon animation
    val mechanicOffset by animateDpAsState(
        targetValue = if (animationPhase == 1) 0.dp else 200.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy
        ),
        label = "mechanicOffset"
    )

    // 🔹 Drag animation state
    val dragOffset = remember { Animatable(0f) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF0F2027),
                        Color(0xFF203A43),
                        Color(0xFF2C5364)
                    ),
                    start = Offset(gradientShift, 0f),
                    end = Offset(gradientShift + 1000f, 1000f)
                )
            )
            .pointerInput(Unit) {
                detectVerticalDragGestures(
                    onVerticalDrag = { _, dragAmount ->
                        coroutineScope.launch {
                            dragOffset.snapTo(dragOffset.value + dragAmount)
                        }
                    },
                    onDragEnd = {
                        coroutineScope.launch {
                            if (dragOffset.value < -250f) {
                                onEnterApp()
                            } else {
                                dragOffset.animateTo(0f)
                            }
                        }
                    }
                )
            }
    ) {

        // 🚗 Road / Route animation
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawLine(
                color = Color.White.copy(alpha = 0.4f),
                start = Offset(size.width / 2, size.height),
                end = Offset(size.width / 2, size.height / 3),
                strokeWidth = 8f,
                pathEffect = PathEffect.dashPathEffect(
                    floatArrayOf(30f, 20f),
                    phase = gradientShift
                )
            )
        }

        // 🔧 Mechanic icon
        Icon(
            imageVector = Icons.Default.Build,
            contentDescription = "Mechanic",
            tint = Color.White,
            modifier = Modifier
                .size(96.dp)
                .align(Alignment.Center)
                .offset(y = mechanicOffset + dragOffset.value.dp)
        )

        // 🏷 App Title
        Text(
            text = "SMART MECHANIC",
            color = Color.White,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 80.dp)
        )

        // 👆 Swipe hint
        Text(
            text = "Swipe up to find a mechanic",
            color = Color.White.copy(alpha = 0.7f),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 40.dp)
        )
    }

    // 🔹 Start animation after screen loads
    LaunchedEffect(Unit) {
        delay(600)
        animationPhase = 1
    }
}
