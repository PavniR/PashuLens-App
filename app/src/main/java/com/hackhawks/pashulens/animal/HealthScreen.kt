package com.hackhawks.pashulens.animal

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hackhawks.pashulens.ui.theme.DarkBlue
import com.hackhawks.pashulens.ui.theme.PashuLensTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HealthScreen(onBackClicked: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Health & Vaccination") },
                navigationIcon = {
                    IconButton(onClick = onBackClicked) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFF0F2F5)),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // --- Summary Cards ---
            item {
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    SummaryCard("Healthy Animals", "25", "80% of total livestock", Icons.Default.Cloud, Color(0xFF4CAF50), Modifier.weight(1f))
                    SummaryCard("Up-to-date Vaccines", "20", "73% vaccination coverage", Icons.Default.Shield, Color(0xFF2196F3), Modifier.weight(1f))
                }
            }
            item {
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    SummaryCard("Need Attention", "5", "Require monitoring", Icons.Default.Warning, Color(0xFFF44336), Modifier.weight(1f))
                    SummaryCard("Overdue Vaccines", "2", "Need immediate attention", Icons.Default.NotificationsActive, DarkBlue, Modifier.weight(1f))
                }
            }

            // --- Animal Health Details ---
            item {
                AnimalHealthCard(
                    animalName = "Daisy",
                    animalId = "ID: COW-001",
                    status = "Healthy",
                    statusColor = Color(0xFF4CAF50),
                    healthScore = 85,
                    vaccineStatus = "Up-to-date",
                    vaccineStatusIcon = Icons.Default.Edit,
                    nextVaccine = "Next: FMD vaccine in 45 days",
                    vaccineChips = listOf(
                        VaccineChipInfo("FMD - Completed (Aug 10)", Color.Gray),
                        VaccineChipInfo("Anthrax - Completed (Jul 15)", Color.Gray)
                    )
                )
            }
            item {
                AnimalHealthCard(
                    animalName = "Daisy",
                    animalId = "ID: COW-001",
                    status = "Need Attention",
                    statusColor = Color(0xFFFFA000), // Amber
                    healthScore = 68,
                    vaccineStatus = "Due Soon",
                    vaccineStatusIcon = Icons.Default.Edit,
                    nextVaccine = "Next: FMD vaccine in 45 days",
                    vaccineChips = listOf(
                        VaccineChipInfo("FMD - Due in 5 days", Color(0xFFFFA000)),
                        VaccineChipInfo("Anthrax - Completed (Jul 20)", Color.Gray)
                    )
                )
            }
        }
    }
}

@Composable
private fun SummaryCard(title: String, count: String, subtitle: String, icon: ImageVector, borderColor: Color, modifier: Modifier = Modifier) {
    Card(
        // --- CHANGED: Added a fixed height to make all cards uniform ---
        modifier = modifier.height(120.dp),
        colors = CardDefaults.cardColors(
            // --- CHANGED: Background is now a light tint of the border color ---
            containerColor = borderColor.copy(alpha = 0.1f)
        ),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.5.dp, borderColor) // Made border slightly thinner
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                Text(text = count, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                Text(text = subtitle, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            }
            Icon(imageVector = icon, contentDescription = title, tint = borderColor, modifier = Modifier.size(24.dp))
        }
    }
}

data class VaccineChipInfo(val text: String, val color: Color)

@Composable
private fun AnimalHealthCard(
    animalName: String,
    animalId: String,
    status: String,
    statusColor: Color,
    healthScore: Int,
    vaccineStatus: String,
    vaccineStatusIcon: ImageVector,
    nextVaccine: String,
    vaccineChips: List<VaccineChipInfo>
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Color.LightGray)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // --- Top Section: Name and Status (Unchanged) ---
            Row(verticalAlignment = Alignment.Top) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = animalName, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    Text(text = animalId, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                }
                Box(
                    modifier = Modifier
                        .background(statusColor.copy(alpha = 0.1f), RoundedCornerShape(20.dp))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(text = status, color = statusColor, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }

            // --- CHANGED: Middle Section Layout ---
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Column for Trait Names (Left)
                Column(modifier = Modifier.weight(1f)) {
                    Text("Health Score", style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Body Length", style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Chest Girth", style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Rump Angle", style = MaterialTheme.typography.bodyMedium)
                }
                // Column for Progress Indicator (Center)
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(
                            progress = { healthScore / 100f },
                            modifier = Modifier.size(60.dp),
                            color = statusColor,
                            strokeWidth = 6.dp,
                            trackColor = Color.LightGray.copy(alpha = 0.5f)
                        )
                    }
                }
                // Column for Scores/Statuses (Right)
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.End
                ) {
                    Text("$healthScore/100", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Normal", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold, color = Color.Gray)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Normal", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold, color = Color.Gray)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Normal", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold, color = if (status == "Healthy") Color.Gray else statusColor)
                }
            }

            // --- Bottom Section: Vaccine Status (Unchanged) ---
            Divider()
            Column(modifier = Modifier.padding(top = 12.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(vaccineStatusIcon, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(vaccineStatus, fontWeight = FontWeight.SemiBold, style = MaterialTheme.typography.bodyMedium)
                }
                Text(nextVaccine, color = Color.Gray, style = MaterialTheme.typography.bodySmall, modifier = Modifier.padding(start = 24.dp))
                Spacer(modifier = Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(start = 24.dp)) {
                    vaccineChips.forEach { chip ->
                        VaccineChip(text = chip.text, color = chip.color)
                    }
                }
            }
        }
    }
}

@Composable
private fun TraitScoreRow(trait: String, status: String, statusColor: Color = Color.Gray) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp)) {
        Text(text = trait, modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodyMedium)
        Text(text = status, color = statusColor, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
private fun VaccineChip(text: String, color: Color) {
    Box(
        modifier = Modifier
            .border(BorderStroke(1.dp, color), RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(text = text, color = color, fontSize = 10.sp, fontWeight = FontWeight.SemiBold)
    }
}


@Preview(showBackground = true)
@Composable
fun HealthScreenPreview() {
    PashuLensTheme {
        HealthScreen(onBackClicked = {})
    }
}