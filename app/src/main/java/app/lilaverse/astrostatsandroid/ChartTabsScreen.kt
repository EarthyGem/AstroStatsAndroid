package app.lilaverse.astrostatsandroid

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import app.lilaverse.astrostatsandroid.model.Chart


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChartTabsScreen(chart: Chart, chartCake: ChartCake, navController: NavHostController) {
    val context = LocalContext.current
    val tabs = listOf("Birth Chart", "Planets", "Signs", "Houses", "Aspects", "Transits","Progressions","AstroClock")
    var selectedTab by remember { mutableStateOf(0) }

    val chartCake = remember(chart) { ChartCake.from(chart) }

    // Calculations (same logic reused)
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

    // Normalization
    val planetTriples = totalPowerScores.entries.map {
        val max = totalPowerScores.values.maxOrNull() ?: 1.0
        Triple(it.key.body, (it.value / max * 100f).toFloat(), it.value.toFloat())
    }

    val signTriples = signScores.entries.map {
        val max = signScores.values.maxOrNull() ?: 1.0
        Triple(it.key, (it.value / max * 100f).toFloat(), it.value.toFloat())
    }

    val maxHouse = houseScores.values.maxOrNull() ?: 1.0
    val houseTriples = houseScores.entries.map {
        Triple(it.key, (it.value / maxHouse * 100f).toFloat(), it.value.toFloat())
    }

    val aspectTriples = aspectKindScores.map {
        val max = aspectKindScores.maxOfOrNull { it.second } ?: 1.0
        Triple(it.first, (it.second / max * 100f).toFloat(), it.second.toFloat())
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 8.dp)
        ) {
            ScrollableTabRow(
                selectedTabIndex = selectedTab,
                edgePadding = 0.dp
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(title, maxLines = 1) }
                    )
                }
            }

            Spacer(Modifier.height(4.dp))

            when (selectedTab) {
                0 -> BirthChartTab(chart)
                1 -> PlanetScoresTab(
                    planetScores = planetTriples,
                    houseCusps = chartCake.houseCusps,
                    chartId = chart.id,
                    navController = navController
                )
                2 -> SignScoresTab(
                    signScores = signTriples,
                    chartId = chart.id,
                    navController = navController
                )
                3 -> HouseScoresTab(
                    houseTriples = houseTriples,
                    chartId = chart.id,
                    navController = navController
                )
                4 -> AspectScoresTab(
                    aspectTriples = aspectTriples,
                    chartId = chart.id,
                    navController = navController
                )
                5 -> TransitsTab(chart = chart, chartCake = chartCake)
                6 -> ProgressionsTab(chart = chart, chartCake = chartCake)
                7 -> AstroClockTab(chart = chart)

            }
        }
    }
}
