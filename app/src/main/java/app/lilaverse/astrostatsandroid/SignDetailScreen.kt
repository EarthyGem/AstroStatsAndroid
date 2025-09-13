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
import androidx.navigation.NavController
import app.lilaverse.astrostatsandroid.model.Chart

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignDetailScreen(chart: Chart, sign: Zodiac.Sign, navController: NavController? = null) {
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
    val planetScores = remember(totalPowerScores) {
        totalPowerScores.mapKeys { it.key.body }
    }
    val signScores = remember(totalPowerScores) {
        SignStrengthCalculator(chartCake, totalPowerScores).calculateTotalSignScores()
    }

    val signPower = signScores[sign] ?: 0.0
    val planetsInSign = remember(sign) { bodies.filter { it.sign == sign } }
    val rulerPlanets = remember(sign) { getRulersForSign(sign) }
    val weightedRulerPower = remember(rulerPlanets, planetScores) {
        rulerPlanets.sumOf { (planetScores[it] ?: 0.0) * 0.5 }
    }
    val influenceSummary = remember(planetsInSign, rulerPlanets, planetScores) {
        val map = mutableMapOf<CelestialObject, Double>()
        planetsInSign.forEach { coord ->
            val power = planetScores[coord.body] ?: 0.0
            map[coord.body] = (map[coord.body] ?: 0.0) + power
        }
        rulerPlanets.forEach { planet ->
            val half = (planetScores[planet] ?: 0.0) * 0.5
            map[planet] = (map[planet] ?: 0.0) + half
        }
        map
    }

    Column(Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text(text = "${sign.name} Details") },
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
                    Text(text = sign.glyph, fontSize = 32.sp)
                    Spacer(Modifier.width(8.dp))
                    Text(text = sign.name, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                }
                Spacer(Modifier.height(8.dp))
                Text("Total Power: ${"%.2f".format(signPower)}", style = MaterialTheme.typography.bodyMedium)
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
                Text("Total Ruler Contribution (½ each): ${"%.2f".format(weightedRulerPower)}")
                Spacer(Modifier.height(16.dp))
            }
            if (planetsInSign.isNotEmpty()) {
                item {
                    Text("Planets in ${sign.name}:", fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(4.dp))
                }
                items(planetsInSign) { coord ->
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                        val symbol = (coord.body as? CelestialObject.Planet)?.planet?.symbol ?: ""
                        Text(symbol)
                        Spacer(Modifier.width(8.dp))
                        Text(text = coord.body.keyName, Modifier.weight(1f))
                        Text("%.2f".format(planetScores[coord.body] ?: 0.0), fontSize = 12.sp)
                    }
                    Spacer(Modifier.height(4.dp))
                }
                item {
                    Divider()
                    Spacer(Modifier.height(16.dp))
                }
            }
            item {
                Text("How Sign Power is Calculated", fontWeight = FontWeight.SemiBold)
                Spacer(Modifier.height(4.dp))
                Text(
                    """
                    A sign's total power is the sum of:
                    • The power of all planets in the sign.
                    • Plus ½ the power of its ruling planet(s), even if they are in other signs.

                    Only half of a ruling planet's strength contributes to the sign — this is the key value, not the planet's full power.
                    """.trimIndent(),
                    fontSize = 12.sp
                )
                Spacer(Modifier.height(16.dp))
            }
            item {
                Text("Planetary Influence Summary", fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(4.dp))
            }
            items(influenceSummary.toList()) { (planet, value) ->
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                    val symbol = (planet as? CelestialObject.Planet)?.planet?.symbol ?: ""
                    Text(symbol)
                    Spacer(Modifier.width(8.dp))
                    Text(text = planet.keyName, Modifier.weight(1f))
                    Text("%.2f".format(value), fontSize = 12.sp)
                }
                Spacer(Modifier.height(4.dp))
            }
        }
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