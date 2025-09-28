package com.hackhawks.pashulens.animal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hackhawks.pashulens.network.AnimalRequest
import com.hackhawks.pashulens.network.RetrofitClient
import com.hackhawks.pashulens.network.SessionManager
import kotlinx.coroutines.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AnimalViewModel @Inject constructor() : ViewModel() {
    private val apiService = RetrofitClient.apiService

    fun addAnimal(
        tagId: String,
        animalName: String,
        species: String,
        breed: String,
        gender: String,
        lactationStage: String,
        weightKg: String,
        ageMonths: String,
        farmId: String,
        status: String,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            // Retrieve the saved auth token
            val token = SessionManager.authToken
            if (token == null) {
                println("❌ ADD ANIMAL ERROR: User is not logged in.")
                return@launch
            }

            // Prepare the data to be sent
            val animalData = AnimalRequest(
                tag_id = tagId,
                animal_name = animalName,
                species = species,
                breed = breed,
                gender = gender,
                lactation_stage = lactationStage,
                weight_kg = weightKg.toFloatOrNull(),
                age_months = ageMonths.toIntOrNull(),
                farm_id = farmId.ifEmpty { null },
                status = status
            )

            try {
                // Make the secure API call, adding "Bearer " prefix to the token
                apiService.addAnimal("Bearer $token", animalData)
                println("✅ ANIMAL ADDED successfully.")
                onSuccess() // Trigger navigation to success screen
            } catch (e: Exception) {
                println("❌ ADD ANIMAL FAILURE: ${e.message}")
            }
        }
    }
}