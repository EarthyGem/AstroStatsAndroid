package app.lilaverse.astrostatsandroid

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import app.lilaverse.astrostatsandroid.model.Chart
import app.lilaverse.astrostatsandroid.model.*
import app.lilaverse.astrostatsandroid.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp

@Composable
fun ChartTabsScreen(chart: Chart, navController: NavHostController) {
    val tabs = listOf("Birth Chart", "Planets", "Signs", "Houses", "Aspects")
    var selectedTab by remember { mutableStateOf(0) }

    val chartCake = remember(chart) { ChartCake.from(chart) }

    // Core calculations
    val totalPowerScores = remember(chartCake) {
        PlanetStrengthCalculator(
            orbDictionary,
            houseProvider = { chartCake.houseCusps.houseForLongitude(it.longitude) },
            luminaryChecker = { it.body.keyName in listOf("Sun", "Moon", "Mercury") },
            houseCuspsProvider = { lon ->
                val houseNum = chartCake.houseCusps.houseForLongitude(lon)
                val cuspLon = chartCake.houseCusps.getCusp(houseNum - 1).longitude
                HouseCusp(houseNum, cuspLon)
            },
            houseCuspValues = chartCake.houseCusps.allCusps().map { it.longitude }
        ).getTotalPowerScoresForPlanetsCo(chartCake.bodies)
    }

    val signScores = remember(chartCake) {
        SignStrengthCalculator(chartCake, totalPowerScores).calculateTotalSignScores()
    }

    val houseScores = remember(chartCake) {
        HouseStrengthCalculator(chartCake, totalPowerScores).calculateHouseStrengths()
    }

    val aspectKindScores = remember(chartCake) {
        PlanetStrengthCalculator(
            orbDictionary,
            houseProvider = { chartCake.houseCusps.houseForLongitude(it.longitude) },
            luminaryChecker = { it.body.keyName in listOf("Sun", "Moon", "Mercury") },
            houseCuspsProvider = { lon ->
                val houseNum = chartCake.houseCusps.houseForLongitude(lon)
                val cuspLon = chartCake.houseCusps.getCusp(houseNum - 1).longitude
                HouseCusp(houseNum, cuspLon)
            },
            houseCuspValues = chartCake.houseCusps.allCusps().map { it.longitude }
        ).totalScoresByAspectType(chartCake.bodies)
    }

    // Normalized for display
    val planetTriples = totalPowerScores.entries.map {
        val max = totalPowerScores.values.maxOrNull() ?: 1.0
        Triple(it.key.body, (it.value / max * 100f).toFloat(), it.value.toFloat())
    }

    val signTriples = signScores.entries.map {
        val max = signScores.values.maxOrNull() ?: 1.0
        Triple(it.key, (it.value / max * 100f).toFloat(), it.value.toFloat())
    }

    val houseTriples = houseScores.entries.map {
        val max = houseScores.values.maxOrNull() ?: 1.0
        Triple("${it.key}th House", (it.value / max * 100f).toFloat(), it.value.toFloat())
    }

    val aspectTriples = aspectKindScores.map {
        val max = aspectKindScores.maxOfOrNull { it.second } ?: 1.0
        Triple(it.first, (it.second / max * 100f).toFloat(), it.second.toFloat())
    }

    // UI Tabs
    Column {
        TabRow(selectedTabIndex = selectedTab) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = { Text(title) }
                )
            }
        }

        Spacer(Modifier.height(8.dp))

        when (selectedTab) {
            0 -> BirthChartTab(chart)
            1 -> PlanetScoresTab(planetTriples, chartCake.houseCusps)
            2 -> SignScoresTab(signTriples)
            3 -> HouseScoresTab(houseTriples)
            4 -> AspectScoresTab(aspectTriples)
        }
    }
}