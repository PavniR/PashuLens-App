package com.hackhawks.pashulens.analysis

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.hackhawks.pashulens.ui.theme.DarkBlue
import com.hackhawks.pashulens.ui.theme.PashuLensTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalysisResultScreen(
    onBackClicked: () -> Unit,
    onAcceptClicked: () -> Unit,
    onSuggestCorrectionsClicked: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("AI Analysis Results") },
                navigationIcon = { IconButton(onClick = onBackClicked) { Icon(Icons.Default.ArrowBack, contentDescription = "Back") } },
                actions = { IconButton(onClick = { /* TODO */ }) { Icon(Icons.Default.Download, contentDescription = "Download Report") } }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(paddingValues).background(Color(0xFFF0F2F5)),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item { AnimalInfoCard() }
            item { OverallScoreCard(score = 89) }
            item { Text("Trait Analysis", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)) }
            item { TraitResultCard("Body Length", 89, "90% confidence") }
            item { TraitResultCard("Chest Width", 78, "88% confidence") }
            item { TraitResultCard("Rump Angle", 82, "85% confidence") }
            item { TraitResultCard("Body Depth", 79, "90% confidence") }
            item { TraitResultCard("Leg Quality", 88, "95% confidence") }
            item {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.padding(top = 16.dp)) {
                    // Using normal buttons but styling them with the rounded corners and borders
                    Button(onClick = onAcceptClicked, modifier = Modifier.fillMaxWidth().height(50.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)), shape = RoundedCornerShape(16.dp), border = BorderStroke(1.dp, DarkBlue.copy(alpha = 0.5f))) { // Border added
                        Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.padding(end = 8.dp))
                        Text("Accept Result", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                    }
                    OutlinedButton(onClick = onSuggestCorrectionsClicked, modifier = Modifier.fillMaxWidth().height(50.dp), border = BorderStroke(1.dp, DarkBlue.copy(alpha = 0.5f)), shape = RoundedCornerShape(16.dp)) { // Border added
                        Icon(Icons.Default.Edit, contentDescription = null, modifier = Modifier.padding(end = 8.dp))
                        Text("Suggest Corrections", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = DarkBlue)
                    }
                }
            }
        }
    }
}

@Composable
private fun OverallScoreCard(score: Int) {
    ShadowedCard(modifier = Modifier.fillMaxWidth()) {
        Column {
            Text(text = "Overall Classification Score", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(12.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                LinearProgressIndicator(progress = { score / 100f }, modifier = Modifier.weight(1f).height(12.dp).clip(RoundedCornerShape(6.dp)), color = DarkBlue, trackColor = Color(0xFFE0E0E0), strokeCap = StrokeCap.Round)
                Text(text = "$score", modifier = Modifier.padding(start = 16.dp), fontWeight = FontWeight.Bold, fontSize = 24.sp, color = DarkBlue)
            }
            Text(text = "90% confidence", style = MaterialTheme.typography.labelMedium, color = Color.Gray, modifier = Modifier.fillMaxWidth().padding(top = 8.dp), textAlign = TextAlign.End)
        }
    }
}

@Composable
private fun TraitResultCard(traitName: String, score: Int, confidenceText: String) {
    val scoreColor = if (score < 80) Color(0xFFD32F2F) else Color(0xFF388E3C)
    ShadowedCard(modifier = Modifier.fillMaxWidth()) {
        ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
            val (trait, scoreBox, confidence, progress) = createRefs()
            Text(text = traitName, modifier = Modifier.constrainAs(trait) { top.linkTo(parent.top); start.linkTo(parent.start) }, fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
            Text(text = "$score", modifier = Modifier.constrainAs(scoreBox) { top.linkTo(parent.top); end.linkTo(parent.end) }.background(scoreColor, RoundedCornerShape(8.dp)).padding(horizontal = 10.dp, vertical = 4.dp), fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.White)
            LinearProgressIndicator(progress = { score / 100f }, modifier = Modifier.constrainAs(progress) { top.linkTo(trait.bottom, 12.dp); start.linkTo(parent.start); end.linkTo(parent.end); width = androidx.constraintlayout.compose.Dimension.fillToConstraints }.height(12.dp), color = DarkBlue, trackColor = Color(0xFFE0E0E0), strokeCap = StrokeCap.Round)
            Text(text = confidenceText, modifier = Modifier.constrainAs(confidence) { top.linkTo(progress.bottom, 8.dp); end.linkTo(parent.end) }, style = MaterialTheme.typography.labelMedium, color = DarkBlue) // Changed confidence color
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AnalysisResultScreenPreview() {
    PashuLensTheme {
        AnalysisResultScreen({}, {}, {})
    }
}