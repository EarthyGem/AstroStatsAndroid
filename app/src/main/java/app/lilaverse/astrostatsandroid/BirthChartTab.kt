package app.lilaverse.astrostatsandroid

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import app.lilaverse.astrostatsandroid.model.Chart
import androidx.compose.ui.Alignment
import androidx.compose.foundation.background
import androidx.compose.runtime.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import app.lilaverse.astrostatsandroid.HouseCusps
import java.text.SimpleDateFormat
import androidx.compose.ui.viewinterop.AndroidView
import app.lilaverse.astrostatsandroid.view.BirthChartView
import app.lilaverse.astrostatsandroid.view.Planet
import java.util.Locale





@Composable
fun BirthChartTab(chart: Chart) {
    // Calculate strongest elements
    val chartCake = ChartCake.from(chart)
    val totalPowerScores = PlanetStrengthCalculator(
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

    val signScores = SignStrengthCalculator(chartCake, totalPowerScores).calculateTotalSignScores()
    val houseScores = HouseStrengthCalculator(chartCake, totalPowerScores).calculateHouseStrengths()

    // Find strongest planet
    val strongestPlanet = totalPowerScores.entries
        .maxByOrNull { it.value }?.key?.body?.keyName ?: "Moon"

    // Find strongest sign
    val strongestSign = signScores.entries
        .maxByOrNull { it.value }?.key?.name ?: "Aries"

    // Find strongest house
    val strongestHouseNumber = houseScores.entries
        .maxByOrNull { it.value }?.key ?: 8
    val strongestHouse = "${strongestHouseNumber}th House"

    // Get the glyphs and colors for the signs
    val sunGlyph = Zodiac.fromName(chart.sunSign)?.glyph ?: ""
    val moonGlyph = Zodiac.fromName(chart.moonSign)?.glyph ?: ""
    val risingGlyph = Zodiac.fromName(chart.risingSign)?.glyph ?: ""
    val strongestSignGlyph = Zodiac.fromName(strongestSign)?.glyph ?: ""

    // Get the planet symbol
    val planetSymbol = when(strongestPlanet) {
        "Sun" -> "☉"
        "Moon" -> "☽"
        "Mercury" -> "☿"
        "Venus" -> "♀"
        "Mars" -> "♂"
        "Jupiter" -> "♃"
        "Saturn" -> "♄"
        "Uranus" -> "♅"
        "Neptune" -> "♆"
        "Pluto" -> "♇"
        else -> "?"
    }

    // Get colors
    val planetColor = AstroColors.planetColor(strongestPlanet)
    val moonColor = AstroColors.planetColor("Moon")
    val sunColor = AstroColors.planetColor("Sun")
    val signColor = AstroColors.signColor(strongestSign)

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header with name and birth details
        Text(
            text = chart.name,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = "${SimpleDateFormat("MMM d, yyyy 'at' h:mma", Locale.getDefault()).format(chart.date)} • ${chart.birthPlace}",
            color = Color.Gray,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp)
        )

        Divider(color = Color(0xFF2A2A2A), thickness = 1.dp)

        // Main stats in a grid
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            // Left column
            Column(Modifier.weight(1f)) {
                // Strongest Planet
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(planetColor, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = planetSymbol,
                            color = Color.Black,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Column(Modifier.padding(start = 8.dp)) {
                        Text(
                            text = "Strongest Planet",
                            color = Color.Gray,
                            fontSize = 14.sp
                        )
                        Text(
                            text = strongestPlanet,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(Modifier.height(16.dp))

                // Moon
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = moonGlyph,
                        color = moonColor,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Column(Modifier.padding(start = 8.dp)) {
                        Text(
                            text = "Moon",
                            color = Color.Gray,
                            fontSize = 14.sp
                        )
                        Text(
                            text = chart.moonSign,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(Modifier.height(16.dp))

                // Strongest Sign
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = strongestSignGlyph,
                        color = signColor,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Column(Modifier.padding(start = 8.dp)) {
                        Text(
                            text = "Strongest Sign",
                            color = Color.Gray,
                            fontSize = 14.sp
                        )
                        Text(
                            text = strongestSign,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // Right column
            Column(Modifier.weight(1f)) {
                // Sun
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = sunGlyph,
                        color = sunColor,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Column(Modifier.padding(start = 8.dp)) {
                        Text(
                            text = "Sun",
                            color = Color.Gray,
                            fontSize = 14.sp
                        )
                        Text(
                            text = chart.sunSign,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(Modifier.height(16.dp))

                // Ascendant
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = risingGlyph,
                        color = AstroColors.signColor(chart.risingSign),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Column(Modifier.padding(start = 8.dp)) {
                        Text(
                            text = "Ascendant",
                            color = Color.Gray,
                            fontSize = 14.sp
                        )
                        Text(
                            text = chart.risingSign,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(Modifier.height(16.dp))

                // Strongest House
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.padding(start = 8.dp)) {
                        Text(
                            text = "Strongest House",
                            color = Color.Gray,
                            fontSize = 14.sp
                        )
                        Text(
                            text = strongestHouse,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        // Chart wheel
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { context ->
                    BirthChartView(context).apply {
                        setChart(chartCake) // This sets planets + cusps
                    }
                }
            )

        }
    }
}

            @Composable
fun PlanetScoresTab(planetScores: List<Triple<CelestialObject, Float, Float>>, houseCusps: HouseCusps) {
    var selectedSort by remember { mutableStateOf(0) }
    val sortOptions = listOf("By Strength", "Conventional")

    val conventionalOrder = listOf(
        "Sun", "Moon", "Mercury", "Venus", "Mars", "Jupiter", "Saturn",
        "Uranus", "Neptune", "Pluto", "Ascendant", "Midheaven", "South Node"
    )

    val sortedScores = when (selectedSort) {
        0 -> planetScores.sortedByDescending { it.third }
        else -> planetScores.sortedBy { conventionalOrder.indexOf(it.first.keyName) }
    }

    // Calculate total score for proper percentage calculation
    val totalScore = planetScores.sumOf { it.third.toDouble() }
    // Find maximum score for scaling the bars appropriately
    val maxScore = planetScores.maxOfOrNull { it.third } ?: 1f

    val planetColors = mapOf(
        "Sun" to Color(0xFFFFA500),
        "Moon" to Color(0xFF1E90FF),
        "Mercury" to Color(0xFFBA55D3),
        "Venus" to Color(0xFFFFD700),
        "Mars" to Color(0xFFFF4500),
        "Jupiter" to Color(0xFF4B0082),
        "Saturn" to Color(0xFF4682B4),
        "Uranus" to Color(0xFF00FF7F),
        "Neptune" to Color(0xFF00CED1),
        "Pluto" to Color(0xFF8A2BE2),
        "Ascendant" to Color(0xFFA9A9A9),
        "Midheaven" to Color(0xFFA9A9A9),
        "South Node" to Color(0xFFD2691E)
    )

    Column(Modifier.padding(16.dp)) {
        Text("Planet Scores", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(8.dp))

        // Segmented control
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            sortOptions.forEachIndexed { index, label ->
                val isSelected = selectedSort == index
                Button(
                    onClick = { selectedSort = index },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.LightGray
                    )
                ) {
                    Text(label, color = if (isSelected) Color.White else Color.Black)
                }
            }
        }

        Spacer(Modifier.height(12.dp))

        sortedScores.forEach { (obj, _, score) ->
            val (name, symbol) = when {
                obj is CelestialObject.Planet -> obj.planet.keyName to obj.planet.symbol
                obj is CelestialObject.SpecialCusp && obj.name == "Ascendant" -> "Ascendant" to "↑"
                obj is CelestialObject.SpecialCusp && obj.name == "Midheaven" -> "Midheaven" to "⊗"
                obj == CelestialObject.SouthNode -> "South Node" to "☋"
                obj is CelestialObject.Cusp -> obj.keyName to ""
                else -> "Unknown" to "?"
            }

            val color = planetColors[name] ?: Color.Gray

            // Calculate the actual percentage relative to total score
            val actualPercent = (score / totalScore * 100).toFloat()

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .background(color, CircleShape)
                        .padding(2.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = symbol,
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(Modifier.width(8.dp))

                Text(
                    text = name,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.width(68.dp)
                )

                Box(
                    Modifier
                        .weight(1f)
                        .height(24.dp)
                        .background(Color.DarkGray)
                ) {
                    Box(
                        Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(score / maxScore)
                            .background(color),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            text = "%.1f%%".format(actualPercent),
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(start = 4.dp),
                            color = Color.White
                        )
                    }
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "%.1f".format(score),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
        }
    }
}

@Composable
fun SignScoresTab(signTriples: List<Triple<Zodiac.Sign, Float, Float>>) {
    var selectedSort by remember { mutableStateOf(0) }
    val sortOptions = listOf("By Strength", "Conventional")

    val sortedScores = when (selectedSort) {
        0 -> signTriples.sortedByDescending { it.third }
        else -> signTriples // Conventional = default order
    }

    // Calculate total score for proper percentage calculation
    val totalScore = signTriples.sumOf { it.third.toDouble() }
    // Find maximum score for scaling the bars appropriately
    val maxScore = signTriples.maxOfOrNull { it.third } ?: 1f

    val signColors = mapOf(
        "Aries" to Color(0xFFFF6347),     // light Mars
        "Taurus" to Color(0xFF9ACD32),    // dark Venus
        "Gemini" to Color(0xFF00BFFF),    // light Mercury
        "Cancer" to Color(0xFF00CED1),    // dark Moon
        "Leo" to Color(0xFFFFA500),       // light Sun
        "Virgo" to Color(0xFF4169E1),     // dark Mercury
        "Libra" to Color(0xFFFFE135),     // light Venus
        "Scorpio" to Color(0xFF8B0000),   // dark Mars
        "Sagittarius" to Color(0xFF4B0082),// light Jupiter
        "Capricorn" to Color(0xFF2F4F4F), // dark Saturn
        "Aquarius" to Color(0xFF00FFFF),  // light Saturn/Uranus
        "Pisces" to Color(0xFF87CEFA)     // light Neptune
    )

    Column(Modifier.padding(16.dp)) {
        Text("Sign Scores", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(8.dp))

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            sortOptions.forEachIndexed { index, label ->
                val isSelected = selectedSort == index
                Button(
                    onClick = { selectedSort = index },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.LightGray
                    )
                ) {
                    Text(label, color = if (isSelected) Color.White else Color.Black)
                }
            }
        }

        Spacer(Modifier.height(12.dp))

        sortedScores.forEach { (sign, _, score) ->
            val color = signColors[sign.name] ?: Color.Gray
            val glyph = sign.glyph

            // Calculate the actual percentage relative to total score
            val actualPercent = (score / totalScore * 100).toFloat()

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$glyph ${sign.name}",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.width(80.dp)
                )
                Box(
                    Modifier
                        .weight(1f)
                        .height(24.dp)
                        .background(Color.DarkGray)
                ) {
                    Box(
                        Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(score / maxScore)
                            .background(color),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            text = "%.1f%%".format(actualPercent),
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(start = 4.dp),
                            color = Color.White
                        )
                    }
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "%.1f".format(score),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
        }
    }
}

@Composable
fun HouseScoresTab(houseTriples: List<Triple<String, Float, Float>>) {
    var selectedSort by remember { mutableStateOf(0) }
    val sortOptions = listOf("By Strength", "Conventional")
    val houseGlyphs = mapOf(
        "Aries" to "♈", "Taurus" to "♉", "Gemini" to "♊", "Cancer" to "♋",
        "Leo" to "♌", "Virgo" to "♍", "Libra" to "♎", "Scorpio" to "♏",
        "Sagittarius" to "♐", "Capricorn" to "♑", "Aquarius" to "♒", "Pisces" to "♓",
        "AC" to "↑", "MC" to "⊗", "South Node" to "☋"
    )

    val sortedScores = when (selectedSort) {
        0 -> houseTriples.sortedByDescending { it.third }
        else -> houseTriples // leave as is for conventional order
    }

    // Calculate total score for proper percentage calculation
    val totalScore = houseTriples.sumOf { it.third.toDouble() }
    // Find maximum score for scaling the bars appropriately
    val maxScore = houseTriples.maxOfOrNull { it.third } ?: 1f

    val houseColors = mapOf(
        "1st House" to Color(0xFFFF6347),    // Aries color
        "2nd House" to Color(0xFF9ACD32),    // Taurus color
        "3rd House" to Color(0xFF00BFFF),    // Gemini color
        "4th House" to Color(0xFF00CED1),    // Cancer color
        "5th House" to Color(0xFFFFA500),    // Leo color
        "6th House" to Color(0xFF4169E1),    // Virgo color
        "7th House" to Color(0xFFFFE135),    // Libra color
        "8th House" to Color(0xFF8B0000),    // Scorpio color
        "9th House" to Color(0xFF4B0082),    // Sagittarius color
        "10th House" to Color(0xFF2F4F4F),   // Capricorn color
        "11th House" to Color(0xFF00FFFF),   // Aquarius color
        "12th House" to Color(0xFF87CEFA)    // Pisces color
    )

    Column(Modifier.padding(16.dp)) {
        Text("House Scores", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(8.dp))

        // Segmented control
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            sortOptions.forEachIndexed { index, label ->
                val isSelected = selectedSort == index
                Button(
                    onClick = { selectedSort = index },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.LightGray
                    )
                ) {
                    Text(label, color = if (isSelected) Color.White else Color.Black)
                }
            }
        }

        Spacer(Modifier.height(12.dp))

        sortedScores.forEach { (name, _, score) ->
            val color = houseColors[name] ?: Color.Gray
            val glyph = houseGlyphs[name] ?: ""

            // Calculate the actual percentage relative to total score
            val actualPercent = (score / totalScore * 100).toFloat()

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$glyph $name",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.width(80.dp)
                )
                Box(
                    Modifier
                        .weight(1f)
                        .height(24.dp)
                        .background(Color.DarkGray)
                ) {
                    Box(
                        Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(score / maxScore)
                            .background(color),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            text = "%.1f%%".format(actualPercent),
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(start = 4.dp),
                            color = Color.White
                        )
                    }
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "%.1f".format(score),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
        }
    }
}

@Composable
fun AspectScoresTab(aspectTriples: List<Triple<Kind, Float, Float>>) {
    var selectedSort by remember { mutableStateOf(0) }
    val sortOptions = listOf("By Strength", "Conventional")

    val conventionalAspectOrder = listOf(
        Kind.Conjunction, Kind.Opposition, Kind.Trine, Kind.Square, Kind.Sextile,
        Kind.Semisextile, Kind.Semisquare, Kind.Sesquisquare, Kind.Inconjunction, Kind.Parallel
    )

    val sortedScores = when (selectedSort) {
        0 -> aspectTriples.sortedByDescending { it.third }
        else -> aspectTriples.sortedBy { conventionalAspectOrder.indexOf(it.first) }
    }

    // Calculate total score for proper percentage calculation
    val totalScore = aspectTriples.sumOf { it.third.toDouble() }
    // Find maximum score for scaling the bars appropriately
    val maxScore = aspectTriples.maxOfOrNull { it.third } ?: 1f

    val aspectColors = mapOf(
        Kind.Conjunction to Color(0xFFBA55D3),
        Kind.Semisextile to Color(0xFF00CED1),
        Kind.Sextile to Color(0xFFFFD700),
        Kind.Square to Color(0xFFFF4500),
        Kind.Trine to Color(0xFF4B0082),
        Kind.Inconjunction to Color(0xFF87CEFA),
        Kind.Semisquare to Color(0xFF00BFFF),
        Kind.Opposition to Color(0xFFFFA500),
        Kind.Sesquisquare to Color(0xFF00FF7F),
        Kind.Parallel to Color(0xFF8A2BE2)
    )

    Column(Modifier.padding(16.dp)) {
        Text("Aspect Scores", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(8.dp))

        // Segmented control
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            sortOptions.forEachIndexed { index, label ->
                val isSelected = selectedSort == index
                Button(
                    onClick = { selectedSort = index },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.LightGray
                    )
                ) {
                    Text(label, color = if (isSelected) Color.White else Color.Black)
                }
            }
        }

        Spacer(Modifier.height(12.dp))

        sortedScores.forEach { (kind, _, score) ->
            val color = aspectColors[kind] ?: Color.Gray
            val glyph = kind.symbol

            // Calculate the actual percentage relative to total score
            val actualPercent = (score / totalScore * 100).toFloat()

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$glyph ${kind.description.capitalize()}",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.width(100.dp)
                )
                Box(
                    Modifier
                        .weight(1f)
                        .height(24.dp)
                        .background(Color.DarkGray)
                ) {
                    Box(
                        Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(score / maxScore)
                            .background(color),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            text = "%.1f%%".format(actualPercent),
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(start = 4.dp),
                            color = Color.White
                        )
                    }
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "%.1f".format(score),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
        }
    }
}