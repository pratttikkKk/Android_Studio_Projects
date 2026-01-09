package com.example.simpleform.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val retrofit = Retrofit.Builder()                   //Used to configure network behavior
        .baseUrl("http://10.0.2.2:3000/")                       //Base address of backend
        .addConverterFactory(GsonConverterFactory.create())        //Kotlin object → JSON (request) && JSON → Kotlin object (response)
        .build()

    val api: ApiService = retrofit.create(ApiService::class.java)
}
