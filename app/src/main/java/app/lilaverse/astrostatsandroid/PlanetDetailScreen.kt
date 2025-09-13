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
import java.util.Locale

import app.lilaverse.astrostatsandroid.AstroColors
import app.lilaverse.astrostatsandroid.Zodiac

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanetDetailScreen(chart: Chart, planet: CelestialObject, navController: NavController? = null) {
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
    val coordinate = remember(planet) { bodies.firstOrNull { it.body == planet } }
    val houseNumber = remember(coordinate) { coordinate?.let { chartCake.houseCusps.houseForLongitude(it.longitude) } }
    val houseScoreMap = remember(calculator) { calculator.getHouseScoreForPlanetsCo(bodies) }
    val houseScore = coordinate?.let { houseScoreMap[it] }
    val totalPowerScores = remember(calculator) { calculator.getTotalPowerScoresForPlanetsCo(bodies) }
    val planetScores = remember(totalPowerScores) { totalPowerScores.mapKeys { it.key.body } }
    val planetScore = planetScores[planet]
    val aspectScoresByAspect = remember(calculator) { calculator.allCelestialAspectScoresByAspect(bodies) }
    val planetAspects = remember(planet) {
        aspectScoresByAspect.filter { (aspect, _) ->
            aspect.body1.body == planet || aspect.body2.body == planet
        }.toList().sortedByDescending { it.second }
    }

    var selectedTab by remember { mutableStateOf(0) }

    Column(Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text(text = "${planet.keyName} Details") },
            navigationIcon = {
                navController?.let {
                    IconButton(onClick = { it.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            }
        )

        Column(Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                val symbol = (planet as? CelestialObject.Planet)?.planet?.symbol ?: ""
                Text(text = symbol, fontSize = 32.sp)
                Spacer(Modifier.width(8.dp))
                Text(text = planet.keyName, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            }
            coordinate?.let {
                Spacer(Modifier.height(4.dp))
                Text(text = it.formatAsSignDegree(), style = MaterialTheme.typography.bodyMedium)
            }
            planetScore?.let {
                Spacer(Modifier.height(2.dp))
                Text("Total power: ${"%.1f".format(it)}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Spacer(Modifier.height(16.dp))
            TabRow(selectedTabIndex = selectedTab) {
                Tab(selected = selectedTab == 0, onClick = { selectedTab = 0 }, text = { Text("Scoring Details") })
                Tab(selected = selectedTab == 1, onClick = { selectedTab = 1 }, text = { Text("Correspondences") })
            }
        }

        when (selectedTab) {
            0 -> {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp)
                ) {
                    item {
                        HouseCard(houseNumber = houseNumber, coordinate = coordinate, houseScore = houseScore, chartCake = chartCake)
                        Spacer(Modifier.height(12.dp))
                    }
                    item { Text("Aspects", fontWeight = FontWeight.Bold) }
                    if (planetAspects.isNotEmpty()) {
                        items(planetAspects) { (aspect, score) ->
                            AspectCard(aspect = aspect, score = score, chartCake = chartCake)
                            Spacer(Modifier.height(8.dp))
                        }
                        item {
                            val total = planetAspects.sumOf { it.second }
                            Text("Total Aspect Power: ${"%.2f".format(total)}", fontWeight = FontWeight.Bold)
                        }
                    } else {
                        item { Text("No significant aspects", color = MaterialTheme.colorScheme.onSurfaceVariant) }
                    }
                }
            }
            1 -> {
                val correspondences = getPlanetCorrespondences(planet.keyName)
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp)
                ) {
                    items(correspondences.toList()) { (key, value) ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                        ) {
                            Column(Modifier.padding(12.dp)) {
                                Text(key, fontWeight = FontWeight.SemiBold)
                                Spacer(Modifier.height(2.dp))
                                Text(value)
                            }
                        }
                        Spacer(Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun HouseCard(
    houseNumber: Int?,
    coordinate: Coordinate?,
    houseScore: Double?,
    chartCake: ChartCake
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        if (houseNumber != null && coordinate != null) {
            val cusp = chartCake.houseCusps.getCusp(houseNumber - 1)
            val cuspSign = Zodiac.fromName(cusp.sign)
            Column(Modifier.padding(12.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("House $houseNumber", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                    cuspSign?.let {
                        Spacer(Modifier.width(8.dp))
                        Text(it.glyph, fontSize = 18.sp)
                    }
                }
                cuspSign?.let { sign ->
                    Spacer(Modifier.height(4.dp))
                    Text("Cusp Sign: ${sign.name}", style = MaterialTheme.typography.bodyMedium)
                }
                houseScore?.let {
                    Spacer(Modifier.height(8.dp))
                    Text("House Score: ${"%.2f".format(it)}", style = MaterialTheme.typography.bodyMedium)
                }
            }
        } else {
            Column(Modifier.padding(12.dp)) {
                Text("House data not available", color = MaterialTheme.colorScheme.onSurfaceVariant)
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
                    Text(aspect.kind.symbol, fontSize = 20.sp, color = AstroColors.aspectColor(aspect.kind.description.capitalize(Locale.getDefault())))
                    Text(aspect.kind.description.capitalize(Locale.getDefault()), fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
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

fun getPlanetCorrespondences(name: String): Map<String, String> {
    return when (name.lowercase()) {
        "sun" -> mapOf(
            "Urge" to "The urge for significance, for power",
            "Deity" to "Apollo, Helios, Ra, Mithras",
            "Body Parts" to "Heart, spine, vitality, arterial blood, circulation",
            "Diseases" to "Cardiovascular issues, fever, inflammation, hypertension",
            "Gems" to "Diamond, ruby, amber, topaz, sunstone",
            "Plants" to "Sunflower, marigold, saffron, cinnamon, bay laurel",
            "Metal" to "Gold",
            "Colors" to "Orange, gold",
            "Musical Note" to "D",
            "Day" to "Sunday"
        )
        "moon" -> mapOf(
            "Urge" to "The urge for change, for domestic security, for emotional expression",
            "Deity" to "Selene, Diana, Artemis, Isis",
            "Body Parts" to "Breasts, stomach, lymph, digestive fluids, cerebrospinal fluids",
            "Diseases" to "Digestive disorders, fluid retention, hormonal imbalances",
            "Gems" to "Pearl, moonstone, crystal, opal, selenite",
            "Plants" to "Cucumber, melon, cabbage, pumpkin, lily, poppy",
            "Element" to "Water",
            "Metal" to "Silver",
            "Colors" to "Silver, white, pale green, cream",
            "Musical Note" to "E",
            "Day" to "Monday"
        )
        "mercury" -> mapOf(
            "Urge" to "The urge for expression, for mental activity, for communication",
            "Deity" to "Hermes, Thoth, Mercury",
            "Body Parts" to "Brain, nervous system, hands, lungs, speech organs",
            "Diseases" to "Nervous disorders, respiratory issues, coordination problems",
            "Gems" to "Agate, opal, variegated stones, quicksilver",
            "Plants" to "Lavender, marjoram, parsley, valerian, dill",
            "Element" to "Air",
            "Metal" to "Mercury, quicksilver",
            "Colors" to "Purple, violet, mixed colors",
            "Musical Note" to "E",
            "Day" to "Wednesday"
        )
        "venus" -> mapOf(
            "Urge" to "The urge for companionship, for love, for beauty, for art",
            "Deity" to "Aphrodite, Venus, Ishtar, Freya",
            "Body Parts" to "Throat, kidneys, veins, thymus, sense of touch",
            "Diseases" to "Throat problems, kidney issues, diabetes, thyroid disorders",
            "Gems" to "Emerald, jade, coral, rose quartz, turquoise",
            "Plants" to "Rose, apple, strawberry, lily, mint",
            "Element" to "Earth/Air",
            "Metal" to "Copper",
            "Colors" to "Green, pink, pastel blue",
            "Musical Note" to "A",
            "Day" to "Friday"
        )
        "mars" -> mapOf(
            "Urge" to "The urge for aggression, for action, for conquest, for vigor",
            "Deity" to "Ares, Mars, Tyr",
            "Body Parts" to "Muscles, head, adrenal glands, genitals, red blood cells",
            "Diseases" to "Inflammation, burns, cuts, fevers, headaches",
            "Gems" to "Ruby, bloodstone, red jasper, garnet",
            "Plants" to "Red pepper, nettle, ginger, basil, garlic",
            "Element" to "Fire",
            "Metal" to "Iron, steel",
            "Colors" to "Red, scarlet, crimson",
            "Musical Note" to "C",
            "Day" to "Tuesday"
        )
        "jupiter" -> mapOf(
            "Urge" to "The urge for expansion, for growth, for wisdom, for abundance",
            "Deity" to "Zeus, Jupiter, Thor",
            "Body Parts" to "Liver, hips, thighs, fat cells, arterial system",
            "Diseases" to "Metabolic disorders, liver issues, blood sugar problems",
            "Gems" to "Sapphire, lapis lazuli, turquoise, amethyst",
            "Plants" to "Sage, nutmeg, dandelion, oak, maple",
            "Element" to "Fire/Air",
            "Metal" to "Tin",
            "Colors" to "Royal blue, purple, indigo",
            "Musical Note" to "F#",
            "Day" to "Thursday"
        )
        "saturn" -> mapOf(
            "Urge" to "The urge for security, for stability, for structure, for discipline",
            "Deity" to "Chronos, Saturn, Shani",
            "Body Parts" to "Bones, teeth, skin, knees, skeletal system",
            "Diseases" to "Arthritis, bone issues, chronic conditions, depression",
            "Gems" to "Onyx, jet, obsidian, black tourmaline",
            "Plants" to "Cypress, ivy, nightshade, hemlock, yew",
            "Element" to "Earth",
            "Metal" to "Lead",
            "Colors" to "Black, dark blue, gray, brown",
            "Musical Note" to "G",
            "Day" to "Saturday"
        )
        "uranus" -> mapOf(
            "Urge" to "The urge for freedom, for change, for originality, for revolution",
            "Deity" to "Uranus, Prometheus, Varuna",
            "Body Parts" to "Nervous system, ankles, bioelectric currents",
            "Diseases" to "Spasms, neurological disorders, electrical imbalances",
            "Gems" to "Aquamarine, amber, uranium glass, fluorite",
            "Plants" to "Clover, birch, aspen, unusual plants",
            "Element" to "Air/Fire",
            "Metal" to "Uranium, aluminum",
            "Colors" to "Electric blue, neon colors, ultraviolet",
            "Musical Note" to "D#",
            "Day" to "None (modern planet)"
        )
        "neptune" -> mapOf(
            "Urge" to "The urge for unity, for transcendence, for idealism, for dissolution",
            "Deity" to "Poseidon, Neptune, Vishnu",
            "Body Parts" to "Pineal gland, feet, lymphatic system, psychic centers",
            "Diseases" to "Addictions, mysterious ailments, foot problems, immune disorders",
            "Gems" to "Moonstone, aquamarine, pearl, coral",
            "Plants" to "Lotus, seaweed, water lily, marijuana, mushrooms",
            "Element" to "Water",
            "Metal" to "Neptune (hypothetical)",
            "Colors" to "Sea green, misty blue, lavender",
            "Musical Note" to "A#",
            "Day" to "None (modern planet)"
        )
        "pluto" -> mapOf(
            "Urge" to "The urge for power, for transformation, for rebirth, for elimination",
            "Deity" to "Hades, Pluto, Shiva, Kali",
            "Body Parts" to "Reproductive organs, cellular regeneration, waste elimination",
            "Diseases" to "Degenerative disorders, obsessions, power-related illnesses",
            "Gems" to "Obsidian, black tourmaline, plutonium",
            "Plants" to "Scorpion grass, blackthorn, deadly nightshade",
            "Element" to "Water/Fire",
            "Metal" to "Plutonium",
            "Colors" to "Black, dark red, deep purple",
            "Musical Note" to "C#",
            "Day" to "None (modern planet)"
        )
        else -> mapOf("Information" to "No correspondences available")
    }
}