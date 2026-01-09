package com.example.simpleform

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.simpleform.network.RetrofitInstance
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.example.simpleform.Classes.User
import com.example.simpleform.network.ApiService
import kotlinx.coroutines.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UserScreen(api = RetrofitInstance.api)
        }
    }
}


@Composable
fun UserScreen(api: ApiService) {

    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    Column {

        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") }
        )

        TextField(
            value = age,
            onValueChange = { age = it },
            label = { Text("Age") }
        )

        Button(onClick = {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = api.sendUser(User(name, age))
                    withContext(Dispatchers.Main) {
                        result = "Hello ${response.user.username}, Age ${response.user.age}"
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        result = "Error: ${e.message}"
                    }
                }
            }
        }) {
            Text("Submit")
        }

        Text(text = result)
    }
}