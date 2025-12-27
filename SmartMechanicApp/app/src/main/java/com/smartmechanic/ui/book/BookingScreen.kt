package com.smartmechanic.ui.book

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.smartmechanic.viewmodel.BookingViewModel

@Composable
fun BookingScreen(navController: NavController, viewModel: BookingViewModel) {
    val context = LocalContext.current
    var issue by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Describe your issue")
        OutlinedTextField(value = issue, onValueChange = { issue = it }, label = { Text("Issue") })
        OutlinedTextField(value = location, onValueChange = { location = it }, label = { Text("Location") })
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = {
            viewModel.bookMechanic(issue, location,
                onSuccess = {
                    Toast.makeText(context, "Booking done!", Toast.LENGTH_SHORT).show()
                    navController.navigate("home")
                },
                onFailure = {
                    Toast.makeText(context, "Error: $it", Toast.LENGTH_SHORT).show()
                }
            )
        }) {
            Text("Submit Booking")
        }
    }
}
