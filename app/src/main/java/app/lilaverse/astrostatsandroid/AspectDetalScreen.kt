package app.lilaverse.astrostatsandroid
import androidx.compose.foundation.clickable
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
import java.util.Locale
import androidx.navigation.NavHostController
import java.net.URLEncoder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AspectDetailScreen(chart: Chart, aspectKind: Kind, navController: NavController? = null) {
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
    val aspectScores = remember(calculator) { calculator.allCelestialAspectScoresByAspect(bodies) }
    val aspectsOfKind = remember(aspectKind) {
        aspectScores.filter { it.key.kind == aspectKind }
            .toList()
            .sortedByDescending { it.second }
    }

    Column(Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text(text = "${aspectKind.symbol} ${aspectKind.description.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }} Aspects") },
            navigationIcon = {
                navController?.let {
                    IconButton(onClick = { it.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            }
        )
        LazyColumn(Modifier.padding(16.dp)) {
            items(aspectsOfKind) { (aspect, score) ->
                AspectCard(aspect = aspect, score = score, chartCake = chartCake)
                Spacer(Modifier.height(8.dp))
            }
            if (aspectsOfKind.isEmpty()) {
                item {
                    Text("No aspects of this type found", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }
}

@Composable
private fun AspectCard(aspect: CelestialAspect, score: Double, chartCake: ChartCake) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(Modifier.padding(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                PlanetBlock(coordinate = aspect.body1, chartCake = chartCake)
                Spacer(Modifier.weight(1f))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(aspect.kind.symbol, fontSize = 20.sp, color = AstroColors.aspectColor(aspect.kind.description.capitalize()))
                    Text(aspect.kind.description.capitalize(), fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                Spacer(Modifier.weight(1f))
                PlanetBlock(coordinate = aspect.body2, chartCake = chartCake, alignEnd = true)
            }
            Divider(Modifier.padding(vertical = 8.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                ScoreBlock(label = "Score", value = "%.1f".format(score))
                ScoreBlock(label = "Orb", value = "%.1f°".format(aspect.orbDelta))
                ScoreBlock(label = "MaxOrb", value = "%.1f°".format(aspect.orb))
            }
        }
    }
}

@Composable
private fun PlanetBlock(coordinate: Coordinate, chartCake: ChartCake, alignEnd: Boolean = false) {
    val sign = coordinate.sign.name
    val house = chartCake.houseCusps.houseForLongitude(coordinate.longitude)
    val planetName = coordinate.body.keyName
    val symbol = (coordinate.body as? CelestialObject.Planet)?.planet?.symbol ?: ""
    val color = AstroColors.planetColor(planetName)

    Column(horizontalAlignment = if (alignEnd) Alignment.End else Alignment.Start) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(symbol, color = color, fontSize = 18.sp)
            Spacer(Modifier.width(4.dp))
            Text(planetName, color = color, fontWeight = FontWeight.SemiBold)
        }
        Text("$sign, ${ordinal(house)} House", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
private fun ScoreBlock(label: String, value: String) {
    Column(horizontalAlignment = Alignment.Start) {
        Text(label, fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(value, fontWeight = FontWeight.Medium)
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
