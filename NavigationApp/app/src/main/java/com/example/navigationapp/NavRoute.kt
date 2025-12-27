package com.example.navigationapp

sealed class NavRoute(val path: String) {

    object Home: NavRoute("home")

    object Profile: NavRoute("profile"){
        val id="id"
        val showDetails = "showDetails"
    }

    object Settings: NavRoute("settings")
}