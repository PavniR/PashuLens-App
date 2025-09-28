package com.hackhawks.pashulens.scan

import android.Manifest
import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import coil.compose.AsyncImage
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.hackhawks.pashulens.ui.theme.DarkBlue
import com.hackhawks.pashulens.ui.theme.PashuLensTheme
import kotlinx.coroutines.launch
import java.io.File

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun PhotoCaptureScreen(
    viewModel: ScanViewModel,
    onBackClicked: () -> Unit,
    onNextClicked: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    var currentSlot by remember { mutableStateOf(CaptureSlotType.SIDE_VIEW) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    // --- Logic for Camera ---
    val cameraPermissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)
    var tempImageUri by remember { mutableStateOf<Uri?>(null) }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            if (success) {
                viewModel.onImageCaptured(currentSlot, tempImageUri)
            }
        }
    )

    // --- Logic for Gallery ---
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            viewModel.onImageCaptured(currentSlot, uri)
        }
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ScanStepper(currentStep = 2)
            Spacer(modifier = Modifier.height(48.dp))
            Text(text = "Guided Photo Capture", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            Text(text = "Take photos from these angles", color = Color.Gray, modifier = Modifier.padding(top = 4.dp))
            Spacer(modifier = Modifier.height(32.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                CaptureSlot(
                    title = "Side View",
                    imageUri = viewModel.capturedImages[CaptureSlotType.SIDE_VIEW],
                    onCaptureClick = {
                        currentSlot = CaptureSlotType.SIDE_VIEW
                        showBottomSheet = true
                    }
                )
                CaptureSlot(
                    title = "Front View",
                    imageUri = viewModel.capturedImages[CaptureSlotType.FRONT_VIEW],
                    onCaptureClick = {
                        currentSlot = CaptureSlotType.FRONT_VIEW
                        showBottomSheet = true
                    }
                )
                CaptureSlot(
                    title = "Rear View",
                    imageUri = viewModel.capturedImages[CaptureSlotType.REAR_VIEW],
                    onCaptureClick = {
                        currentSlot = CaptureSlotType.REAR_VIEW
                        showBottomSheet = true
                    }
                )
            }

            Spacer(modifier = Modifier.weight(1f)) // Pushes content below to the bottom

            Card(
                colors = CardDefaults.cardColors(containerColor = DarkBlue.copy(alpha = 0.1f)),
                shape = MaterialTheme.shapes.medium
            ) {
                Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Lightbulb, contentDescription = "Tip", tint = DarkBlue)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Tip: Keep the animal calm and ensure good lighting for best results.",
                        style = MaterialTheme.typography.bodySmall,
                        color = DarkBlue
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedButton(onClick = onBackClicked, modifier = Modifier.weight(1f)) {
                    Text("Back")
                }
                Button(onClick = onNextClicked, modifier = Modifier.weight(1f), colors = ButtonDefaults.buttonColors(containerColor = DarkBlue)) {
                    Text("Next")
                }
            }
        }

        if (showBottomSheet) {
            ModalBottomSheet(onDismissRequest = { showBottomSheet = false }, sheetState = sheetState) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Capture Image", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick = {
                            scope.launch { sheetState.hide() }.invokeOnCompletion { showBottomSheet = false }
                            if (cameraPermissionState.status.isGranted) {
                                val uri = createImageUri(context)
                                tempImageUri = uri
                                cameraLauncher.launch(uri)
                            } else {
                                cameraPermissionState.launchPermissionRequest()
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = DarkBlue)
                    ) {
                        Icon(Icons.Default.CameraAlt, contentDescription = null, modifier = Modifier.padding(end = 8.dp))
                        Text("Take Photo")
                    }
                    OutlinedButton(onClick = {
                        scope.launch { sheetState.hide() }.invokeOnCompletion { showBottomSheet = false }
                        galleryLauncher.launch("image/*")
                    }, modifier = Modifier.fillMaxWidth()) {
                        Icon(Icons.Default.Image, contentDescription = null, modifier = Modifier.padding(end = 8.dp))
                        Text("Choose From Gallery", color = DarkBlue)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    TextButton(onClick = {
                        scope.launch { sheetState.hide() }.invokeOnCompletion { showBottomSheet = false }
                    }) {
                        Text("Cancel", color = Color.Red)
                    }
                }
            }
        }
    }
}

private fun createImageUri(context: Context): Uri {
    val imageFile = File(context.filesDir, "camera_photo_${System.currentTimeMillis()}.jpg")
    return FileProvider.getUriForFile(
        context,
        "${context.packageName}.provider",
        imageFile
    )
}

@Composable
private fun CaptureSlot(title: String, imageUri: Uri?, onCaptureClick: () -> Unit) {
    Card(
        modifier = Modifier.size(width = 100.dp, height = 140.dp),
        border = BorderStroke(1.dp, Color.LightGray),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                if (imageUri != null) {
                    AsyncImage(
                        model = imageUri,
                        contentDescription = title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.CameraAlt, contentDescription = null, tint = Color.Gray)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = title, style = MaterialTheme.typography.labelMedium)
                    }
                }
            }
            Button(onClick = onCaptureClick, modifier = Modifier.fillMaxWidth(), shape = MaterialTheme.shapes.extraSmall) {
                Text("Capture", style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}