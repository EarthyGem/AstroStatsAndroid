package app.lilaverse.astrostatsandroid

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import app.lilaverse.astrostatsandroid.orbDictionary
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import app.lilaverse.astrostatsandroid.model.Chart
import app.lilaverse.astrostatsandroid.ui.theme.AstroStatsAndroidTheme
import java.util.*

import app.lilaverse.astrostatsandroid.ChartCake
import app.lilaverse.astrostatsandroid.PlanetStrengthCalculator
import app.lilaverse.astrostatsandroid.HouseCusp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AstroStatsAndroidTheme {
                LaunchedEffect(Unit) {
                    runPlanetStrengthTest()
                }

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
                                onChartSelected = { selectedChart ->
                                    navController.navigate("chartDetail/${selectedChart.name}")
                                }
                            )
                        }
                    }

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

    private fun runPlanetStrengthTest() {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("America/Los_Angeles"))
        calendar.set(1977, Calendar.MAY, 21, 13, 57, 0)
        val birthDate = calendar.time

        val latitude = 32.7157
        val longitude = -117.1611

        val chartCake = ChartCake(
            birthDate = birthDate,
            latitude = latitude,
            longitude = longitude,
            transitDate = Date()
        )

        val bodies = chartCake.natalBodies

        val calculator = PlanetStrengthCalculator(
            orbDictionary = orbDictionary,
            houseProvider = { coord ->
                chartCake.houseCusps.houseForLongitude(coord.longitude)
            },
            luminaryChecker = { it.body.keyName == "Sun" || it.body.keyName == "Moon" },
            houseCuspsProvider = { lon ->
                val houseIndex = chartCake.houseCusps.houseForLongitude(lon)
                val cusp = chartCake.houseCusps.getCusp(houseIndex)
                HouseCusp(cusp.index, cusp.longitude)
            },
            houseCuspValues = chartCake.houseCusps.allCusps().map { it.longitude }
        )

        // 🧮 Original: Aspect + House Scores separately
        val aspectScores = calculator.allCelestialAspectScoresCo(bodies)
        val houseScores = calculator.getHouseScoreForPlanetsCo(bodies)

        Log.d("PlanetStrength", "🪐 Planet Strength Breakdown (Aspect + House + Total):")
        val planetsSorted = bodies.sortedByDescending {
            (aspectScores[it] ?: 0.0) + (houseScores[it] ?: 0.0)
        }

        for (planet in planetsSorted) {
            val name = planet.body.keyName.padEnd(12)
            val aspect = aspectScores[planet] ?: 0.0
            val house = houseScores[planet] ?: 0.0
            val total = aspect + house
            Log.d(
                "PlanetStrength",
                "$name → Aspect: %.2f | House: %.2f | Total: %.2f".format(aspect, house, total)
            )
        }

        // 🌐 Extra: Log declinations + total score using method with declination debug
        val totalScores = calculator.getTotalPowerScoresForPlanetsCo(bodies)
        Log.d(
            "PlanetStrength",
            "📡 Total Planet Power Scores (from full method with declination logs):"
        )
        val totalSorted = totalScores.entries.sortedByDescending { it.value }
        for ((planet, score) in totalSorted) {
            Log.d("PlanetStrength", "${planet.body.keyName.padEnd(12)} → Total: %.2f".format(score))
        }

        // 🏠 Extra: Log house placements
        Log.d("PlanetHouse", "🏠 Planet House Placements:")
        for (planet in bodies) {
            val name = planet.body.keyName.padEnd(12)
            val lon = "%.2f".format(planet.longitude)
            val house = chartCake.houseCusps.houseForLongitude(planet.longitude)
            Log.d("PlanetHouse", "$name → Lon: $lon° falls in House $house")
        }
    }
}
