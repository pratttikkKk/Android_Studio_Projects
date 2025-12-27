package com.smartmechanic.ui.auth

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.smartmechanic.viewmodel.AuthViewModel

@Composable
fun RegisterMechanicScreen(navController: NavController, vm: AuthViewModel) {
    var name    by remember { mutableStateOf("") }
    var email   by remember { mutableStateOf("") }
    var pass    by remember { mutableStateOf("") }
    var license by remember { mutableStateOf("") }
    val ctx = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Mechanic Registration", style = MaterialTheme.typography.h5)
        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Full Name") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = pass,
            onValueChange = { pass = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = license,
            onValueChange = { license = it },
            label = { Text("License #") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {
                vm.registerMechanic(name, email, pass, license,
                    onSuccess = {
                        Toast.makeText(ctx, "Mechanic Registered!", Toast.LENGTH_SHORT).show()
                        navController.popBackStack() // Go back to login
                    },
                    onFailure = {
                        Toast.makeText(ctx, it, Toast.LENGTH_SHORT).show()
                    }
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Register Mechanic")
        }
    }
}
