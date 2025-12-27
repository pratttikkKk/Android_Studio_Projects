package com.example.notesapp.data

import com.google.gson.annotations.SerializedName

data class Note(
    @SerializedName("_id") val id: String,
    val text: String,
    val createdAt: String,
    val updatedAt: String
)

data class CreateNoteRequest(val text: String)
data class UpdateNoteRequest(val text: String)
