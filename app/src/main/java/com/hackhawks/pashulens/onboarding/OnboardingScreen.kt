package com.hackhawks.pashulens.onboarding

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hackhawks.pashulens.R
import com.hackhawks.pashulens.ui.theme.DarkBlue
import com.hackhawks.pashulens.ui.theme.PashuLensTheme
import kotlinx.coroutines.launch

data class OnboardingItem(
    val imageRes: Int,
    val title: String,
    val description: String
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(onFinished: () -> Unit) {
    val items = listOf(
        OnboardingItem(R.drawable.onboarding_1, "Scan Cattle Easily", "Use your phone camera to capture detailed images of your livestock with guided assistance"),
        OnboardingItem(R.drawable.onboarding_2, "Get Instant Classification", "Receive accurate body measurements and trait scores powered by AI technology"),
        OnboardingItem(R.drawable.onboarding_3, "Sync with Bharat Pashudhan", "Securely sync your data with the official government livestock management system")
    )

    val pagerState = rememberPagerState { items.size }
    val coroutineScope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            OnboardingPage(item = items[page])
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 64.dp, start = 32.dp, end = 32.dp)
        ) {
            Row(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                for (i in items.indices) {
                    val width = animateDpAsState(targetValue = if (i == pagerState.currentPage) 24.dp else 8.dp, label = "")
                    Box(
                        modifier = Modifier
                            .height(8.dp)
                            .width(width.value)
                            .clip(CircleShape)
                            .background(if (i == pagerState.currentPage) DarkBlue else Color.LightGray)
                    )
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            Button(
                onClick = {
                    if (pagerState.currentPage == items.size - 1) {
                        onFinished()
                    } else {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = DarkBlue)
            ) {
                Text(
                    text = if (pagerState.currentPage == items.size - 1) "Get Started" else "Next",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        TextButton(
            onClick = onFinished,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
        ) {
            // --- CHANGED: Increased "Skip" text size ---
            Text(text = "Skip", color = Color.Gray, fontSize = 18.sp) // Was 16.sp
        }
    }
}

@Composable
private fun OnboardingPage(item: OnboardingItem) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 40.dp)
    ) {
        Image(
            painter = painterResource(id = item.imageRes),
            contentDescription = item.title,
            modifier = Modifier
                .size(300.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = item.title,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))

        // --- CHANGED: Increased description size ---
        Text(
            text = item.description,
            style = MaterialTheme.typography.titleMedium, // Was titleSmall
            textAlign = TextAlign.Center,
            color = Color.Gray,
            lineHeight = 28.sp // Adjusted line spacing
        )
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingScreenPreview() {
    PashuLensTheme {
        OnboardingScreen(onFinished = {})
    }
}
