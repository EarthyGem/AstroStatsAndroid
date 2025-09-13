package app.lilaverse.astrostatsandroid

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.lilaverse.astrostatsandroid.model.Chart
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HouseDetailScreen(chart: Chart, houseNumber: Int, navController: NavController? = null) {
    val chartCake = remember(chart) { ChartCake.from(chart) }
    val calculator = remember(chartCake) {
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
        )
    }
    val bodies = chartCake.bodies
    val totalPowerScores = remember(calculator) { calculator.getTotalPowerScoresForPlanetsCo(bodies) }
    val planetScores = remember(totalPowerScores) { totalPowerScores.mapKeys { it.key.body } }
    val houseScores = remember(chartCake) { HouseStrengthCalculator(chartCake, totalPowerScores).calculateHouseStrengths() }

    val housePower = houseScores[houseNumber] ?: 0.0
    val planetsInHouse = remember(houseNumber) { bodies.filter { chartCake.houseCusps.houseForLongitude(it.longitude) == houseNumber } }
    val cusp = remember(houseNumber) { chartCake.houseCusps.getCusp(houseNumber - 1) }
    val cuspSign = remember(cusp) { Zodiac.fromName(cusp.sign) }
    val rulerPlanets = remember(cuspSign) { cuspSign?.let { getRulersForSign(it) } ?: emptyList() }
    val cuspRulerPower = remember(rulerPlanets, planetScores) { rulerPlanets.sumOf { (planetScores[it] ?: 0.0) * 0.5 } }

    Column(Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text(text = "${ordinal(houseNumber)} House Details") },
            navigationIcon = {
                navController?.let {
                    IconButton(onClick = { it.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            }
        )
        LazyColumn(Modifier.padding(16.dp)) {
            item {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "${ordinal(houseNumber)} House", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    cuspSign?.let { sign ->
                        Spacer(Modifier.width(8.dp))
                        Text(text = sign.glyph, fontSize = 24.sp)
                    }
                }
                cuspSign?.let { sign ->
                    Spacer(Modifier.height(4.dp))
                    Text("Cusp Sign: ${sign.name}", style = MaterialTheme.typography.bodyMedium)
                }
                Spacer(Modifier.height(8.dp))
                Text("Total Power: ${"%.2f".format(housePower)}", style = MaterialTheme.typography.bodyMedium)
                Spacer(Modifier.height(16.dp))
            }
            item {
                Text("Ruler(s):", fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(4.dp))
            }
            items(rulerPlanets) { planet ->
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                    val symbol = (planet as? CelestialObject.Planet)?.planet?.symbol ?: ""
                    Text(text = symbol)
                    Spacer(Modifier.width(8.dp))
                    Text(text = planet.keyName, Modifier.weight(1f))
                    Text("%.2f".format(planetScores[planet] ?: 0.0), fontSize = 12.sp)
                }
                Spacer(Modifier.height(4.dp))
            }
            item {
                Divider()
                Spacer(Modifier.height(4.dp))
                Text("Total Ruler Contribution (½ each): ${"%.2f".format(cuspRulerPower)}")
                Spacer(Modifier.height(16.dp))
            }
            if (planetsInHouse.isNotEmpty()) {
                item {
                    Text("Planets in this House:", fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(4.dp))
                }
                items(planetsInHouse) { coord ->
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                        val symbol = (coord.body as? CelestialObject.Planet)?.planet?.symbol ?: ""
                        Text(symbol)
                        Spacer(Modifier.width(8.dp))
                        Text(text = coord.body.keyName, Modifier.weight(1f))
                        Text("%.2f".format(planetScores[coord.body] ?: 0.0), fontSize = 12.sp)
                    }
                    Spacer(Modifier.height(4.dp))
                }
            }
        }
    }
}

private fun ordinal(number: Int): String {
    if (number % 100 in 11..13) return "${number}th"
    return when (number % 10) {
        1 -> "${number}st"
        2 -> "${number}nd"
        3 -> "${number}rd"
        else -> "${number}th"
    }
}

private fun getRulersForSign(sign: Zodiac.Sign): List<CelestialObject> = when (sign.name.lowercase()) {
    "aries" -> listOf(CelestialObject.Planet(Planet.Mars))
    "taurus" -> listOf(CelestialObject.Planet(Planet.Venus))
    "gemini" -> listOf(CelestialObject.Planet(Planet.Mercury))
    "cancer" -> listOf(CelestialObject.Planet(Planet.Moon))
    "leo" -> listOf(CelestialObject.Planet(Planet.Sun))
    "virgo" -> listOf(CelestialObject.Planet(Planet.Mercury))
    "libra" -> listOf(CelestialObject.Planet(Planet.Venus))
    "scorpio" -> listOf(CelestialObject.Planet(Planet.Mars), CelestialObject.Planet(Planet.Pluto))
    "sagittarius" -> listOf(CelestialObject.Planet(Planet.Jupiter))
    "capricorn" -> listOf(CelestialObject.Planet(Planet.Saturn))
    "aquarius" -> listOf(CelestialObject.Planet(Planet.Uranus), CelestialObject.Planet(Planet.Saturn))
    "pisces" -> listOf(CelestialObject.Planet(Planet.Neptune), CelestialObject.Planet(Planet.Jupiter))
    else -> emptyList()
}