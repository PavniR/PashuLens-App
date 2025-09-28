package com.hackhawks.pashulens.animal

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hackhawks.pashulens.ui.theme.DarkBlue
import com.hackhawks.pashulens.ui.theme.PashuLensTheme

private val speciesList = listOf("Cow", "Buffalo")
private val genderList = listOf("Male", "Female")
private val lactationList = listOf("Heifer (not yet calved)", "First Lactation", "Mid-Lactation", "Late Lactation", "Dry")
private val statusList = listOf("Healthy", "Needs Attention", "Sick")
private val breedData = mapOf(
    "Cow" to listOf("Gir", "Sahiwal", "Red Sindhi", "Tharparkar", "Kankrej", "Ongole", "Hariana", "Rathi", "Deoni", "Nagori", "Holstein Friesian (HF)", "Jersey", "Brown Swiss", "Ayrshire", "Guernsey", "HF Crossbred", "Jersey Crossbred", "Other Crossbred"),
    "Buffalo" to listOf("Murrah", "Mehsana", "Nili-Ravi", "Surti", "Jaffarabadi", "Banni", "Pandharpuri", "Nagpuri")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAnimalScreen(
    viewModel: AnimalViewModel,
    onBackClicked: () -> Unit,
    onSubmitSuccess: () -> Unit
) {
    var tagId by remember { mutableStateOf("") }
    var selectedSpecies by remember { mutableStateOf("") }
    var selectedBreed by remember { mutableStateOf("") }
    var selectedLactation by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var selectedGender by remember { mutableStateOf("") }
    var farmId by remember { mutableStateOf("") }
    var selectedStatus by remember { mutableStateOf("") }
    var lastVacDate by remember { mutableStateOf("") }

    var breedOptions by remember { mutableStateOf<List<String>>(emptyList()) }

    LaunchedEffect(selectedSpecies) {
        breedOptions = breedData[selectedSpecies] ?: emptyList()
        if (selectedBreed !in breedOptions) {
            selectedBreed = ""
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Animal") },
                navigationIcon = { IconButton(onClick = onBackClicked) { Icon(Icons.Default.ArrowBack, contentDescription = "Back") } }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            OutlinedTextField(value = tagId, onValueChange = { tagId = it }, label = { Text("Tag ID / Ear Tag Number") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(16.dp))
            EditableDropdownMenu(label = "Species", items = speciesList, selectedItem = selectedSpecies, onItemSelected = { selectedSpecies = it })
            Spacer(modifier = Modifier.height(16.dp))
            EditableDropdownMenu(label = "Breed", items = breedOptions, selectedItem = selectedBreed, onItemSelected = { selectedBreed = it })
            Spacer(modifier = Modifier.height(16.dp))
            EditableDropdownMenu(label = "Lactation Stage", items = lactationList, selectedItem = selectedLactation, onItemSelected = { selectedLactation = it })
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(value = age, onValueChange = { age = it }, label = { Text("Age") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(value = weight, onValueChange = { weight = it }, label = { Text("Weight") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(16.dp))
            EditableDropdownMenu(label = "Gender", items = genderList, selectedItem = selectedGender, onItemSelected = { selectedGender = it })
            Spacer(modifier = Modifier.height(16.dp))
            PhotoUploadBox()
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(value = farmId, onValueChange = { farmId = it }, label = { Text("Farm ID") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(16.dp))
            EditableDropdownMenu(label = "Status", items = statusList, selectedItem = selectedStatus, onItemSelected = { selectedStatus = it })
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(value = lastVacDate, onValueChange = { lastVacDate = it }, label = { Text("Last Vaccination Date") }, modifier = Modifier.fillMaxWidth(), trailingIcon = { Icon(Icons.Default.CalendarToday, contentDescription = "Select Date") })
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = {
                    viewModel.addAnimal(
                        tagId = tagId,
                        animalName = "Cow", // You can update this to be a text field if needed
                        species = selectedSpecies,
                        breed = selectedBreed,
                        gender = selectedGender,
                        lactationStage = selectedLactation,
                        weightKg = weight,
                        ageMonths = age,
                        farmId = farmId,
                        status = selectedStatus,
                        onSuccess = onSubmitSuccess
                    )
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = DarkBlue)
            ) {
                Text("Submit Now")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EditableDropdownMenu(
    label: String,
    items: List<String>,
    selectedItem: String,
    onItemSelected: (String) -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = { isExpanded = it }
    ) {
        OutlinedTextField(
            value = selectedItem,
            onValueChange = onItemSelected,
            label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded) },
            modifier = Modifier.fillMaxWidth().menuAnchor()
        )
        val filteredOptions = items.filter { it.contains(selectedItem, ignoreCase = true) }
        if (filteredOptions.isNotEmpty()) {
            ExposedDropdownMenu(
                expanded = isExpanded,
                onDismissRequest = { isExpanded = false }
            ) {
                filteredOptions.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(item) },
                        onClick = {
                            onItemSelected(item)
                            isExpanded = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun PhotoUploadBox() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* TODO: Open camera/gallery */ },
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Color.LightGray),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(vertical = 24.dp, horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(Icons.Default.CloudUpload, contentDescription = null, tint = DarkBlue, modifier = Modifier.size(40.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text("Starting Reading Photo", fontWeight = FontWeight.SemiBold)
            Text("Tap to upload additional photos", fontSize = 12.sp, color = Color.Gray)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddAnimalScreenPreview() {
    PashuLensTheme {
        AddAnimalScreen(viewModel(), onBackClicked = {}, onSubmitSuccess = {})
    }
}