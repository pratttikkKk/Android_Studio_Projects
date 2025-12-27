package com.smartmechanic.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.firebase.auth.FirebaseAuth
import com.smartmechanic.ui.auth.LoginScreen
import com.smartmechanic.ui.auth.RegisterUserScreen
import com.smartmechanic.ui.auth.RegisterMechanicScreen
import com.smartmechanic.ui.home.HomeScreen
import com.smartmechanic.ui.book.BookingScreen
import com.smartmechanic.ui.admin.AdminDashboard
import com.smartmechanic.ui.mechanic.MechanicDashboard

import com.smartmechanic.viewmodel.AuthViewModel
import com.smartmechanic.viewmodel.BookingViewModel

@Composable
fun AppNavigation(navController: NavHostController, authViewModel: AuthViewModel) {
    // Decide start screen (skip login if already logged in)
    val currentUser = FirebaseAuth.getInstance().currentUser
    val startDestination = if (currentUser != null) "home" else "login"

    NavHost(navController = navController, startDestination = startDestination) {
        // Auth Screens
        composable("login") {
            LoginScreen(navController, authViewModel)
        }
        composable("regUser") {
            RegisterUserScreen(navController, authViewModel)
        }
        composable("regMech") {
            RegisterMechanicScreen(navController, authViewModel)
        }

        // Role-based Screens
        composable("home") {
            HomeScreen(navController)
        }
        composable("mechDash") {
            MechanicDashboard(navController)
        }
        composable("admin") {
            AdminDashboard(navController)
        }

        // Booking screen
        composable("booking") {
            BookingScreen(navController, BookingViewModel())
        }
    }
}
