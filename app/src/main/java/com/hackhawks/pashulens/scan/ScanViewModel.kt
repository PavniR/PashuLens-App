package com.hackhawks.pashulens.scan

import android.net.Uri
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

// An enum to clearly identify each photo slot
enum class CaptureSlotType {
    SIDE_VIEW, FRONT_VIEW, REAR_VIEW
}

@HiltViewModel
class ScanViewModel @Inject constructor() : ViewModel() {
    // A special map that the UI can observe for changes.
    // It will store the URI (the location) of the captured image for each slot.
    val capturedImages = mutableStateMapOf<CaptureSlotType, Uri>()

    fun onImageCaptured(slot: CaptureSlotType, uri: Uri?) {
        if (uri != null) {
            capturedImages[slot] = uri
            println("✅ Image captured for $slot: $uri")
        } else {
            println("❌ Image capture failed or was cancelled for $slot.")
        }
    }
}