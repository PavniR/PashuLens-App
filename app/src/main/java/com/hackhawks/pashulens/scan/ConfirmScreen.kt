package com.hackhawks.pashulens.scan

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hackhawks.pashulens.ui.theme.DarkBlue
import com.hackhawks.pashulens.ui.theme.PashuLensTheme

@Composable
fun ConfirmScreen(
    onBackClicked: () -> Unit,
    onAnalyzeClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ScanStepper(currentStep = 3)
        Spacer(modifier = Modifier.height(48.dp))

        Text(text = "Confirm Images", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Text(text = "Review captured photos before analysis", color = Color.Gray, modifier = Modifier.padding(top = 4.dp))
        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ImagePreview("Side View")
            ImagePreview("Front View")
            ImagePreview("Rear View")
        }

        Spacer(modifier = Modifier.height(24.dp))
        Card(colors = CardDefaults.cardColors(containerColor = DarkBlue.copy(alpha = 0.1f))) {
            Row(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.CheckCircle, contentDescription = "Done", tint = DarkBlue)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Animal ID: A1234  •  3/3 photos captured", fontWeight = FontWeight.SemiBold, color = DarkBlue)
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedButton(onClick = onBackClicked, modifier = Modifier.weight(1f)) {
                Text("Back")
            }
            Button(onClick = onAnalyzeClicked, modifier = Modifier.weight(1f), colors = ButtonDefaults.buttonColors(containerColor = DarkBlue)) {
                Text("Analyze")
            }
        }
    }
}

@Composable
private fun ImagePreview(title: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Card(
            modifier = Modifier.size(width = 100.dp, height = 100.dp),
            border = BorderStroke(1.dp, Color.LightGray),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Icon(Icons.Default.Image, contentDescription = title, tint = Color.LightGray, modifier = Modifier.size(48.dp))
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(title, style = MaterialTheme.typography.labelMedium)
        Spacer(modifier = Modifier.height(4.dp))
        Chip(label = "Captured", color = Color.Green.copy(alpha = 0.2f))
    }
}

@Composable
private fun Chip(label: String, color: Color) {
    Box(
        modifier = Modifier.background(color, MaterialTheme.shapes.small).padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(label, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.SemiBold, color = Color.Black.copy(alpha = 0.7f))
    }
}