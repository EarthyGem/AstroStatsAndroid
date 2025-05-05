package app.lilaverse.astrostatsandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.*
import app.lilaverse.astrostatsandroid.model.Chart
import app.lilaverse.astrostatsandroid.ui.theme.AstroStatsAndroidTheme
import androidx.navigation.navArgument


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AstroStatsAndroidTheme {
                val navController = rememberNavController()
                val chartList = remember { mutableStateListOf<Chart>() }

                NavHost(navController = navController, startDestination = "main") {
                    // Main chart list screen
                    composable("main") {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colorScheme.background
                        ) {
                            AstrologyChartListScreen(
                                charts = chartList,
                                onAddChartClicked = {
                                    navController.navigate("addChart")
                                },
                                onChartSelected = { selectedChart ->
                                    navController.navigate("chartDetail/${selectedChart.name}")
                                }
                            )
                        }
                    }

                    // Add chart screen
                    composable("addChart") {
                        ChartInputScreen(
                            onSaveComplete = { chart ->
                                chartList.add(chart)
                                navController.popBackStack()
                            },
                            onCancel = {
                                navController.popBackStack()
                            }
                        )
                    }

                    // Chart detail screen
                    composable(
                        route = "chartDetail/{chartName}",
                        arguments = listOf(navArgument("chartName") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val chartName = backStackEntry.arguments?.getString("chartName")
                        val chart = chartList.find { it.name == chartName }
                        if (chart != null) {
                            ChartTabsScreen(chart = chart, navController = navController)

                        }
                    }
                }
            }
        }
    }
}
