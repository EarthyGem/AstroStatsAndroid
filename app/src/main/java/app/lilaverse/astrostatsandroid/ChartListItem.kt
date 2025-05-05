// ChartListItem.kt
package app.lilaverse.astrostatsandroid.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.lilaverse.astrostatsandroid.*
import app.lilaverse.astrostatsandroid.model.Chart
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ChartListItem(chart: Chart, onClick: () -> Unit) {
    val dateFormat = SimpleDateFormat("MMM d, yyyy 'at' h:mma", Locale.getDefault())
    val dateText = dateFormat.format(chart.date)

    // Calculate the strongest planet
    val chartCake = remember(chart) { ChartCake.from(chart) }

    val totalPowerScores = remember(chartCake) {
        PlanetStrengthCalculator(
            orbDictionary,
            houseProvider = { chartCake.houseCusps.houseForLongitude(it.longitude) },
            luminaryChecker = { it.body.keyName in listOf("Sun", "Moon") },
            houseCuspsProvider = { lon ->
                val houseNum = chartCake.houseCusps.houseForLongitude(lon)
                val cuspLon = chartCake.houseCusps.getCusp(houseNum - 1).longitude
                HouseCusp(houseNum, cuspLon)
            },
            houseCuspValues = chartCake.houseCusps.allCusps().map { it.longitude }
        ).getTotalPowerScoresForPlanetsCo(chartCake.bodies)
    }

    // Get the strongest planet
    val strongestPlanet = remember(totalPowerScores) {
        totalPowerScores.entries
            .filter { it.key.body is CelestialObject.Planet }
            .maxByOrNull { it.value }
            ?.key?.body as? CelestialObject.Planet
    }

    // Get the planet name and symbol
    val planetName = strongestPlanet?.planet?.keyName ?: "Moon" // Default to Moon if calculation fails
    val planetSymbol = strongestPlanet?.planet?.symbol ?: "☽"

    // Get color for the planet
    val planetColor = AstroColors.planetColor(planetName)

    // Get zodiac glyphs
    val sunGlyph = Zodiac.fromName(chart.sunSign)?.glyph ?: ""
    val moonGlyph = Zodiac.fromName(chart.moonSign)?.glyph ?: ""
    val risingGlyph = Zodiac.fromName(chart.risingSign)?.glyph ?: ""

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF1F1F1F))
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Colored circle with planet glyph
        Box(
            modifier = Modifier
                .size(56.dp)
                .background(planetColor, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = planetSymbol,
                color = Color.Black,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Text information
        Column {
            Text(
                chart.name,
                style = MaterialTheme.typography.titleMedium,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                "$dateText • ${chart.birthPlace}",
                fontSize = 14.sp,
                color = Color.Gray
            )
            Text(
                "${chart.sunSign} Sun • ${chart.moonSign} Moon • ${chart.risingSign} $risingGlyph Rising",
                fontSize = 14.sp,
                color = Color.Gray
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // Arrow icon
        Text(
            text = "›",
            fontSize = 24.sp,
            color = Color.Gray
        )
    }
}