package com.smartmechanic.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class BookingRepository {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    fun createBooking(issue: String, location: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        val booking = hashMapOf(
            "userId" to auth.currentUser?.uid,
            "issue" to issue,
            "location" to location,
            "timestamp" to System.currentTimeMillis()
        )
        db.collection("bookings")
            .add(booking)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e -> onFailure(e.message ?: "Booking failed") }
    }
}
