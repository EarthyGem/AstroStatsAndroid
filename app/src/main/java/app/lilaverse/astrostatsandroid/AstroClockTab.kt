package app.lilaverse.astrostatsandroid

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import app.lilaverse.astrostatsandroid.model.Chart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

@Composable
fun AstroClockTab(chart: Chart) {
    val scrollState = rememberScrollState()
    val zone = remember(chart.timezone) { TimeZone.getTimeZone(chart.timezone) }
    val timeFormatter = remember(chart.timezone) {
        SimpleDateFormat("HH:mm:ss", Locale.getDefault()).apply {
            timeZone = zone
        }
    }
    val dateFormatter = remember(chart.timezone) {
        SimpleDateFormat("EEEE, MMM d, yyyy", Locale.getDefault()).apply {
            timeZone = zone
        }
    }

    var snapshot by remember { mutableStateOf<AstroClockSnapshot?>(null) }
    val updatedChart by rememberUpdatedState(chart)

    LaunchedEffect(chart.id) {
        while (isActive) {
            val now = Date()
            val newSnapshot = withContext(Dispatchers.Default) {
                computeAstroClockSnapshot(updatedChart, now)
            }
            snapshot = newSnapshot

            val millisPastSecond = System.currentTimeMillis() % 1000L
            delay(1000L - millisPastSecond)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "AstroClock",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Real-time sky over ${chart.locationName.ifBlank { chart.birthPlace }}",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "Timezone: ${zone.getDisplayName(false, TimeZone.SHORT)}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        snapshot?.let { data ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = timeFormatter.format(data.timestamp),
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = dateFormatter.format(data.timestamp),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = "Data refreshes every second using Swiss Ephemeris",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Analog sky clock",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(Modifier.height(12.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        AstroClockFace(data.hands)
                    }

                    Spacer(Modifier.height(12.dp))
                    HorizontalDivider()
                    Spacer(Modifier.height(12.dp))

                    Text(
                        text = "Planetary hands",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold
                    )
                    val planetHands = data.hands.filter { it.type == HandType.Planet }
                    planetHands.forEach { hand ->
                        LegendRow(hand)
                    }

                    Spacer(Modifier.height(12.dp))
                    Text(
                        text = "Angles",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold
                    )
                    data.hands.filter { it.type != HandType.Planet }.forEach { hand ->
                        LegendRow(hand)
                    }
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Real-time planetary strength",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(Modifier.height(12.dp))
                    val sorted = data.hands.sortedByDescending { it.score }
                    val maxScore = sorted.maxOfOrNull { it.score }?.takeIf { it > 0 } ?: 1.0
                    val (planetEntries, angleEntries) = sorted.partition { it.type == HandType.Planet }

                    Text(
                        text = "Planets",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold
                    )
                    planetEntries.forEach { entry ->
                        StrengthRow(entry, maxScore)
                    }

                    if (angleEntries.isNotEmpty()) {
                        Spacer(Modifier.height(12.dp))
                        Text(
                            text = "Angles",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.SemiBold
                        )
                        angleEntries.forEach { entry ->
                            StrengthRow(entry, maxScore)
                        }
                    }
                }
            }
        } ?: Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 48.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

@Composable
private fun AstroClockFace(hands: List<ClockHand>) {
    val density = LocalDensity.current
    val planetHands = remember(hands) { hands.filter { it.type == HandType.Planet } }
    val ascendant = hands.firstOrNull { it.type == HandType.Ascendant }
    val midheaven = hands.firstOrNull { it.type == HandType.Midheaven }
    val colorScheme = MaterialTheme.colorScheme

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp)
    ) {
        val radius = min(size.width, size.height) / 2f
        val center = Offset(size.width / 2f, size.height / 2f)
        val ringStroke = with(density) { 2.dp.toPx() }
        val tickStroke = with(density) { 1.5.dp.toPx() }
        val planetStroke = with(density) { 3.dp.toPx() }
        val angleStroke = with(density) { 5.dp.toPx() }

        drawCircle(
            color = colorScheme.surfaceVariant.copy(alpha = 0.3f),
            radius = radius,
            center = center
        )
        drawCircle(
            color = colorScheme.onSurface.copy(alpha = 0.35f),
            radius = radius,
            center = center,
            style = Stroke(width = ringStroke)
        )

        for (i in 0 until 12) {
            val angle = Math.toRadians(i * 30.0 - 90.0)
            val outer = Offset(
                x = center.x + cos(angle).toFloat() * radius,
                y = center.y + sin(angle).toFloat() * radius
            )
            val inner = Offset(
                x = center.x + cos(angle).toFloat() * radius * 0.85f,
                y = center.y + sin(angle).toFloat() * radius * 0.85f
            )
            drawLine(
                color = colorScheme.onSurface.copy(alpha = 0.25f),
                start = inner,
                end = outer,
                strokeWidth = tickStroke,
                cap = StrokeCap.Round
            )
        }

        val planetLength = radius * 0.82f
        planetHands.forEachIndexed { index, hand ->
            val normalizedIndex = if (planetHands.size <= 1) 0f else index.toFloat() / (planetHands.size - 1)
            val lengthFactor = 0.75f + normalizedIndex * 0.2f
            val stroke = planetStroke * (1.2f - normalizedIndex * 0.4f).coerceAtLeast(0.85f)
            drawHand(
                hand = hand,
                center = center,
                radius = planetLength * lengthFactor,
                strokeWidth = stroke
            )
        }

        ascendant?.let {
            drawHand(
                hand = it,
                center = center,
                radius = radius * 0.95f,
                strokeWidth = angleStroke
            )
        }

        midheaven?.let {
            drawHand(
                hand = it,
                center = center,
                radius = radius * 0.95f,
                strokeWidth = angleStroke,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(18f, 12f))
            )
        }

        drawCircle(
            color = colorScheme.surface,
            radius = with(density) { 8.dp.toPx() },
            center = center
        )
        drawCircle(
            color = colorScheme.primary,
            radius = with(density) { 4.dp.toPx() },
            center = center
        )
    }
}

private fun DrawScope.drawHand(
    hand: ClockHand,
    center: Offset,
    radius: Float,
    strokeWidth: Float,
    pathEffect: PathEffect? = null
) {
    val angle = Math.toRadians(hand.longitude - 90.0)
    val end = Offset(
        x = center.x + cos(angle).toFloat() * radius,
        y = center.y + sin(angle).toFloat() * radius
    )
    drawLine(
        color = hand.color,
        start = center,
        end = end,
        strokeWidth = strokeWidth,
        cap = StrokeCap.Round,
        pathEffect = pathEffect
    )
    drawCircle(
        color = hand.color,
        radius = strokeWidth * 0.6f,
        center = end
    )
}

@Composable
private fun LegendRow(hand: ClockHand) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(16.dp)
                .background(hand.color, CircleShape)
        )
        Spacer(Modifier.width(12.dp))
        Text(
            text = "${hand.glyph} ${hand.label}",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.width(120.dp)
        )
        Text(
            text = hand.detail,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Start
        )
    }
}

@Composable
private fun StrengthRow(entry: ClockHand, maxScore: Double) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${entry.glyph} ${entry.label}",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(Modifier.weight(1f))
            Text(
                text = String.format(Locale.getDefault(), "%.2f", entry.score),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Spacer(Modifier.height(4.dp))
        LinearProgressIndicator(
            progress = (entry.score / maxScore).toFloat().coerceIn(0f, 1f),
            color = entry.color,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
        )
        Spacer(Modifier.height(8.dp))
    }
}

private enum class HandType { Planet, Ascendant, Midheaven }

private data class ClockHand(
    val label: String,
    val glyph: String,
    val longitude: Double,
    val color: Color,
    val detail: String,
    val house: Int?,
    val score: Double,
    val type: HandType
)

private data class AstroClockSnapshot(
    val timestamp: Date,
    val hands: List<ClockHand>
)

private suspend fun computeAstroClockSnapshot(chart: Chart, moment: Date): AstroClockSnapshot {
    val timezoneId = chart.timezone
    val cusps = HouseCuspBuilder.create(chart.latitude, chart.longitude, moment, timezoneId)

    data class PlanetData(val planet: Planet, val coordinate: Coordinate, val house: Int)

    val planets = planetOrder.map { planet ->
        val coordinate = Coordinate(CelestialObject.Planet(planet), moment, timezoneId)
        val house = cusps.houseForLongitude(coordinate.longitude)
        PlanetData(planet, coordinate, house)
    }

    val ascendantCoordinate = Coordinate.fromAscendant(
        cusp = cusps.getCusp(0),
        date = moment,
        geoLatitude = chart.latitude,
        geoLongitude = chart.longitude,
        timezone = timezoneId
    )

    val midheavenCoordinate = Coordinate.fromMidheaven(
        cusp = cusps.getCusp(9),
        date = moment,
        geoLatitude = chart.latitude,
        geoLongitude = chart.longitude,
        timezone = timezoneId
    )

    val calculator = PlanetStrengthCalculator(
        orbDictionary = orbDictionary,
        houseProvider = { coordinate ->
            when (coordinate.body.keyName) {
                "Ascendant" -> 1
                "Midheaven" -> 10
                else -> cusps.houseForLongitude(coordinate.longitude)
            }
        },
        luminaryChecker = { coordinate ->
            val key = coordinate.body.keyName
            key == "Sun" || key == "Moon"
        },
        houseCuspsProvider = { longitude ->
            val house = cusps.houseForLongitude(longitude)
            val cuspLongitude = cusps.getCusp(house - 1).longitude
            HouseCusp(house, cuspLongitude)
        },
        houseCuspValues = cusps.allCusps().map { it.longitude }
    )

    val bodiesForStrength = planets.map { it.coordinate } + ascendantCoordinate + midheavenCoordinate
    val strengthMap = calculator.getTotalPowerScoresForPlanetsCo(bodiesForStrength)

    val hands = buildList {
        planets.forEach { data ->
            val coord = data.coordinate
            add(
                ClockHand(
                    label = data.planet.keyName,
                    glyph = data.planet.symbol,
                    longitude = coord.longitude,
                    color = planetColor(data.planet.keyName),
                    detail = "${coord.formattedDegreeInSign}° ${coord.signName} ${coord.signGlyph} • House ${data.house}",
                    house = data.house,
                    score = strengthMap[coord] ?: 0.0,
                    type = HandType.Planet
                )
            )
        }

        add(
            ClockHand(
                label = "Ascendant",
                glyph = "ASC",
                longitude = ascendantCoordinate.longitude,
                color = planetColor("Ascendant"),
                detail = "${ascendantCoordinate.formattedDegreeInSign}° ${ascendantCoordinate.signName} ${ascendantCoordinate.signGlyph} • 1st House",
                house = 1,
                score = strengthMap[ascendantCoordinate] ?: 0.0,
                type = HandType.Ascendant
            )
        )

        add(
            ClockHand(
                label = "Midheaven",
                glyph = "MC",
                longitude = midheavenCoordinate.longitude,
                color = planetColor("Midheaven"),
                detail = "${midheavenCoordinate.formattedDegreeInSign}° ${midheavenCoordinate.signName} ${midheavenCoordinate.signGlyph} • 10th House",
                house = 10,
                score = strengthMap[midheavenCoordinate] ?: 0.0,
                type = HandType.Midheaven
            )
        )
    }

    return AstroClockSnapshot(moment, hands)
}

private val planetOrder = listOf(
    Planet.Sun,
    Planet.Moon,
    Planet.Mercury,
    Planet.Venus,
    Planet.Mars,
    Planet.Jupiter,
    Planet.Saturn,
    Planet.Uranus,
    Planet.Neptune,
    Planet.Pluto
)

private val planetColorMap = mapOf(
    "Sun" to Color(0xFFFFB300),
    "Moon" to Color(0xFF64B5F6),
    "Mercury" to Color(0xFF9575CD),
    "Venus" to Color(0xFFF48FB1),
    "Mars" to Color(0xFFE57373),
    "Jupiter" to Color(0xFF7986CB),
    "Saturn" to Color(0xFFA1887F),
    "Uranus" to Color(0xFF4DB6AC),
    "Neptune" to Color(0xFF4FC3F7),
    "Pluto" to Color(0xFFBA68C8),
    "Ascendant" to Color(0xFF455A64),
    "Midheaven" to Color(0xFF00897B)
)

private fun planetColor(name: String): Color = planetColorMap[name] ?: Color(0xFF9E9E9E)