package com.example.courseapp.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp


@Composable
fun TextourCourses(modifier: Modifier){
    Text(text="New Courses",
        modifier= modifier,
        fontSize = 20.sp,
        fontWeight = FontWeight.ExtraBold,
        color = Color.Black)
}