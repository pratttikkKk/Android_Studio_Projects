package com.example.courseapp.screens

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.courseapp.R

@Composable
fun GradientBackground(modifier: Modifier=Modifier){
    Image(
        modifier=modifier.alpha(1.0f),
        painter = painterResource(id = R.drawable.download),
        contentDescription = "background image",
        contentScale = ContentScale.FillBounds
    )
}