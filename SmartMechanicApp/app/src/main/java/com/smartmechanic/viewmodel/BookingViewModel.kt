package com.smartmechanic.viewmodel

import androidx.lifecycle.ViewModel
import com.smartmechanic.repository.BookingRepository

class BookingViewModel : ViewModel() {
    private val repo = BookingRepository()

    fun bookMechanic(issue: String, location: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        repo.createBooking(issue, location, onSuccess, onFailure)
    }
}
