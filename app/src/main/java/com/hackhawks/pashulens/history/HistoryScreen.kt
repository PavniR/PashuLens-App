package com.hackhawks.pashulens.history

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hackhawks.pashulens.R
import com.hackhawks.pashulens.ui.theme.DarkBlue
import com.hackhawks.pashulens.ui.theme.PashuLensTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Scan History", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { /* TODO: Handle back navigation */ }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { /* TODO: Handle download */ }) {
                        Icon(Icons.Filled.Download, contentDescription = "Download")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFF0F2F5))
                .padding(16.dp)
        ) {
            // --- Custom Search Bar ---
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CustomSearchBar(modifier = Modifier.weight(1f))
                Button(
                    onClick = { /*TODO*/ },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = DarkBlue),
                    modifier = Modifier.size(56.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Icon(Icons.Default.FilterList, contentDescription = "Filter")
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            // --- Stat Cards ---
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                HistoryStatCard("Total Scans", "4", Modifier.weight(1f))
                HistoryStatCard("Synced", "2", Modifier.weight(1f))
                HistoryStatCard("Avg Score", "81", Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(16.dp))

            // --- History List ---
            LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                item { HistoryListItem("Animal #A1234", "Holstein Friesian • Female", "Scanned: Today, 2:30 PM", "Synced", Color(0xFF4CAF50), "82", R.drawable.cow_reference) }
                item { HistoryListItem("#B5678", "Jersey", "Scanned: Yesterday, 1:30 PM", "Pending", Color(0xFFFFA000), "98", R.drawable.cow_reference) }
                item { HistoryListItem("#D3456", "Sahiwal", "2024-01-07 • 9:20 AM", "Failed", Color(0xFFF44336), "99", R.drawable.cow_reference) }
            }
        }
    }
}

@Composable
private fun CustomSearchBar(modifier: Modifier = Modifier) {
    BasicTextField(
        value = "",
        onValueChange = {},
        modifier = modifier
            .height(56.dp)
            .background(DarkBlue, RoundedCornerShape(12.dp)),
        textStyle = TextStyle(color = Color.White, fontSize = 16.sp),
        singleLine = true,
        cursorBrush = SolidColor(Color.White),
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Search, contentDescription = null, tint = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Box {
                    if ("".isEmpty()) {
                        Text("Search by ID or Breed..", color = Color.White.copy(alpha = 0.7f))
                    }
                    innerTextField()
                }
            }
        }
    )
}

@Composable
private fun HistoryStatCard(title: String, value: String, modifier: Modifier = Modifier) {
    ShadowedBox(modifier = modifier) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = DarkBlue.copy(alpha = 0.8f)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = value, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = Color.White)
                Text(text = title, style = MaterialTheme.typography.labelMedium, color = Color.White.copy(alpha = 0.8f))
            }
        }
    }
}

@Composable
private fun HistoryListItem(
    id: String,
    breed: String,
    scanTime: String,
    status: String,
    statusColor: Color,
    score: String,
    imageRes: Int
) {
    ShadowedBox {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(12.dp)
        ) {
            Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = id,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = id, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.width(8.dp))
                        StatusChip(text = status, color = statusColor)
                    }
                    Text(text = breed, style = MaterialTheme.typography.bodySmall)
                    Text(text = scanTime, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(text = score, fontWeight = FontWeight.Bold, fontSize = 20.sp, color = DarkBlue)
                    Text(text = "Overall Score", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                    Text(text = "3 Photos", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                }
            }
        }
    }
}

@Composable
private fun StatusChip(text: String, color: Color) {
    Box(
        modifier = Modifier
            .background(color.copy(alpha = 0.1f), RoundedCornerShape(20.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(text = text, color = color, fontSize = 10.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun ShadowedBox(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .offset(x = 2.dp, y = 4.dp)
                .background(DarkBlue.copy(alpha = 0.8f), shape = RoundedCornerShape(12.dp))
        )
        content()
    }
}

@Preview(showBackground = true)
@Composable
fun HistoryScreenPreview() {
    PashuLensTheme {
        HistoryScreen()
    }
}