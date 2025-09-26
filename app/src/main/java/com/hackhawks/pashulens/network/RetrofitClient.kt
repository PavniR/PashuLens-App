package com.hackhawks.pashulens.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// We use an 'object' to create a singleton. This ensures there's only one
// instance of the Retrofit client in our entire app, which is very efficient.
object RetrofitClient {

    // --- IMPORTANT: YOUR SERVER'S ADDRESS ---
    // This is the root address of your FastAPI server.
    // If you are running the server on the SAME COMPUTER as the Android Emulator,
    // you must use this special IP address: http://10.0.2.2
    private const val BASE_URL = "http://10.0.2.2:8000/"

    // This builds the Retrofit instance that will be used to make API calls.
    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL) // 1. Set the server's base address
            .addConverterFactory(GsonConverterFactory.create()) // 2. Set the JSON converter
            .build()
            .create(ApiService::class.java) // 3. Create an implementation of our ApiService interface
    }
}