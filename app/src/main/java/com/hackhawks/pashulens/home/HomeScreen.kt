package com.hackhawks.pashulens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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
fun HomeScreen(navController: NavController) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFF0F2F5)),
            contentPadding = PaddingValues(bottom = 60.dp)
        ) {
            // Header
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            DarkBlue,
                            shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)
                        )
                        .padding(start = 20.dp, end = 20.dp, top = 24.dp, bottom = 54.dp)
                ) {
                    Text(text = "Good morning!", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold, color = Color.White)
                    Text(text = "Let's check your livestock today", style = MaterialTheme.typography.bodyLarge, color = Color.White.copy(alpha = 0.8f))
                    Spacer(modifier = Modifier.height(24.dp))
                    OutlinedTextField(
                        value = "",
                        onValueChange = { /*TODO*/ },
                        placeholder = { Text("Search....", color = Color.Gray) },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = Color.Gray) },
                        trailingIcon = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Tune, contentDescription = "Filter", tint = Color.Gray)
                                Spacer(modifier = Modifier.width(8.dp))
                                Icon(Icons.Default.Sort, contentDescription = "Sort", tint = Color.Gray)
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            disabledContainerColor = Color.White,
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent
                        )
                    )
                }
            }

            // Rest of the content, pulled up to overlap
            item {
                Column(
                    modifier = Modifier
                        .offset(y = (-30).dp)
                        .padding(horizontal = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Summary Boxes in a 2x2 Grid
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        // --- CHANGED: Specific colors for each SummaryCard ---
                        SummaryCard(
                            title = "Total Animals",
                            value = "25",
                            trend = "+2 this month",
                            icon = Icons.Default.Cloud,
                            cardBackgroundColor = Color(0xFFC5CAE9), // Light blue background
                            cardBorderColor = Color(0xFF3F51B5), // Darker blue border
                            contentColor = DarkBlue, // Text color
                            modifier = Modifier.weight(1f)
                        )
                        SummaryCard(
                            title = "Healthy Animals",
                            value = "20",
                            trend = "+1 this month",
                            icon = Icons.Default.Shield,
                            cardBackgroundColor = Color(0xFFC8E6C9), // Light green background
                            cardBorderColor = Color(0xFF4CAF50), // Darker green border
                            contentColor = Color(0xFF2E7D32), // Dark green text
                            modifier = Modifier.weight(1f)
                        )
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        SummaryCard(
                            title = "Unhealthy Animals",
                            value = "5",
                            trend = "+5 this month",
                            icon = Icons.Default.Warning,
                            cardBackgroundColor = Color(0xFFFFCDD2), // Light red background
                            cardBorderColor = Color(0xFFD32F2F), // Darker red border
                            contentColor = Color(0xFFB71C1C), // Dark red text
                            modifier = Modifier.weight(1f)
                        )
                        SummaryCard(
                            title = "Pending Vaccinations",
                            value = "2",
                            trend = "+2 this month",
                            icon = Icons.Default.Notifications,
                            cardBackgroundColor = Color(0xFFC5CAE9), // Light blue background
                            cardBorderColor = Color(0xFF3F51B5), // Darker blue border
                            contentColor = DarkBlue, // Text color
                            modifier = Modifier.weight(1f)
                        )
                    }

                    // Farm Operations
                    Text("Farm Operations", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 16.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        OperationButton("New Scan", "Start Guided Scanning", Icons.Default.QrCodeScanner, Modifier.weight(1f), onClick = { navController.navigate("scan") })
                        OperationButton("Add Animals", "Start Now", Icons.Default.Add, Modifier.weight(1f), onClick = { navController.navigate("add_animal") })
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        OperationButton("Health & Vaccination", "Check schedules/status", Icons.Default.HealthAndSafety, Modifier.weight(1f), onClick = { navController.navigate("health") })
                        OperationButton("Sync Data", "5 min to sync", Icons.Default.Sync, Modifier.weight(1f), onClick = { navController.navigate("sync") })
                    }

                    // Recent Activity
                    Text("Recent Activity", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 16.dp))
                    ActivityItem(icon = Icons.Default.Pets, description = "Cow #456 Scanned", time = "2hr ago", iconTint = DarkBlue)
                    ActivityItem(icon = Icons.Default.Sync, description = "Data Synced Successfully", time = "1hr ago", iconTint = Color(0xFF4CAF50))
                }
            }
        }
    }
}

// Reusable component for the 3D shadow effect
@Composable
private fun ShadowedBox(modifier: Modifier = Modifier, content: @Composable BoxScope.() -> Unit) {
    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .offset(x = 2.dp, y = 4.dp)
                .background(DarkBlue.copy(alpha = 0.8f), shape = RoundedCornerShape(16.dp))
        )
        content()
    }
}

// SummaryCard now takes specific colors for background, border, and content
@Composable
private fun SummaryCard(
    title: String,
    value: String,
    trend: String,
    icon: ImageVector,
    cardBackgroundColor: Color, // New parameter for card background
    cardBorderColor: Color,     // New parameter for card border
    contentColor: Color,        // New parameter for text/icon color
    modifier: Modifier = Modifier
) {
    ShadowedBox(modifier = modifier) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = cardBackgroundColor), // Use new background color
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, cardBorderColor) // Use new border color
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = title, style = MaterialTheme.typography.labelMedium, color = contentColor) // Use new content color
                    Icon(icon, contentDescription = title, tint = contentColor) // Use new content color
                }
                Text(text = value, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = contentColor) // Use new content color
                Text(text = trend, style = MaterialTheme.typography.bodySmall, color = contentColor.copy(alpha = 0.8f)) // Use new content color
            }
        }
    }
}

@Composable
private fun OperationButton(title: String, subtitle: String, icon: ImageVector, modifier: Modifier = Modifier, onClick: () -> Unit) {
    ShadowedBox(modifier = modifier) {
        Card(
            modifier = Modifier.fillMaxWidth().height(100.dp).clickable(onClick = onClick),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier.fillMaxSize().padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(icon, contentDescription = null, tint = DarkBlue, modifier = Modifier.size(28.dp))
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = title, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, textAlign = TextAlign.Center)
                Text(text = subtitle, fontSize = 12.sp, color = Color.Gray, maxLines = 1, textAlign = TextAlign.Center)
            }
        }
    }
}

@Composable
private fun ActivityItem(icon: ImageVector, description: String, time: String, iconTint: Color) {
    ShadowedBox {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(icon, contentDescription = null, tint = iconTint, modifier = Modifier.size(24.dp))
                Spacer(modifier = Modifier.width(12.dp))
                Text(text = description, fontWeight = FontWeight.Medium, fontSize = 16.sp, modifier = Modifier.weight(1f))
                Text(text = time, color = Color.Gray, fontSize = 14.sp)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    PashuLensTheme {
        HomeScreen(rememberNavController())
    }
}