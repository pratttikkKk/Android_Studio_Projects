package com.example.navigationapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.navigationapp.NavRoute
import com.example.navigationapp.Screens.HomeScreen
import com.example.navigationapp.Screens.ProfileScreen
import com.example.navigationapp.Screens.SettingsScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        addHomeScreen(navController, this) // This refers to the scoop of this function. which is nav graph builder
        addProfileScreen(navController, this)
        addSettingsScreen(navController, this)
    }
}

fun addHomeScreen(
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(
        route = NavRoute.Home.path
    ) {
        HomeScreen(
            navigateToProfile = { id, showDetails ->
                navController.navigate(
                    NavRoute.Profile.path + id + showDetails
                )
            },
            navigateToSettings = {
                navController.navigate(
                    NavRoute.Settings.path
                )
            }
        )
    }
}

fun addProfileScreen(
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(
        route = NavRoute.Profile.path + "{id}{showDetails}"
    ) {
        ProfileScreen(
            id = 0,
            showDetails = false,
            navigateTosettings = {
                navController.navigate(NavRoute.Settings.path)
            }
        )
    }
}

fun addSettingsScreen(
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(
        route = NavRoute.Settings.path
    ) {
        SettingsScreen(
            navigateToHome = {
                navController.popBackStack()
            }
        )
    }
}
