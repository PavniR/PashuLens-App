package com.hackhawks.pashulens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hackhawks.pashulens.network.*
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val apiService = RetrofitClient.apiService

    fun signUpUser(name: String, phone: String, email: String?) {
        viewModelScope.launch {
            try {
                val request = SignUpRequest(name = name, phone = phone, email = email)
                apiService.signUpUser(request)
                // After successful sign up, immediately trigger the OTP send
                sendOtp(phone)
            } catch (e: Exception) {
                println("❌ SIGN UP FAILURE: ${e.message}")
            }
        }
    }

    fun sendOtp(phone: String) {
        viewModelScope.launch {
            try {
                val request = LoginRequest(phone = phone)
                apiService.loginWithOtp(request)
                println("✅ OTP SENT: Request sent for phone number $phone")
            } catch (e: Exception) {
                println("❌ OTP SEND FAILURE: ${e.message}")
            }
        }
    }

    fun verifyOtp(phone: String, otp: String, onVerificationSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                val request = VerifyRequest(phone = phone, token = otp)
                val response = apiService.verifyOtp(request)
                println("✅ VERIFICATION SUCCESS for $phone")

                // Save the token to our SessionManager
                SessionManager.authToken = response.session.accessToken
                println("TOKEN SAVED: ${SessionManager.authToken}")

                onVerificationSuccess()

            } catch (e: Exception) {
                println("❌ VERIFICATION FAILURE: ${e.message}")
            }
        }
    }
}