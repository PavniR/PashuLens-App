package com.hackhawks.pashulens.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.hackhawks.pashulens.R
import com.hackhawks.pashulens.ui.theme.PashuLensTheme

@Composable
fun SettingsScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF0F2F5))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // --- User Profile Section ---
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(12.dp)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo), // Placeholder for profile pic
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(text = "Ravi Singh", fontWeight = FontWeight.Bold)
                    Text(text = "Member since Jan 2024", fontSize = 12.sp, color = Color.Gray)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // --- Settings Menu Items ---
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            SettingsItem(icon = Icons.Outlined.Person, text = "Profile")
            SettingsItem(icon = Icons.Outlined.Language, text = "Language")
            SettingsItem(icon = Icons.Outlined.WifiOff, text = "Offline mode")
            SettingsItem(icon = Icons.Outlined.Feedback, text = "Feedback & Support")
            SettingsItem(
                icon = Icons.Outlined.BookmarkBorder,
                text = "Saved Animals",
                onClick = { navController.navigate("saved_animals") }
            )
            SettingsItem(icon = Icons.Outlined.Info, text = "About")
        }

        Spacer(modifier = Modifier.weight(1f)) // Pushes content below to the bottom

        // --- Sign Out Button ---
        OutlinedButton(
            onClick = { /* TODO: Handle Sign Out */ },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red)
        ) {
            Icon(Icons.Default.Logout, contentDescription = "Sign Out", modifier = Modifier.padding(end = 8.dp))
            Text("Sign Out")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // --- Footer ---
        Text(text = "PashuLens v1.2.0", fontSize = 12.sp, color = Color.Gray)
        Text(text = "Powered by Hackhawks • Made in India", fontSize = 12.sp, color = Color.Gray)
    }
}

// --- Reusable Component for each settings row ---
@Composable
private fun SettingsItem(
    icon: ImageVector,
    text: String,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = icon, contentDescription = text, tint = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = text, modifier = Modifier.weight(1f))
            Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Color.Gray)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    PashuLensTheme {
        // Pass a dummy NavController for the preview to work
        SettingsScreen(navController = rememberNavController())
    }
}