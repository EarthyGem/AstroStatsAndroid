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
import app.lilaverse.astrostatsandroid.ui.theme.AstroStatsAndroidTheme
import app.lilaverse.astrostatsandroid.model.Chart

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AstroStatsAndroidTheme {
                val navController = rememberNavController()
                val chartList = remember { mutableStateListOf<Chart>() }


                NavHost(navController = navController, startDestination = "main") {
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
                                onChartSelected = { /* handle tap if needed */ }
                            )

                        }
                    }

                    composable("addChart") {
                        ChartInputScreen(
                            onSaveComplete = { chart ->
                                chartList.add(chart)
                                navController.popBackStack()
                            }
                            ,
                            onCancel = {
                                navController.popBackStack()
                            }
                        )

                    }
                }
            }
        }
    }
}
