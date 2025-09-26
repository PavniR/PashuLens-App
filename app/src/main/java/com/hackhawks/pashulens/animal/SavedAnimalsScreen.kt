package com.hackhawks.pashulens.animal

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hackhawks.pashulens.R
import com.hackhawks.pashulens.ui.theme.PashuLensTheme

// --- CHANGED: Added imageRes to hold the drawable ID ---
private data class SavedAnimal(
    val id: String,
    val breed: String,
    val gender: String,
    val lastScanned: String,
    val imageRes: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavedAnimalsScreen(onBackClicked: () -> Unit) {
    // --- CHANGED: Added imageRes for each animal ---
    // IMPORTANT: Replace these with your actual file names from the 'drawable' folder
    val savedAnimals = listOf(
        SavedAnimal("Cow #A1234", "Holstein Friesian", "Female", "Scanned: Today, 2:30 PM", R.drawable.cow_reference),
        SavedAnimal("Cow #B9284", "Gir", "Female", "Scanned: Yesterday, 1:20 PM", R.drawable.cow_reference), // Example
        SavedAnimal("Buffalo #D1864", "Murrah", "Male", "Scanned: Today, 1:33 PM", R.drawable.buffalo) // Example
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Saved Animals") },
                navigationIcon = { IconButton(onClick = onBackClicked) { Icon(Icons.Default.ArrowBack, contentDescription = "Back") } }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(savedAnimals) { animal ->
                SavedAnimalListItem(animal = animal)
            }
        }
    }
}

@Composable
private fun SavedAnimalListItem(animal: SavedAnimal) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            // --- CHANGED: Replaced the Box and Icon with an Image ---
            Image(
                painter = painterResource(id = animal.imageRes),
                contentDescription = animal.breed,
                contentScale = ContentScale.Crop, // This ensures the image fills the box
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = animal.id, fontWeight = FontWeight.Bold)
                Text(text = "${animal.breed} • ${animal.gender}", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                Text(text = animal.lastScanned, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SavedAnimalsScreenPreview() {
    PashuLensTheme {
        SavedAnimalsScreen {}
    }
}