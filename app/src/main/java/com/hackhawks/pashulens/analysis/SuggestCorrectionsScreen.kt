package com.hackhawks.pashulens.analysis

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.hackhawks.pashulens.ui.theme.DarkBlue
import com.hackhawks.pashulens.ui.theme.PashuLensTheme
import com.hackhawks.pashulens.analysis.ShadowedCard

data class TraitCorrection(
    val name: String,
    val aiScore: Int,
    var userScore: String,
    var notes: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuggestCorrectionsScreen(
    onBackClicked: () -> Unit,
    onSubmitClicked: () -> Unit
) {
    val traits = remember {
        mutableStateListOf(
            TraitCorrection("Body Length", 89, "90", ""),
            TraitCorrection("Chest Width", 78, "80", ""),
            TraitCorrection("Rump Angle", 82, "80", ""),
            TraitCorrection("Body Depth", 77, "67", ""),
            TraitCorrection("Leg Quality", 88, "67", ""),
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Suggest Corrections") },
                navigationIcon = { IconButton(onClick = onBackClicked) { Icon(Icons.Default.ArrowBack, contentDescription = "Back") } }
            )
        },
        bottomBar = {
            Surface(shadowElevation = 4.dp) {
                Row(modifier = Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    OutlinedButton(onClick = onBackClicked, modifier = Modifier.weight(1f).height(50.dp), shape = RoundedCornerShape(12.dp), border = BorderStroke(1.dp, DarkBlue.copy(alpha = 0.5f))) {
                        Text("Cancel Edits", fontWeight = FontWeight.SemiBold, color = DarkBlue)
                    }
                    Button(onClick = onSubmitClicked, modifier = Modifier.weight(1f).height(50.dp), colors = ButtonDefaults.buttonColors(containerColor = DarkBlue), shape = RoundedCornerShape(12.dp)) {
                        Text("Submit Correction", fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(paddingValues).background(Color(0xFFF0F2F5)),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Card(colors = CardDefaults.cardColors(containerColor = DarkBlue.copy(alpha = 0.1f)), shape = RoundedCornerShape(12.dp)) {
                    Row(modifier = Modifier.padding(16.dp)) {
                        Icon(Icons.Default.Info, contentDescription = "Info", tint = DarkBlue)
                        Spacer(modifier = Modifier.width(12.dp))
                        Text("Help Improve AI Accuracy. Your corrections help train our AI to provide better analysis for future scans.", color = DarkBlue, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
            item { AnimalInfoCard() }
            item { Text("Adjust Trait Scores", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp)) }
            items(traits.size) { index ->
                val trait = traits[index]
                TraitCorrectionCard(
                    trait = trait,
                    onUserScoreChange = { newScore -> traits[index] = trait.copy(userScore = newScore) },
                    onNotesChange = { newNotes -> traits[index] = trait.copy(notes = newNotes) }
                )
            }
        }
    }
}

@Composable
private fun TraitCorrectionCard(
    trait: TraitCorrection,
    onUserScoreChange: (String) -> Unit,
    onNotesChange: (String) -> Unit
) {
    val aiScoreColor = if (trait.aiScore < 80) Color(0xFFD32F2F) else Color(0xFF388E3C)

    ShadowedCard(modifier = Modifier.fillMaxWidth()) {
        ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
            val (traitName, aiScoreLabel, aiScoreBox, yourScoreLabel, yourScoreInput, notesLabel, notesInput) = createRefs()

            Text(
                text = trait.name,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.constrainAs(traitName) { top.linkTo(parent.top); start.linkTo(parent.start) }
            )
            ScoreBox(
                label = "AI:",
                score = trait.aiScore.toString(),
                color = aiScoreColor,
                modifier = Modifier.constrainAs(aiScoreBox) {
                    centerVerticallyTo(traitName)
                    end.linkTo(parent.end)
                }
            )
            Text(
                text = "Your Score:",
                modifier = Modifier.constrainAs(yourScoreLabel) {
                    top.linkTo(traitName.bottom, 24.dp)
                    start.linkTo(parent.start)
                }
            )


            OutlinedTextField(
                value = trait.userScore,
                onValueChange = onUserScoreChange,
                modifier = Modifier
                    .constrainAs(yourScoreInput) {
                        centerVerticallyTo(yourScoreLabel)
                        end.linkTo(parent.end)
                    }
                    .width(80.dp),
                textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                shape = RoundedCornerShape(8.dp)
            )
            Text(
                text = "Notes (Optional)",
                modifier = Modifier.constrainAs(notesLabel) {
                    top.linkTo(yourScoreLabel.bottom, 24.dp)
                    start.linkTo(parent.start)
                },
                fontWeight = FontWeight.SemiBold
            )
            OutlinedTextField(
                value = trait.notes,
                onValueChange = onNotesChange,
                placeholder = { Text("Explain your reasoning for this correction...") },
                modifier = Modifier
                    .constrainAs(notesInput) {
                        top.linkTo(notesLabel.bottom, 8.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }
                    .height(100.dp),
                shape = RoundedCornerShape(8.dp)
            )
        }
    }
}

@Composable
private fun ScoreBox(label: String, score: String, color: Color, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (label.isNotEmpty()) {
            Text(text = label, color = Color.Gray, modifier = Modifier.padding(end = 8.dp))
        }
        Text(
            text = score,
            modifier = Modifier
                .background(color, RoundedCornerShape(8.dp))
                .padding(horizontal = 16.dp, vertical = 6.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Color.White
        )
    }
}


@Preview(showBackground = true)
@Composable
fun SuggestCorrectionsScreenPreview() {
    PashuLensTheme {
        SuggestCorrectionsScreen({}, {})
    }
}