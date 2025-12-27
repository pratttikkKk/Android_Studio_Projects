package com.smartmechanic.ui.admin

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

data class MechanicData(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val license: String = "",
    val status: String = ""
)

data class OrderData(
    val orderId: String = "",
    val userId: String = "",
    val mechanicId: String = "",
    val amount: String = "",
    val status: String = ""
)

@Composable
fun AdminDashboard(navController: NavController) {
    val db = FirebaseFirestore.getInstance()

    var pendingMechanics by remember { mutableStateOf(listOf<MechanicData>()) }
    var approvedMechanics by remember { mutableStateOf(listOf<MechanicData>()) }
    var orders by remember { mutableStateOf(listOf<OrderData>()) }

    LaunchedEffect(true) {
        try {
            // Fetch mechanics
            val mechSnap = db.collection("mechanics").get().await()
            val allMechs = mechSnap.documents.mapNotNull {
                it.toObject(MechanicData::class.java)?.copy(id = it.id)
            }
            pendingMechanics = allMechs.filter { it.status == "pending" }
            approvedMechanics = allMechs.filter { it.status == "approved" }

            // Fetch orders
            val orderSnap = db.collection("orders").get().await()
            orders = orderSnap.documents.mapNotNull {
                it.toObject(OrderData::class.java)?.copy(orderId = it.id)
            }
        } catch (e: Exception) {
            Log.e("AdminDashboard", "Error: ${e.message}")
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Admin Dashboard") },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate("login") {
                            popUpTo("AdminDashboard") { inclusive = true }
                        }
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back to Login")
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate("AdminDashboard") }) {
                        Icon(Icons.Default.AccountCircle, contentDescription = "Account")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(modifier = Modifier.padding(paddingValues).padding(16.dp)) {
            item {
                Text("🔄 Pending Mechanics", style = MaterialTheme.typography.h6)
                Spacer(Modifier.height(8.dp))
            }
            items(pendingMechanics) { mech ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    elevation = 4.dp
                ) {
                    Column(Modifier.padding(12.dp)) {
                        Text("Name: ${mech.name}")
                        Text("Email: ${mech.email}")
                        Text("License: ${mech.license}")
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Button(onClick = {
                                db.collection("mechanics").document(mech.id)
                                    .update("status", "approved")
                            }) {
                                Text("Approve")
                            }
                            Spacer(Modifier.width(8.dp))
                            Button(
                                onClick = {
                                    db.collection("mechanics").document(mech.id).delete()
                                },
                                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.error)
                            ) {
                                Text("Reject")
                            }
                        }
                    }
                }
            }

            item {
                Spacer(Modifier.height(16.dp))
                Text("✅ Approved Mechanics", style = MaterialTheme.typography.h6)
                Spacer(Modifier.height(8.dp))
            }
            items(approvedMechanics) { mech ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    elevation = 2.dp
                ) {
                    Column(Modifier.padding(12.dp)) {
                        Text("Name: ${mech.name}")
                        Text("Email: ${mech.email}")
                        Text("License: ${mech.license}")
                    }
                }
            }

            item {
                Spacer(Modifier.height(16.dp))
                Text("📦 Orders", style = MaterialTheme.typography.h6)
                Spacer(Modifier.height(8.dp))
            }
            items(orders) { order ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    elevation = 2.dp
                ) {
                    Column(Modifier.padding(12.dp)) {
                        Text("Order ID: ${order.orderId}")
                        Text("User ID: ${order.userId}")
                        Text("Mechanic ID: ${order.mechanicId}")
                        Text("Amount: ₹${order.amount}")
                        Text("Status: ${order.status}")
                    }
                }
            }
        }
    }
}
