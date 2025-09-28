package com.hackhawks.pashulens.scan

import android.net.Uri
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hackhawks.pashulens.ui.theme.DarkBlue
import com.hackhawks.pashulens.ui.theme.PashuLensTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoCaptureScreen(
    onBackClicked: () -> Unit,
    onNextClicked: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

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
                    imageUri = null, // No image in static prototype
                    onCaptureClick = { showBottomSheet = true }
                )
                CaptureSlot(
                    title = "Front View",
                    imageUri = null, // No image in static prototype
                    onCaptureClick = { showBottomSheet = true }
                )
                CaptureSlot(
                    title = "Rear View",
                    imageUri = null, // No image in static prototype
                    onCaptureClick = { showBottomSheet = true }
                )
            }

            Spacer(modifier = Modifier.weight(1f))

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
                    Button(onClick = { /* Does nothing in static prototype */ }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = DarkBlue)) {
                        Icon(Icons.Default.CameraAlt, contentDescription = null, modifier = Modifier.padding(end = 8.dp))
                        Text("Take Photo")
                    }
                    OutlinedButton(onClick = { /* Does nothing in static prototype */ }, modifier = Modifier.fillMaxWidth()) {
                        Icon(Icons.Default.Image, contentDescription = null, modifier = Modifier.padding(end = 8.dp))
                        Text("Choose From Gallery", color = DarkBlue)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    TextButton(onClick = {
                        scope.launch { sheetState.hide() }.invokeOnCompletion { if (!sheetState.isVisible) { showBottomSheet = false } }
                    }) {
                        Text("Cancel", color = Color.Red)
                    }
                }
            }
        }
    }
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
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.CameraAlt, contentDescription = null, tint = Color.Gray)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = title, style = MaterialTheme.typography.labelMedium)
                }
            }
            Button(onClick = onCaptureClick, modifier = Modifier.fillMaxWidth(), shape = MaterialTheme.shapes.extraSmall) {
                Text("Capture", style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PhotoCaptureScreenPreview() {
    PashuLensTheme {
        PhotoCaptureScreen({}, {})
    }
}