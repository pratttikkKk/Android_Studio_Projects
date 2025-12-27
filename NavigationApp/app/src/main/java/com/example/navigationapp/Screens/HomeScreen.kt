package com.example.navigationapp.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HomeScreen(navigateToProfile:(Int, Boolean)-> Unit,
               navigateToSettings:()-> Unit
               )
{
    Column (modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center){

        Text(text="Welcome to Home Screen")

        Button(onClick = {navigateToProfile(77,true)}) {
            Text(text="Profile")
        }

        Button(onClick = {navigateToSettings()}) {
            Text(text="Settings")
        }
    }
}