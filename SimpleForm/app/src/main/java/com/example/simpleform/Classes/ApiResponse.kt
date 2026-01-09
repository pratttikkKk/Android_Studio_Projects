package com.example.simpleform.Classes

data class ApiResponse(
    val success: Boolean,
    val message: String,
    val user: User
)

