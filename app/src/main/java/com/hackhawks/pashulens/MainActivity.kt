package com.hackhawks.pashulens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.runtime.*
import com.hackhawks.pashulens.MainScreen
import com.hackhawks.pashulens.auth.AuthScreen
import com.hackhawks.pashulens.onboarding.OnboardingScreen
import com.hackhawks.pashulens.splash.SplashScreen
import com.hackhawks.pashulens.ui.theme.PashuLensTheme
import kotlinx.coroutines.delay

sealed class AppState {
    object Splash : AppState()
    object Onboarding : AppState()
    object Authentication : AppState()
    object MainApp : AppState()
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PashuLensTheme {
                var appState by remember { mutableStateOf<AppState>(AppState.Splash) }

                Crossfade(
                    targetState = appState,
                    animationSpec = tween(durationMillis = 500),
                    label = "screen_crossfade"
                ) { state ->
                    when (state) {
                        is AppState.Splash -> {
                            SplashScreen()
                            LaunchedEffect(key1 = true) {
                                delay(3000L)
                                appState = AppState.Onboarding
                            }
                        }
                        is AppState.Onboarding -> {
                            OnboardingScreen(onFinished = { appState = AppState.Authentication })
                        }
                        is AppState.Authentication -> {
                            AuthScreen(onLoginSuccess = { appState = AppState.MainApp })
                        }
                        is AppState.MainApp -> {
                            MainScreen()
                        }
                    }
                }
            }
        }
    }
}

