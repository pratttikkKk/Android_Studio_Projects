package com.example.navigationapp.Screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun ProfileScreen(
    id : Int,
    showDetails: Boolean,
    navigateTosettings:()->Unit
){
    Column (modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally){

        Text(text="Welcome to Profile Screen")

        Text(text="id=$id,Details=$showDetails")

        Button(onClick = navigateTosettings) {
            Text(text = "Go to settings")
        }
}}