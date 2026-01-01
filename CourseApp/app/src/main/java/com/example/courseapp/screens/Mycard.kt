package com.example.courseapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.courseapp.R

@Composable
fun Mycard(modifier: Modifier){
    Card (elevation = CardDefaults.cardElevation(8.dp),
         shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
        modifier = modifier
        ){

    }

}
@Composable
fun JavaCourse(modifier: Modifier){
    Image(painter = painterResource( id=R.drawable.java),
        contentDescription = "java image",
        modifier = modifier)
}

@Composable
fun PythonCourse(modifier: Modifier){
    Image(painter = painterResource( id=R.drawable.python),
        contentDescription = "python image",
        modifier = modifier)
}

@Composable
fun AndroidCourse(modifier: Modifier){
    Image(painter = painterResource( id=R.drawable.android),
        contentDescription = "Android image",
        modifier = modifier)
}