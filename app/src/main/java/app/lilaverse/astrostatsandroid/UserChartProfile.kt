package app.lilaverse.astrostatsandroid

import java.util.Date

/** Data class describing key natal chart metrics for a user. */
data class UserChartProfile(
    val name: String,
    val birthDate: Date,
    val sex: Sex,
    val strongestPlanet: CelestialObject,
    val strongestPlanetSign: Zodiac.Sign,
    val strongestPlanetHouse: Int,
    val strongestPlanetRuledHouses: List<Int>,
    val topAspectsToStrongestPlanet: List<NatalAspectScore>,
    val sunSign: Zodiac.Sign,
    val sunHouse: Int,
    val sunPower: Double,
    val topAspectsToSun: List<NatalAspectScore>,
    val moonSign: Zodiac.Sign,
    val moonHouse: Int,
    val moonPower: Double,
    val topAspectsToMoon: List<NatalAspectScore>,
    val mercurySign: Zodiac.Sign,
    val mercuryHouse: Int,
    val ascendantSign: Zodiac.Sign,
    val ascendantPower: Double,
    val topAspectsToAscendant: List<NatalAspectScore>,
    val ascendantRulerSigns: List<Zodiac.Sign>,
    val ascendantRulers: List<CelestialObject>,
    val ascendantRulerHouses: List<Int>,
    val ascendantRulerPowers: List<Double>,
    val topAspectsToAscendantRulers: List<NatalAspectScore>,
    val dominantHouseScores: Map<Int, Double>,
    val dominantSignScores: Map<Zodiac.Sign, Double>,
    val dominantPlanetScores: Map<CelestialObject, Double>,
    val mostHarmoniousPlanet: CelestialObject,
    val mostDiscordantPlanet: CelestialObject,
    val venusSign: Zodiac.Sign,
    val venusHouse: Int,
    val marsSign: Zodiac.Sign,
    val marsHouse: Int,
    val jupiterSign: Zodiac.Sign,
    val jupiterHouse: Int,
    val saturnSign: Zodiac.Sign,
    val saturnHouse: Int,
    val uranusSign: Zodiac.Sign,
    val uranusHouse: Int,
    val neptuneSign: Zodiac.Sign,
    val neptuneHouse: Int,
    val plutoSign: Zodiac.Sign,
    val plutoHouse: Int
)

enum class Sex { MALE, FEMALE, OTHER; }

data class NatalAspectScore(
    val aspect: CelestialAspect,
    val score: Double,
    val isHarmonious: Boolean
)

/** Build a comprehensive UserChartProfile for this chart. */
fun ChartCake.buildUserChartProfile(name: String = "User", sex: Sex = Sex.OTHER): UserChartProfile {
    val houseProvider: (Coordinate) -> Int = { coord ->
        val lon = if (coord.body.keyName == "Ascendant") coord.longitude + 0.5 else coord.longitude
        houseCusps.houseForLongitude(lon)
    }
    val luminaryChecker: (Coordinate) -> Boolean = { it.body.keyName in listOf("Sun", "Moon") }
    val houseCuspValues = houseCusps.allCusps().map { it.normalizedLongitude }
    val houseCuspsProvider: (Double) -> HouseCusp = { lon ->
        val h = houseCusps.houseForLongitude(lon)
        val cuspLon = houseCusps.getCusp(h - 1).normalizedLongitude
        HouseCusp(h, cuspLon)
    }

    val planetCalculator = PlanetStrengthCalculator(
        orbDictionary = orbDictionary,
        houseProvider = houseProvider,
        luminaryChecker = luminaryChecker,
        houseCuspsProvider = houseCuspsProvider,
        houseCuspValues = houseCuspValues
    )

    val planetPowerMap = planetCalculator.getTotalPowerScoresForPlanetsCo(natalBodies)
    val houseStrengths = HouseStrengthCalculator(this, planetPowerMap).calculateHouseStrengths()
    val signStrengths = SignStrengthCalculator(this, planetPowerMap).calculateTotalSignScores()

    val strongestPlanetCoord = planetPowerMap.maxByOrNull { it.value }?.key
        ?: natalBodies.first { it.body.keyName == "Sun" }
    val strongest = strongestPlanetCoord.body

    val aspectScores = planetCalculator.allCelestialAspectScoresByAspect(natalBodies)

    val sun = natalBodies.first { it.body.keyName == "Sun" }
    val moon = natalBodies.first { it.body.keyName == "Moon" }
    val mercury = natalBodies.first { it.body.keyName == "Mercury" }
    val venus = natalBodies.first { it.body.keyName == "Venus" }
    val mars = natalBodies.first { it.body.keyName == "Mars" }
    val jupiter = natalBodies.first { it.body.keyName == "Jupiter" }
    val saturn = natalBodies.first { it.body.keyName == "Saturn" }
    val uranus = natalBodies.first { it.body.keyName == "Uranus" }
    val neptune = natalBodies.first { it.body.keyName == "Neptune" }
    val pluto = natalBodies.first { it.body.keyName == "Pluto" }
    val ascendant = natalBodies.first { it.body.keyName == "Ascendant" }

    val sunHouse = houseCusps.houseForLongitude(sun.longitude)
    val moonHouse = houseCusps.houseForLongitude(moon.longitude)
    val mercuryHouse = houseCusps.houseForLongitude(mercury.longitude)
    val venusHouse = houseCusps.houseForLongitude(venus.longitude)
    val marsHouse = houseCusps.houseForLongitude(mars.longitude)
    val jupiterHouse = houseCusps.houseForLongitude(jupiter.longitude)
    val saturnHouse = houseCusps.houseForLongitude(saturn.longitude)
    val uranusHouse = houseCusps.houseForLongitude(uranus.longitude)
    val neptuneHouse = houseCusps.houseForLongitude(neptune.longitude)
    val plutoHouse = houseCusps.houseForLongitude(pluto.longitude)
    val strongestHouse = houseCusps.houseForLongitude(strongestPlanetCoord.longitude)

    val ruledHouses = calculateRuledHouses(strongestPlanetCoord, strongestHouse)

    val ascRulers = calculateAscendantRulers()
    val ascRulerCoords = ascRulers.mapNotNull { ruler ->
        natalBodies.find { it.body.keyName == ruler.keyName }
    }
    val ascRulerHouses = ascRulerCoords.map { houseCusps.houseForLongitude(it.longitude) }
    val ascRulerSigns = ascRulerCoords.map { it.sign }
    val ascRulerPowers = ascRulers.map { ruler ->
        planetPowerMap.entries.find { it.key.body.keyName == ruler.keyName }?.value ?: 0.0
    }

    val (mostHarmonious, mostDiscordant) = calculateHarmonyDiscord(aspectScores, natalBodies)

    val planetScoresByObject = planetPowerMap
        .filter { it.key.body.keyName in listOf("Sun","Moon","Mercury","Venus","Mars","Jupiter","Saturn","Uranus","Neptune","Pluto") }
        .mapKeys { it.key.body }

    return UserChartProfile(
        name = name,
        birthDate = birthDate,
        sex = sex,
        strongestPlanet = strongest,
        strongestPlanetSign = strongestPlanetCoord.sign,
        strongestPlanetHouse = strongestHouse,
        strongestPlanetRuledHouses = ruledHouses,
        topAspectsToStrongestPlanet = getTopAspects(strongest, aspectScores),
        sunSign = sun.sign,
        sunHouse = sunHouse,
        sunPower = planetPowerMap[sun] ?: 0.0,
        topAspectsToSun = getTopAspects(sun.body, aspectScores),
        moonSign = moon.sign,
        moonHouse = moonHouse,
        moonPower = planetPowerMap[moon] ?: 0.0,
        topAspectsToMoon = getTopAspects(moon.body, aspectScores),
        mercurySign = mercury.sign,
        mercuryHouse = mercuryHouse,
        ascendantSign = ascendant.sign,
        ascendantPower = planetPowerMap[ascendant] ?: 0.0,
        topAspectsToAscendant = getTopAspects(ascendant.body, aspectScores),
        ascendantRulerSigns = ascRulerSigns,
        ascendantRulers = ascRulers,
        ascendantRulerHouses = ascRulerHouses,
        ascendantRulerPowers = ascRulerPowers,
        topAspectsToAscendantRulers = ascRulers.flatMap { getTopAspects(it, aspectScores) },
        dominantHouseScores = houseStrengths,
        dominantSignScores = signStrengths,
        dominantPlanetScores = planetScoresByObject,
        mostHarmoniousPlanet = mostHarmonious,
        mostDiscordantPlanet = mostDiscordant,
        venusSign = venus.sign,
        venusHouse = venusHouse,
        marsSign = mars.sign,
        marsHouse = marsHouse,
        jupiterSign = jupiter.sign,
        jupiterHouse = jupiterHouse,
        saturnSign = saturn.sign,
        saturnHouse = saturnHouse,
        uranusSign = uranus.sign,
        uranusHouse = uranusHouse,
        neptuneSign = neptune.sign,
        neptuneHouse = neptuneHouse,
        plutoSign = pluto.sign,
        plutoHouse = plutoHouse
    )
}

// ---- Helper calculations ----

private fun ChartCake.calculateRuledHouses(
    planetCoord: Coordinate,
    ownHouse: Int
): List<Int> {
    val ruled = mutableListOf<Int>()
    val rulerships = mapOf(
        "Sun" to listOf(5),
        "Moon" to listOf(4),
        "Mercury" to listOf(3, 6),
        "Venus" to listOf(2, 7),
        "Mars" to listOf(1, 8),
        "Jupiter" to listOf(9, 12),
        "Saturn" to listOf(10, 11),
        "Uranus" to listOf(11),
        "Neptune" to listOf(12),
        "Pluto" to listOf(8)
    )
    for (i in 0 until 12) {
        val cusp = houseCusps.getCusp(i)
        val cuspSign = Zodiac.from(cusp.longitude)   // returns Zodiac.Sign (data class)
        rulerships[planetCoord.body.keyName]?.forEach { rulership: Int ->
            val rulerSign = Zodiac.signAt(rulership - 1)  // ✅ use your helper, not values()
            if (cuspSign == rulerSign && (i + 1) != ownHouse) {
                ruled.add(i + 1)
            }
        }
    }
    return ruled.distinct()
}



private fun ChartCake.calculateAscendantRulers(): List<CelestialObject> {
    val ascSign = Zodiac.from(houseCusps.getCusp(0).longitude)
    return rulersForSign(ascSign)
}

// Put this near your other helpers (or make it top-level util you share):
private fun rulersForSign(sign: Zodiac.Sign): List<CelestialObject> = when (sign.name.lowercase()) {
    "aries"       -> listOf(CelestialObject.Planet(Planet.Mars))
    "taurus"      -> listOf(CelestialObject.Planet(Planet.Venus))
    "gemini"      -> listOf(CelestialObject.Planet(Planet.Mercury))
    "cancer"      -> listOf(CelestialObject.Planet(Planet.Moon))
    "leo"         -> listOf(CelestialObject.Planet(Planet.Sun))
    "virgo"       -> listOf(CelestialObject.Planet(Planet.Mercury))
    "libra"       -> listOf(CelestialObject.Planet(Planet.Venus))
    "scorpio"     -> listOf(CelestialObject.Planet(Planet.Mars), CelestialObject.Planet(Planet.Pluto))
    "sagittarius" -> listOf(CelestialObject.Planet(Planet.Jupiter))
    "capricorn"   -> listOf(CelestialObject.Planet(Planet.Saturn))
    "aquarius"    -> listOf(CelestialObject.Planet(Planet.Saturn), CelestialObject.Planet(Planet.Uranus))
    "pisces"      -> listOf(CelestialObject.Planet(Planet.Jupiter), CelestialObject.Planet(Planet.Neptune))
    else          -> emptyList()
}

private fun calculateHarmonyDiscord(
    aspectScores: Map<CelestialAspect, Double>,
    natalBodies: List<Coordinate>
): Pair<CelestialObject, CelestialObject> {
    val planetScores = mutableMapOf<CelestialObject, Double>()
    natalBodies.forEach { planet ->
        var score = 0.0
        aspectScores.forEach { (aspect, s) ->
            if (aspect.body1.body.keyName == planet.body.keyName || aspect.body2.body.keyName == planet.body.keyName) {
                val harmonious = aspect.kind in Kind.harmoniousAspects
                score += if (harmonious) s else -s
            }
        }
        planetScores[planet.body] = score
    }
    val mostHarmonious = planetScores.maxByOrNull { it.value }?.key ?: CelestialObject.Planet(Planet.Venus)
    val mostDiscordant = planetScores.minByOrNull { it.value }?.key ?: CelestialObject.Planet(Planet.Mars)
    return mostHarmonious to mostDiscordant
}

private fun getTopAspects(
    celestialObject: CelestialObject,
    aspectScores: Map<CelestialAspect, Double>
): List<NatalAspectScore> = aspectScores
    .filter { (aspect, _) ->
        aspect.body1.body.keyName == celestialObject.keyName || aspect.body2.body.keyName == celestialObject.keyName
    }
    .map { (aspect, score) ->
        NatalAspectScore(aspect, score, aspect.kind in Kind.harmoniousAspects)
    }
    .sortedByDescending { it.score }
    .take(5)