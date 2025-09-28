package com.hackhawks.pashulens.network

import com.google.gson.annotations.SerializedName
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

// --- Data class for the SIGN UP REQUEST ---
// This defines the structure of the JSON data your app will SEND.
data class SignUpRequest(
    val name: String,
    val phone: String,
    val email: String?
)

// --- Data class for the SIGN UP RESPONSE ---
// This defines the structure of the JSON data your app will RECEIVE.
data class SignUpResponse(
    val message: String,
    val userId: String
)

// --- Data class for the LOGIN REQUEST ---
data class LoginRequest(
    val phone: String
)

// --- Data class for the VERIFY OTP REQUEST ---
data class VerifyRequest(
    val phone: String,
    val token: String // The OTP
)

data class UserSession(
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("refresh_token") val refreshToken: String,
    // Add other fields from the session if you need them
)
data class VerifyResponse(
    val message: String,
    val session: UserSession
)

data class AnimalRequest(
    val tag_id: String,
    val animal_name: String,
    val species: String,
    val breed: String,
    val gender: String,
    val lactation_stage: String,
    val weight_kg: Float?,
    val age_months: Int?,
    val farm_id: String?,
    val status: String
)

// This interface lists all the API calls your app can make.
interface ApiService {

    @POST("auth/signup")
    suspend fun signUpUser(@Body request: SignUpRequest): SignUpResponse

    @POST("auth/login")
    suspend fun loginWithOtp(@Body request: LoginRequest)

    @POST("auth/verify")
    suspend fun verifyOtp(@Body request: VerifyRequest): VerifyResponse

    @POST("animals")
    suspend fun addAnimal(
        @Header("Authorization") token: String, // Sends the user's login token
        @Body animalData: AnimalRequest
    )

}