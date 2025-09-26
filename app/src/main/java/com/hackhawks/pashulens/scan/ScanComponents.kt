package com.hackhawks.pashulens.scan

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hackhawks.pashulens.ui.theme.DarkBlue

@Composable
fun ScanStepper(currentStep: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        (1..3).forEach { step ->
            val isCurrent = step == currentStep

            val backgroundColor = if (isCurrent) DarkBlue else Color.Transparent
            val contentColor = if (isCurrent) Color.White else Color.Gray
            val border = if (isCurrent) null else BorderStroke(2.dp, DarkBlue.copy(alpha = 0.3f))

            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(backgroundColor)
                    .let { if (border != null) it.border(border, CircleShape) else it },
                contentAlignment = Alignment.Center
            ) {
                Text(text = "$step", color = contentColor, fontWeight = FontWeight.Bold)
            }

            if (step < 3) {
                Divider(
                    modifier = Modifier.width(48.dp),
                    color = DarkBlue.copy(alpha = 0.3f)
                )
            }
        }
    }
}