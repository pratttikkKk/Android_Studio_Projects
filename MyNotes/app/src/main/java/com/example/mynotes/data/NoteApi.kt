package com.example.notesapp.data

import retrofit2.http.*

interface NoteApi {
    @GET("notes")
    suspend fun getNotes(): List<Note>

    @POST("notes")
    suspend fun createNote(@Body body: CreateNoteRequest): Note

    @PUT("notes/{id}")
    suspend fun updateNote(@Path("id") id: String, @Body body: UpdateNoteRequest): Note

    @DELETE("notes/{id}")
    suspend fun deleteNote(@Path("id") id: String): retrofit2.Response<Unit>
}
