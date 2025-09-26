package com.hackhawks.pashulens.analysis

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hackhawks.pashulens.R
import com.hackhawks.pashulens.ui.theme.DarkBlue

// A reusable component to create the 3D shadow effect
@Composable
fun ShadowedCard(modifier: Modifier = Modifier, content: @Composable ColumnScope.() -> Unit) {
    Box(modifier = modifier) {
        // The "shadow" layer, offset
        Box(
            modifier = Modifier
                .matchParentSize()
                .offset(x = 2.dp, y = 4.dp)
                .background(DarkBlue.copy(alpha = 0.8f), shape = RoundedCornerShape(16.dp))
        )
        // The main content card on top
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, DarkBlue.copy(alpha = 0.5f))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                content()
            }
        }
    }
}

// AnimalInfoCard now uses the ShadowedCard
@Composable
fun AnimalInfoCard() {
    ShadowedCard(modifier = Modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.cow_reference),
                contentDescription = "Animal Photo",
                modifier = Modifier.size(50.dp).clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "Animal #A1234", fontWeight = FontWeight.Bold)
                Text(text = "Holstein Friesian • Female", style = MaterialTheme.typography.bodySmall, color = Color.DarkGray)
                Text(text = "Scanned: Today, 2:30 PM", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            }
            Icon(Icons.Default.CheckCircle, contentDescription = "Analyzed", tint = Color(0xFF4CAF50))
        }
    }
}