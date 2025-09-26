package com.hackhawks.pashulens.scan

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.hackhawks.pashulens.ui.theme.DarkBlue
import com.hackhawks.pashulens.ui.theme.PashuLensTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScanScreen(
    navController: NavController,
    onStartCapture: () -> Unit
) {
    var animalId by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { }, // Title is handled by the content now
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ScanStepper(currentStep = 1)
            Spacer(modifier = Modifier.height(48.dp))
            Text(text = "Enter Animal ID", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Text(
                text = "Type the animal identifier or start capturing photos",
                color = Color.Gray,
                modifier = Modifier.padding(top = 8.dp, start = 32.dp, end = 32.dp), // Added padding for word wrap
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(32.dp))

            // --- CHANGED: Text field is now styled ---
            OutlinedTextField(
                value = animalId,
                onValueChange = { animalId = it },
                label = { Text("Animal Id (eg. A1235)") },
                modifier = Modifier.fillMaxWidth(0.8f),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = DarkBlue.copy(alpha = 0.1f),
                    unfocusedContainerColor = DarkBlue.copy(alpha = 0.1f),
                    focusedBorderColor = DarkBlue,
                    unfocusedBorderColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.height(24.dp))
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth(0.6f)) {
                Divider(modifier = Modifier.weight(1f))
                Text("or", modifier = Modifier.padding(horizontal = 8.dp), color = Color.Gray)
                Divider(modifier = Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(24.dp))

            // --- CHANGED: Added icon to the button ---
            Button(
                onClick = onStartCapture,
                modifier = Modifier.fillMaxWidth(0.8f).height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = DarkBlue)
            ) {
                Icon(Icons.Default.CameraAlt, contentDescription = null, modifier = Modifier.padding(end = 8.dp))
                Text(text = "Start Photo Capture", fontSize = 16.sp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ScanScreenPreview() {
    PashuLensTheme {
        ScanScreen(navController = rememberNavController(), onStartCapture = {})
    }
}