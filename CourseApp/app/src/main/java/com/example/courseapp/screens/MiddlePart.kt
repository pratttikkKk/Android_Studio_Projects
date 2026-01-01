package com.example.courseapp.screens

import androidx.compose.foundation.Image
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.courseapp.R

@Composable
fun Welcometext(modifier: Modifier){
    Text(text = "Welcome To Course App",
        fontSize = 32.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White,
        modifier = modifier

    )

}

@Composable
fun Questiontext(modifier: Modifier){
    Text(text = "What do you want to learn",
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold,
        fontStyle = FontStyle.Italic,
        color = Color.Cyan,
        modifier = modifier

    )

}

@Composable
fun JoinNow(modifier: Modifier){

    Button(onClick = {},
        modifier=modifier) {
        Text(text = "join now")
    }
}

@Composable
fun CourseImg(modifier: Modifier){
    Image(painter = painterResource( id=R.drawable.courselogo),
        contentDescription = "Course logo",
        modifier=modifier)

}