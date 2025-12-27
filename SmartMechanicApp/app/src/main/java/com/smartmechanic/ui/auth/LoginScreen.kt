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
fun LoginScreen(navController: NavController, vm: AuthViewModel) {
    var email    by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var role     by remember { mutableStateOf("user") } // default role
    val ctx = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Welcome To Smart Mechanic Login", style = MaterialTheme.typography.h5)
        Spacer(Modifier.height(16.dp))

        // Role selector (User, Mechanic, Admin)
        Row {
            listOf("user", "mechanic", "admin").forEach { r ->
                Button(
                    onClick = { role = r },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = if (role == r) MaterialTheme.colors.primary else MaterialTheme.colors.secondary
                    ),
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Text(r.replaceFirstChar { it.uppercase() })
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        // ✅ Fixed TextField usage
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {
                if (role == "admin") {
                    if (email == "9356121442" && password == "pratik@334518") {
                        navController.navigate("admin")
                    } else {
                        Toast.makeText(ctx, "Invalid admin credentials", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    vm.login(email, password,
                        onSuccess = { r ->
                            when (r) {
                                "user" -> navController.navigate("home") {
                                    popUpTo("login") { inclusive = true }
                                }

                                "mechanic" -> navController.navigate("mechDash") {
                                    popUpTo("login") { inclusive = true }
                                }

                                else -> Toast.makeText(ctx, "Unknown role", Toast.LENGTH_SHORT).show()
                            }
                        },
                        onFailure = {
                            Toast.makeText(ctx, it, Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login as ${role.replaceFirstChar { it.uppercase() }}")
        }

        Spacer(Modifier.height(8.dp))

        // Registration links
        TextButton(onClick = { navController.navigate("regUser") }) {
            Text("Not registered as User? Sign up")
        }
        TextButton(onClick = { navController.navigate("regMech") }) {
            Text("Not registered as Mechanic? Sign up")
        }
    }
}
