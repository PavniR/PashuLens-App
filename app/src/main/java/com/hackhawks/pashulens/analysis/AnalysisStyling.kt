package com.hackhawks.pashulens.analysis

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hackhawks.pashulens.ui.theme.DarkBlue

fun Modifier.customCardStyle(): Modifier = this
    // The main card background, border, and padding
    .background(Color.White, shape = RoundedCornerShape(16.dp))
    .border(BorderStroke(1.dp, DarkBlue), shape = RoundedCornerShape(16.dp)) // Top/Left/Right border
    .padding(16.dp)

