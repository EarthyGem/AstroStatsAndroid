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
import androidx.compose.ui.platform.LocalContext
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
import app.lilaverse.astrostatsandroid.model.ChartDatabase
import app.lilaverse.astrostatsandroid.Planet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Removing the call to heavy calculations
        // runPlanetStrengthTest()

        setContent {
            val context = LocalContext.current
            val chartDao = remember { ChartDatabase.getDatabase(context).chartDao() }
            val chartList = chartDao.getAllCharts().collectAsState(initial = emptyList()).value

            AstroStatsAndroidTheme {
                val navController = rememberNavController()

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
                                    navController.navigate("chartDetail/${selectedChart.id}")
                                },
                                onChartEdit = { chart ->
                                    // Add navigation to edit screen
                                    navController.navigate("addChart?chartId=${chart.id}")
                                },
                                onChartDelete = { chart ->
                                    // Delete chart from database
                                    CoroutineScope(Dispatchers.IO).launch {
                                        chartDao.deleteChart(chart)
                                    }
                                }
                            )
                        }
                    }

                    composable(
                        route = "addChart?chartId={chartId}",
                        arguments = listOf(navArgument("chartId") {
                            type = NavType.IntType
                            defaultValue = -1
                        })
                    ) { backStackEntry ->
                        val chartId = backStackEntry.arguments?.getInt("chartId") ?: -1
                        val chartToEdit = if (chartId != -1) chartList.find { it.id == chartId } else null

                        ChartInputScreen(
                            editChart = chartToEdit,
                            onSaveComplete = { chart ->
                                CoroutineScope(Dispatchers.IO).launch {
                                    chartDao.insertChart(chart)
                                }
                                navController.popBackStack()
                            },
                            onCancel = {
                                navController.popBackStack()
                            }
                        )
                    }

                    composable(
                        route = "chartDetail/{chartId}",
                        arguments = listOf(navArgument("chartId") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val chartId = backStackEntry.arguments?.getInt("chartId") ?: 0
                        val chart = chartList.find { it.id == chartId }
                        if (chart != null) {
                            ChartTabsScreen(chart = chart, navController = navController)
                        }
                    }
                    composable(
                        route = "planetDetail/{chartId}/{planetName}",
                        arguments = listOf(
                            navArgument("chartId") { type = NavType.IntType },
                            navArgument("planetName") { type = NavType.StringType }
                        )
                    ) { backStackEntry ->
                        val chartId = backStackEntry.arguments?.getInt("chartId") ?: 0
                        val planetName = backStackEntry.arguments?.getString("planetName") ?: ""
                        val chart = chartList.find { it.id == chartId }
                        val planet = Planet.fromKeyName(planetName)?.celestialObject
                        if (chart != null && planet != null) {
                            PlanetDetailScreen(chart = chart, planet = planet, navController = navController)
                        }
                    }

                    composable(
                        route = "signDetail/{chartId}/{signName}",
                        arguments = listOf(
                            navArgument("chartId") { type = NavType.IntType },
                            navArgument("signName") { type = NavType.StringType }
                        )
                    ) { backStackEntry ->
                        val chartId = backStackEntry.arguments?.getInt("chartId") ?: 0
                        val signName = backStackEntry.arguments?.getString("signName") ?: ""
                        val chart = chartList.find { it.id == chartId }
                        val sign = Zodiac.fromName(signName)
                        if (chart != null && sign != null) {
                            SignDetailScreen(chart = chart, sign = sign, navController = navController)
                        }
                    }

                    composable(
                        route = "houseDetail/{chartId}/{houseNumber}",
                        arguments = listOf(
                            navArgument("chartId") { type = NavType.IntType },
                            navArgument("houseNumber") { type = NavType.IntType }
                        )
                    ) { backStackEntry ->
                        val chartId = backStackEntry.arguments?.getInt("chartId") ?: 0
                        val houseNumber = backStackEntry.arguments?.getInt("houseNumber") ?: 1
                        val chart = chartList.find { it.id == chartId }
                        if (chart != null) {
                            HouseDetailScreen(chart = chart, houseNumber = houseNumber, navController = navController)
                        }
                    }

                    composable(
                        route = "aspectDetail/{chartId}/{aspectKind}",
                        arguments = listOf(
                            navArgument("chartId") { type = NavType.IntType },
                            navArgument("aspectKind") { type = NavType.StringType }
                        )
                    ) { backStackEntry ->
                        val chartId = backStackEntry.arguments?.getInt("chartId") ?: 0
                        val kindName = backStackEntry.arguments?.getString("aspectKind") ?: Kind.Conjunction.name
                        val chart = chartList.find { it.id == chartId }
                        val aspectKind = try { Kind.valueOf(kindName) } catch (e: IllegalArgumentException) { Kind.Conjunction }
                        if (chart != null) {
                            AspectDetailScreen(chart = chart, aspectKind = aspectKind, navController = navController)
                        }
                    }
                }
            }
        }
    }

    // Commenting out the entire runPlanetStrengthTest method
    /*
    private fun runPlanetStrengthTest() {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("America/Los_Angeles"))
        calendar.set(1977, Calendar.MAY, 21, 13, 57, 0)
        val birthDate = calendar.time

        val latitude = 32.7157
        val longitude = -117.1611

        val houseCusps = HouseCuspBuilder.create(latitude, longitude, birthDate) // ✅ Add this

        val chartCake = ChartCake(
            birthDate = birthDate,
            latitude = latitude,
            longitude = longitude,
            transitDate = Date(),
            houseCusps = houseCusps // ✅ Pass it here
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

        // 🪐 Aspect + House Score Calculation
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

        // 📡 Total Planet Power Scores
        val totalScores = calculator.getTotalPowerScoresForPlanetsCo(bodies)
        Log.d("PlanetStrength", "📡 Total Planet Power Scores:")
        totalScores.entries.sortedByDescending { it.value }.forEach { (planet, score) ->
            Log.d("PlanetStrength", "${planet.body.keyName.padEnd(12)} → Total: %.2f".format(score))
        }

        // 🏠 Planet House Placements
        Log.d("PlanetHouse", "🏠 Planet House Placements:")
        for (planet in bodies) {
            val name = planet.body.keyName.padEnd(12)
            val lon = "%.2f".format(planet.longitude)
            val house = chartCake.houseCusps.houseForLongitude(planet.longitude)
            Log.d("PlanetHouse", "$name → Lon: $lon° falls in House $house")
        }

        // 🔮 Sign Strength Calculation
        val signStrengthCalc = SignStrengthCalculator(chartCake, totalScores)
        val signScores = signStrengthCalc.calculateTotalSignScores()

        Log.d("SignStrength", "🔮 Total Sign Strength Scores:")
        signScores.entries.sortedByDescending { it.value }.forEach { (sign, score) ->
            Log.d("SignStrength", "${sign.name.padEnd(10)} → Score: %.2f".format(score))

            // 🏛️ House Strength Scores
            val houseStrengthCalc = HouseStrengthCalculator(chartCake, totalScores)
            val houseScores = houseStrengthCalc.calculateHouseStrengths()

            Log.d("HouseStrength", "🏛️ Total House Strength Scores:")
            houseScores.entries.sortedByDescending { it.value }.forEach { (house, score) ->
                Log.d("HouseStrength", "House $house → Score: %.2f".format(score))
                val aspectKindScores = calculator.totalScoresByAspectType(bodies)
                Log.d("AspectKindScores", "🎯 Aspect Scores by Type:")
                aspectKindScores.forEach { (kind, total) ->
                    Log.d("AspectKindScores", "${kind.name}: %.2f".format(total))
                }
            }
        }
    }
    */
}