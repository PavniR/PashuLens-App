package com.hackhawks.pashulens.splash

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hackhawks.pashulens.R
import com.hackhawks.pashulens.ui.theme.DarkBlue // Import your DarkBlue color
import com.hackhawks.pashulens.ui.theme.PashuLensTheme
import kotlinx.coroutines.delay

@Composable
fun SplashScreen() {
    // State for animation
    var startAnimation by remember { mutableStateOf(false) }
    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 1500)
    )
    val scaleAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0.8f,
        animationSpec = tween(durationMillis = 1500)
    )

    LaunchedEffect(key1 = true) {
        startAnimation = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        // Combined Background and Logo Image
        Image(
            painter = painterResource(id = R.drawable.logo_pashulens), // Your combined image
            contentDescription = "PashuLens Logo Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // "PashuLens" Text (animated, now blue and bigger, closer to logo)
        Text(
            text = "PashuLens",
            modifier = Modifier
                .alpha(alphaAnim.value)
                .scale(scaleAnim.value)
                .offset(y = 100.dp), // <-- Adjusted offset to bring it closer
            color = DarkBlue,
            fontSize = 58.sp, // <-- Made text even bigger
            fontWeight = FontWeight.Black,
            style = MaterialTheme.typography.headlineLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    PashuLensTheme {
        SplashScreen()
    }
}