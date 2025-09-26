package com.hackhawks.pashulens.sync

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.SyncProblem
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hackhawks.pashulens.ui.theme.DarkBlue
import com.hackhawks.pashulens.ui.theme.PashuLensTheme

private enum class SyncStatus { PENDING, FAILED }
private data class SyncItem(
    val id: String,
    val date: String,
    val size: String,
    val status: SyncStatus
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SyncDataScreen(onBackClicked: () -> Unit) {
    val itemsToSync = listOf(
        SyncItem("Animal #A1234", "2024-01-07, 2.4MB", "Pending", SyncStatus.PENDING),
        SyncItem("Animal #D3456", "2024-01-07, 3.2MB", "Failed", SyncStatus.FAILED),
        SyncItem("Animal #A1234", "2024-01-07, 2.4MB", "Pending", SyncStatus.PENDING),
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Sync Status") },
                navigationIcon = { IconButton(onClick = onBackClicked) { Icon(Icons.Default.ArrowBack, contentDescription = "Back") } }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // --- Sync Status Card ---
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.CheckCircle, contentDescription = "Connected", tint = Color.Green)
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text("Connected", fontWeight = FontWeight.Bold)
                        Text("Ready to sync data", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))

            // --- Sync Summary ---
            Text("Sync Summary", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text("${itemsToSync.size} items pending sync", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = DarkBlue)
            ) {
                Text("Sync Now")
            }
            Spacer(modifier = Modifier.height(24.dp))

            // --- Items to Sync List ---
            Text("Items to Sync", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(itemsToSync) { item ->
                    SyncListItem(item = item)
                }
            }
        }
    }
}

@Composable
private fun SyncListItem(item: SyncItem) {
    val statusColor = when(item.status) {
        SyncStatus.PENDING -> Color(0xFFFFA000) // Amber
        SyncStatus.FAILED -> Color.Red
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(Color.LightGray, RoundedCornerShape(8.dp))
                    .border(1.dp, Color.Gray, RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Pets, contentDescription = null, tint = Color.Gray)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = item.id, fontWeight = FontWeight.Bold)
                Text(text = "${item.date}, ${item.size}", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            }
            Box(
                modifier = Modifier
                    .background(statusColor.copy(alpha = 0.1f), RoundedCornerShape(20.dp))
                    .padding(horizontal = 10.dp, vertical = 4.dp)
            ) {
                Text(text = item.status.name, color = statusColor, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SyncDataScreenPreview() {
    PashuLensTheme {
        SyncDataScreen {}
    }
}