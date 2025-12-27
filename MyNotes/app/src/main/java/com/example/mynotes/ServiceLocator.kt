package com.example.notesapp

import com.example.notesapp.data.NoteApi
import com.example.notesapp.data.NoteRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceLocator {
    // IMPORTANT: For Android emulator talking to your PC's localhost
    // use http://10.0.2.2:3000/api/
    const val BASE_URL = "http://10.0.2.2:3000/api/"

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    val api: NoteApi = retrofit.create(NoteApi::class.java)
    val repository: NoteRepository = NoteRepository(api)
}
