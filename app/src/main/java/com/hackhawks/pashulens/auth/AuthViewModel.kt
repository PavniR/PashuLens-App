package com.hackhawks.pashulens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hackhawks.pashulens.network.RetrofitClient
import com.hackhawks.pashulens.network.SignUpRequest
import kotlinx.coroutines.launch
import java.lang.Exception

class AuthViewModel : ViewModel() {

    // 1. Get a reference to our ApiService from the RetrofitClient
    private val apiService = RetrofitClient.apiService

    fun signUpUser(name: String, phone: String, email: String?) {
        viewModelScope.launch {
            // 2. Use a try/catch block to safely handle network errors
            try {
                // 3. Create the request object with the user's data
                val request = SignUpRequest(name = name, phone = phone, email = email)

                // 4. Make the actual network call
                val response = apiService.signUpUser(request)

                // 5. If the call is successful, print the server's response
                println("✅ SUCCESS: ${response.message}, User ID: ${response.userId}")

            } catch (e: Exception) {
                // 6. If the call fails, print the error message
                println("❌ FAILURE: ${e.message}")
            }
        }
    }
}