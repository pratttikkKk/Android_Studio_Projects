package com.example.simpleform.network

import com.example.simpleform.Classes.ApiResponse
import com.example.simpleform.Classes.User
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("details")
    suspend fun sendUser(
        @Body user: User            //“Object → JSON → HTTP request.”
    ): ApiResponse                  //“HTTP response → JSON → Object.”
}
