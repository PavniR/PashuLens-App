package com.hackhawks.pashulens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hackhawks.pashulens.network.LoginRequest
import com.hackhawks.pashulens.network.RetrofitClient
import com.hackhawks.pashulens.network.SignUpRequest
import com.hackhawks.pashulens.network.VerifyRequest
import kotlinx.coroutines.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor() : ViewModel() {
    private val apiService = RetrofitClient.apiService

    fun signUpUser(name: String, phone: String, email: String?) {
        viewModelScope.launch {
            try {
                val request = SignUpRequest(name = name, phone = phone, email = email)
                apiService.signUpUser(request)
                sendOtp(phone)
            } catch (e: Exception) {
                println("❌ SIGN UP FAILURE: ${e.message}")
            }
        }
    }

    fun sendOtp(phone: String) {
        viewModelScope.launch {
            try {
                // --- NEW: Debug message ---
                println("OTP_DEBUG: Requesting OTP for phone number: $phone")
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
                // --- NEW: Debug message ---
                println("OTP_DEBUG: Verifying OTP for phone number: $phone")
                val request = VerifyRequest(phone = phone, token = otp)
                apiService.verifyOtp(request)
                println("✅ VERIFICATION SUCCESS for $phone")
                onVerificationSuccess()
            } catch (e: Exception) {
                println("❌ VERIFICATION FAILURE: ${e.message}")
            }
        }
    }
}