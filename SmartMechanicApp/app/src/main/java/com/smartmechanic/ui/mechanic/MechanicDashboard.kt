package com.smartmechanic.ui.mechanic

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth

@Composable
fun MechanicDashboard(navController: NavController) {
    val context = LocalContext.current
    val mechanicName = remember { mutableStateOf("Mechanic") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mechanic Dashboard") },
                actions = {
                    TextButton(onClick = {
                        FirebaseAuth.getInstance().signOut()
                        navController.navigate("login") {
                            popUpTo("mechDash") { inclusive = true }
                        }
                    }) {
                        Text("Logout", color = MaterialTheme.colors.onPrimary)
                    }
                }
            )
        }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Welcome, ${mechanicName.value}", style = MaterialTheme.typography.h6)

            Spacer(modifier = Modifier.height(16.dp))

            Text("Today's Bookings", style = MaterialTheme.typography.subtitle1)

            LazyColumn(modifier = Modifier.fillMaxHeight()) {
                items(5) { index ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        elevation = 4.dp
                    ) {
                        Column(Modifier.padding(16.dp)) {
                            Text("Booking #${index + 1}")
                            Text("Customer Name: John Doe")
                            Text("Location: Near MG Road")
                            Text("Issue: Flat tire")
                            Button(
                                onClick = {
                                    Toast.makeText(context, "Marked as Done!", Toast.LENGTH_SHORT).show()
                                },
                                modifier = Modifier.padding(top = 8.dp)
                            ) {
                                Text("Mark Complete")
                            }
                        }
                    }
                }
            }
        }
    }
}
