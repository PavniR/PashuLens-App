package com.hackhawks.pashulens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.hackhawks.pashulens.animal.AddAnimalScreen
import com.hackhawks.pashulens.animal.HealthScreen
import com.hackhawks.pashulens.animal.SavedAnimalsScreen
import com.hackhawks.pashulens.animal.SubmissionSuccessScreen
import com.hackhawks.pashulens.analysis.AnalysisResultScreen
import com.hackhawks.pashulens.analysis.SuggestCorrectionsScreen
import com.hackhawks.pashulens.history.HistoryScreen
import com.hackhawks.pashulens.home.HomeScreen
import com.hackhawks.pashulens.scan.ConfirmScreen
import com.hackhawks.pashulens.scan.PhotoCaptureScreen
import com.hackhawks.pashulens.scan.ScanScreen
import com.hackhawks.pashulens.settings.SettingsScreen
import com.hackhawks.pashulens.sync.SyncDataScreen
import com.hackhawks.pashulens.ui.theme.DarkBlue

sealed class Screen(val route: String, val label: String, val icon: ImageVector) {
    object Home : Screen("home", "Home", Icons.Default.Home)
    object Scan : Screen("scan", "Scan", Icons.Filled.QrCodeScanner)
    object History : Screen("history", "History", Icons.Filled.History)
    object Settings : Screen("settings", "Settings", Icons.Default.Settings)
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            AppBottomNavigation(navController = navController)
        }
    ) { innerPadding ->
        AppNavHost(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
private fun AppBottomNavigation(navController: NavHostController) {
    val items = listOf(Screen.Home, Screen.Scan, Screen.History, Screen.Settings)
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        items.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(screen.icon, contentDescription = null) },
                label = { Text(screen.label) },
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = DarkBlue,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    selectedTextColor = DarkBlue,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    indicatorColor = DarkBlue.copy(alpha = 0.1f)
                )
            )
        }
    }
}

@Composable
private fun AppNavHost(navController: NavHostController, modifier: Modifier) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(Screen.Home.route) { HomeScreen(navController = navController) }

        navigation(startDestination = "scan_step_1", route = Screen.Scan.route) {
            composable("scan_step_1") {
                ScanScreen(
                    navController = navController,
                    onStartCapture = { navController.navigate("scan_step_2") }
                )
            }
            composable("scan_step_2") { PhotoCaptureScreen(onBackClicked = { navController.popBackStack() }, onNextClicked = { navController.navigate("scan_step_3") }) }
            composable("scan_step_3") { ConfirmScreen(onBackClicked = { navController.popBackStack() }, onAnalyzeClicked = { navController.navigate("analysis_results") { popUpTo(Screen.Scan.route) } }) }
        }

        composable("add_animal") {
            AddAnimalScreen(
                onBackClicked = { navController.popBackStack() },
                onSubmitSuccess = { navController.navigate("add_animal_success") }
            )
        }

        composable("add_animal_success") {
            SubmissionSuccessScreen(
                onReturnToDashboardClicked = { navController.navigate(Screen.Home.route) { popUpTo(Screen.Home.route) { inclusive = true } } }
            )
        }

        composable("health") { HealthScreen(onBackClicked = { navController.popBackStack() }) }

        composable("sync") { SyncDataScreen(onBackClicked = { navController.popBackStack() }) }

        composable("saved_animals") { SavedAnimalsScreen(onBackClicked = { navController.popBackStack() }) }

        composable("analysis_results") {
            AnalysisResultScreen(
                onBackClicked = { navController.popBackStack() },
                onAcceptClicked = { navController.navigate("result_accepted") },
                onSuggestCorrectionsClicked = { navController.navigate("suggest_corrections") }
            )
        }

        composable("suggest_corrections") {
            SuggestCorrectionsScreen(
                onBackClicked = { navController.popBackStack() },
                onSubmitClicked = { navController.navigate("result_accepted") }
            )
        }

        composable("result_accepted") {
            SubmissionSuccessScreen(
                onReturnToDashboardClicked = { navController.navigate(Screen.Home.route) { popUpTo(Screen.Home.route) { inclusive = true } } }
            )
        }

        composable(Screen.History.route) { HistoryScreen() }

        composable(Screen.Settings.route) { SettingsScreen(navController = navController) }
    }
}