package com.hackhawks.pashulens.network

import retrofit2.http.Body
import retrofit2.http.POST

// 1. Data class for the SIGN UP REQUEST
// This defines the structure of the JSON data your app will SEND.
data class SignUpRequest(
    val name: String,
    val phone: String,
    val email: String?
)

// 2. Data class for the SIGN UP RESPONSE
// This defines the structure of the JSON data your app will RECEIVE.
data class SignUpResponse(
    val message: String,
    val userId: String
)

// 3. The API Service Interface
// This interface lists all the API calls your app can make.
interface ApiService {

    /**
     * Sends user details to the server to create a new account.
     *
     * @POST("auth/signup") - Specifies this is a POST request to the "/auth/signup" endpoint.
     * IMPORTANT: Make sure this path matches your FastAPI endpoint!
     * @Body request: SignUpRequest - Tells Retrofit to convert the SignUpRequest object into JSON
     * and send it as the request body.
     * suspend - Marks this as a function that can be paused and resumed, perfect for
     * running on a background thread with coroutines.
     */
    @POST("auth/signup")
    suspend fun signUpUser(@Body request: SignUpRequest): SignUpResponse

    // We will add more functions here later for login, fetching animals, etc.
}