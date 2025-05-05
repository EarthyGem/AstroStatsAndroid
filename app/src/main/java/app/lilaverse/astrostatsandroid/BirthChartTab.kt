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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import app.lilaverse.astrostatsandroid.HouseCusps


@Composable
fun BirthChartTab(chart: Chart) {
    Column(Modifier.padding(16.dp)) {
        Text("☉ Sun: ${chart.sunSign}")
        Text("☽ Moon: ${chart.moonSign}")
        Text("↑ Rising: ${chart.risingSign}")
        Spacer(Modifier.height(16.dp))
        Text("Chart wheel goes here")
    }
}


@Composable
fun PlanetScoresTab(houseCusps: HouseCusps) {

    var selectedSort by remember { mutableStateOf(0) } // 0 = Strength, 1 = Conventional
    val sortOptions = listOf("By Strength", "Conventional")

    val planetScores = listOf(
        Triple(CelestialObject.Planet(Planet.Mercury), 10.2f, 56.5f),
        Triple(CelestialObject.ascendantFrom(houseCusps.getCusp(0)), 9.7f, 53.9f),
        Triple(CelestialObject.midheavenFrom(houseCusps.getCusp(9)), 9.4f, 52.2f),
        Triple(CelestialObject.Planet(Planet.Moon), 9.2f, 51.1f),
        Triple(CelestialObject.Planet(Planet.Mars), 8.5f, 47.0f),
        Triple(CelestialObject.Planet(Planet.Uranus), 8.2f, 45.7f),
        Triple(CelestialObject.Planet(Planet.Sun), 8.0f, 44.1f),
        Triple(CelestialObject.Planet(Planet.Jupiter), 7.9f, 44.1f),
        Triple(CelestialObject.Planet(Planet.Venus), 6.8f, 37.7f),
        Triple(CelestialObject.Planet(Planet.Pluto), 6.8f, 37.7f),
        Triple(CelestialObject.Planet(Planet.Saturn), 5.7f, 31.8f),
        Triple(CelestialObject.Planet(Planet.Neptune), 5.4f, 29.9f),
        Triple(CelestialObject.SouthNode, 4.2f, 23.3f)
    )

    val conventionalOrder = listOf(
        "Sun", "Moon", "Mercury", "Venus", "Mars", "Jupiter", "Saturn",
        "Uranus", "Neptune", "Pluto", "Ascendant", "Midheaven", "South Node"
    )

    val sortedScores = when (selectedSort) {
        0 -> planetScores.sortedByDescending { it.second }
        else -> planetScores.sortedBy { conventionalOrder.indexOf(it.first.keyName) }
    }

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

        sortedScores.forEach { (obj, percent, score) ->
            val (name, symbol) = when {
                obj is CelestialObject.Planet -> obj.planet.keyName to obj.planet.symbol
                obj is CelestialObject.Cusp && obj == CelestialObject.ascendantFrom(houseCusps.getCusp(0)) -> "Ascendant" to "↑"
                obj is CelestialObject.Cusp && obj == CelestialObject.midheavenFrom(houseCusps.getCusp(9)) -> "Midheaven" to "⊗"
                obj == CelestialObject.SouthNode -> "South Node" to "☋"
                obj is CelestialObject.Cusp -> obj.keyName to ""
                else -> "Unknown" to "?"
            }


            val color = planetColors[name] ?: Color.Gray

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
                            .fillMaxWidth(percent / 15f)
                            .background(color),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            text = "${percent}%",
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
fun SignScoresTab() {
    var selectedSort by remember { mutableStateOf(0) } // 0 = Strength, 1 = Conventional
    val sortOptions = listOf("By Strength", "Conventional")

    val signScores = listOf(
        Triple(Zodiac.signAt(0), 6.5f, 38.3f),   // Aries
        Triple(Zodiac.signAt(1), 2.9f, 17.3f),   // Taurus
        Triple(Zodiac.signAt(2), 7.9f, 47.0f),   // Gemini
        Triple(Zodiac.signAt(3), 10.3f, 61.2f),  // Cancer
        Triple(Zodiac.signAt(4), 7.3f, 43.4f),   // Leo
        Triple(Zodiac.signAt(5), 4.8f, 28.3f),   // Virgo
        Triple(Zodiac.signAt(6), 7.3f, 43.1f),   // Libra
        Triple(Zodiac.signAt(7), 9.7f, 57.6f),   // Scorpio
        Triple(Zodiac.signAt(8), 6.5f, 38.7f),   // Sagittarius
        Triple(Zodiac.signAt(9), 9.2f, 54.7f),   // Capricorn
        Triple(Zodiac.signAt(10), 2.7f, 15.8f),  // Aquarius
        Triple(Zodiac.signAt(11), 24.7f, 146.1f) // Pisces
    )

    val sortedScores = when (selectedSort) {
        0 -> signScores.sortedByDescending { it.second }
        else -> signScores // Conventional = default order
    }

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

        sortedScores.forEach { (sign, percent, score) ->
            val color = signColors[sign.name] ?: Color.Gray
            val glyph = sign.glyph

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
                            .fillMaxWidth(percent / 15f)
                            .background(color),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            text = "${percent}%",
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
fun HouseScoresTab() {
    var selectedSort by remember { mutableStateOf(0) } // 0 = Strength, 1 = Conventional
    val sortOptions = listOf("By Strength", "Conventional")
    val houseGlyphs = mapOf(
        "Aries" to "♈", "Taurus" to "♉", "Gemini" to "♊", "Cancer" to "♋",
        "Leo" to "♌", "Virgo" to "♍", "Libra" to "♎", "Scorpio" to "♏",
        "Sagittarius" to "♐", "Capricorn" to "♑", "Aquarius" to "♒", "Pisces" to "♓",
        "AC" to "↑", "MC" to "⊗", "South Node" to "☋"
    )

    val houseScores = listOf(
        Triple("1st House", 14.8f, 120.9f),
        Triple("2nd House", 14.7f, 120.0f),
        Triple("3rd House", 9.7f, 79.6f),
        Triple("4th House", 2.9f, 23.5f),
        Triple("5th House", 2.3f, 18.9f),
        Triple("6th House", 8.9f, 72.4f),
        Triple("7th House", 8.9f, 72.5f),
        Triple("8th House", 6.6f, 53.8f),
        Triple("9th House", 8.1f, 66.0f),
        Triple("10th House", 14.3f, 116.8f),
        Triple("11th House", 2.6f, 21.2f),
        Triple("12th House", 6.4f, 51.9f)
    )
    val sortedScores = when (selectedSort) {
        0 -> houseScores.sortedByDescending { it.second } // by strength %
        else -> houseScores // leave as is for conventional order
    }

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

        sortedScores.forEach { (name, percent, score) ->
            val color = houseColors[name] ?: Color.Gray
            val glyph = houseGlyphs[name] ?: ""

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
                            .fillMaxWidth(percent / 15f)
                            .background(color),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            text = "${percent}%",
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
fun AspectScoresTab() {
    var selectedSort by remember { mutableStateOf(0) } // 0 = Strength, 1 = Conventional
    val sortOptions = listOf("By Strength", "Conventional")

    val aspectScores = listOf(
        Triple(Kind.Square, 25.9f, 53.7f),
        Triple(Kind.Trine, 24.5f, 50.9f),
        Triple(Kind.Conjunction, 14.5f, 30.0f),
        Triple(Kind.Parallel, 12.0f, 24.8f),
        Triple(Kind.Sextile, 9.7f, 20.1f),
        Triple(Kind.Opposition, 5.0f, 10.4f),
        Triple(Kind.Sesquisquare, 3.1f, 6.4f),
        Triple(Kind.Semisquare, 2.1f, 4.4f),
        Triple(Kind.Semisextile, 1.9f, 3.9f),
        Triple(Kind.Inconjunction, 1.4f, 3.0f)
    )

    val conventionalAspectOrder = listOf(
        Kind.Conjunction, Kind.Opposition, Kind.Trine, Kind.Square, Kind.Sextile,
        Kind.Semisextile, Kind.Semisquare, Kind.Sesquisquare, Kind.Inconjunction, Kind.Parallel
    )

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

    val sortedScores = when (selectedSort) {
        0 -> aspectScores.sortedByDescending { it.second }
        else -> aspectScores.sortedBy { conventionalAspectOrder.indexOf(it.first) }
    }

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

        sortedScores.forEach { (kind, percent, score) ->
            val color = aspectColors[kind] ?: Color.Gray
            val glyph = kind.symbol

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
                            .fillMaxWidth(percent / 15f)
                            .background(color),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            text = "${percent}%",
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
