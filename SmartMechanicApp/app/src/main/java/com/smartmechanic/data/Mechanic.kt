package com.smartmechanic.data

data class Mechanic(
    val id: String = "",
    val name: String = "",
    val lat: Double = 0.0,
    val lng: Double = 0.0,
    val license: String = "" // ✅ Add this line!
)
