package app.lilaverse.astrostatsandroid

import android.util.Log
import kotlin.math.abs

val orbDictionary: Map<String, Double> = mapOf(
    "angular_conjunction" to 12.0,
    "angular_semisextile" to 3.0,
    "angular_sextile" to 7.0,
    "angular_quintile" to 5.0,
    "angular_square" to 10.0,
    "angular_trine" to 10.0,
    "angular_inconjunction" to 3.0,
    "angular_semisquare" to 5.0,
    "angular_sesquisquare" to 5.0,
    "angular_biquintile" to 3.0,
    "angular_opposition" to 12.0,
    "angular_parallel" to 1.0,

    "lum_angular_conjunction" to 15.0,
    "lum_angular_semisextile" to 4.0,
    "lum_angular_sextile" to 8.0,
    "lum_angular_quintile" to 6.0,
    "lum_angular_square" to 12.0,
    "lum_angular_trine" to 12.0,
    "lum_angular_inconjunction" to 4.0,
    "lum_angular_semisquare" to 6.0,
    "lum_angular_sesquisquare" to 6.0,
    "lum_angular_biquintile" to 4.0,
    "lum_angular_opposition" to 15.0,
    "lum_angular_parallel" to 1.0,

    "succedent_conjunction" to 10.0,
    "succedent_semisextile" to 2.0,
    "succedent_sextile" to 6.0,
    "succedent_quintile" to 4.0,
    "succedent_square" to 8.0,
    "succedent_trine" to 8.0,
    "succedent_inconjunction" to 2.0,
    "succedent_semisquare" to 4.0,
    "succedent_sesquisquare" to 4.0,
    "succedent_biquintile" to 2.0,
    "succedent_opposition" to 10.0,
    "succedent_parallel" to 1.0,

    "lum_succedent_conjunction" to 13.0,
    "lum_succedent_semisextile" to 3.0,
    "lum_succedent_sextile" to 7.0,
    "lum_succedent_quintile" to 5.0,
    "lum_succedent_square" to 10.0,
    "lum_succedent_trine" to 10.0,
    "lum_succedent_inconjunction" to 3.0,
    "lum_succedent_semisquare" to 5.0,
    "lum_succedent_sesquisquare" to 5.0,
    "lum_succedent_biquintile" to 3.0,
    "lum_succedent_opposition" to 13.0,
    "lum_succedent_parallel" to 1.0,

    "cadent_conjunction" to 10.0,
    "cadent_semisextile" to 1.0,
    "cadent_sextile" to 5.0,
    "cadent_quintile" to 3.0,
    "cadent_square" to 6.0,
    "cadent_trine" to 6.0,
    "cadent_inconjunction" to 1.0,
    "cadent_semisquare" to 3.0,
    "cadent_sesquisquare" to 3.0,
    "cadent_biquintile" to 1.0,
    "cadent_opposition" to 8.0,
    "cadent_parallel" to 1.0,

    "lum_cadent_conjunction" to 11.0,
    "lum_cadent_semisextile" to 2.0,
    "lum_cadent_sextile" to 6.0,
    "lum_cadent_quintile" to 4.0,
    "lum_cadent_square" to 10.0,
    "lum_cadent_trine" to 8.0,
    "lum_cadent_inconjunction" to 2.0,
    "lum_cadent_semisquare" to 4.0,
    "lum_cadent_sesquisquare" to 4.0,
    "lum_cadent_biquintile" to 2.0,
    "lum_cadent_opposition" to 11.0,
    "lum_cadent_parallel" to 1.0
)

val housePower = listOf(15.0, 8.5, 8.0, 14.0, 7.5, 7.0, 14.5, 10.9, 10.0, 15.0, 11.9, 9.3)
val houseVar = listOf(2.0, 0.5, 0.5, 2.0, 0.5, 0.5, 2.0, 0.9, 0.7, 2.0, 1.0, 0.7)

data class HouseCusp(val number: Int, val value: Double)

class PlanetStrengthCalculator(
    private val orbDictionary: Map<String, Double>,
    private val houseProvider: (Coordinate) -> Int,
    private val luminaryChecker: (Coordinate) -> Boolean,
    private val houseCuspsProvider: (Double) -> HouseCusp,
    private val houseCuspValues: List<Double>
) {
    fun getTotalPowerScoresForPlanetsCo(bodies: List<Coordinate>): Map<Coordinate, Double> {
        Log.d("DeclinationDebug", "🔍 Logging Declinations for All Coordinates:")
        bodies.forEach { body ->
            val name = body.body.keyName
            val lon = String.format("%.2f", body.longitude)
            val dec = String.format("%.2f", body.declination)
            val hemisphere = if (body.declination >= 0) "N" else "S"
            Log.d("DeclinationDebug", "🌐 $name → Lon: $lon° | Dec: $dec° $hemisphere")
            if (body.declination == 0.0 && name != "Ascendant" && name != "Midheaven") {
                Log.w("DeclinationDebug", "⚠️ $name has declination = 0.0 — possible missing calc")
            }
        }

        val aspectScores = allCelestialAspectScoresCo(bodies)
        val houseScores = getHouseScoreForPlanetsCo(bodies)
        return (aspectScores.keys + houseScores.keys).associateWith { body ->
            val a = aspectScores[body] ?: 0.0
            val h = houseScores[body] ?: 0.0
            Log.d("PowerCalc", "🔍 ${body.body.keyName} → Aspect: %.2f | House: %.2f | Total: %.2f".format(a, h, a + h))
            a + h
        }
    }


    fun getHouseScoreForPlanetsCo(bodies: List<Coordinate>): Map<Coordinate, Double> {
        return bodies.associateWith { getHouseScore(it) }
    }

    private fun getHouseScore(body: Coordinate): Double {
        val key = body.body.keyName
        if (key == "Ascendant" || key == "Midheaven") {
            Log.d("HouseScore", "🔒 $key is fixed at 15.0")
            return 15.0
        }

        val house = houseProvider(body)
        val power = housePower[house - 1]
        val variation = houseVar[house - 1]
        val size = getHouseSizeArray()[house - 1]
        val cusp = houseCuspsProvider(body.longitude)
        val offset = abs(body.longitude - cusp.value)
        val score = power - (offset * (variation / size))

        Log.d("HouseScore", "🏠 ${body.body.keyName}: House=$house | Cusp=${cusp.value} | Lon=${body.longitude} | Offset=$offset | Power=$power | Var=$variation | Size=$size → Score=$score")

        return score
    }

    private fun getHouseSizeArray(): List<Double> {
        return houseCuspValues.mapIndexed { i, current ->
            val next = if (i == houseCuspValues.lastIndex) houseCuspValues[0] else houseCuspValues[i + 1]
            val dist = (next - current + 360) % 360
            Log.d("HouseSize", "House ${i + 1} size: $dist")
            dist
        }
    }

    fun allCelestialAspectScoresCo(bodies: List<Coordinate>): Map<Coordinate, Double> {
        val scoreMap = mutableMapOf<Coordinate, Double>()
        allCelestialAspectScoresByAspect(bodies).forEach { (aspect, score) ->
            Log.d("AspectScore", "🔗 ${aspect.body1.body.keyName} - ${aspect.body2.body.keyName} (${aspect.kind}) → Orb=${aspect.orbDelta} | Score=$score")
            scoreMap[aspect.body1] = (scoreMap[aspect.body1] ?: 0.0) + score
            scoreMap[aspect.body2] = (scoreMap[aspect.body2] ?: 0.0) + score
        }
        return scoreMap
    }

    fun allCelestialAspectScoresByAspect(bodies: List<Coordinate>): Map<CelestialAspect, Double> {
        val scoreDict = mutableMapOf<CelestialAspect, Double>()
        val aspects = allRickysNatalAspects(bodies)
        val (parallels, nonParallels) = aspects.partition { it.kind == Kind.Parallel }

        for (aspect in parallels) {
            val p1 = aspect.body1
            val p2 = aspect.body2
            val h1 = houseProvider(p1)
            val h2 = houseProvider(p2)
            val isLum1 = luminaryChecker(p1)
            val isLum2 = luminaryChecker(p2)

            val maxOrb = getParallelOrbCategory(h1, h2, isLum1, isLum2)

            val decl1 = abs(p1.declination)
            val decl2 = abs(p2.declination)
            val actualOrb = abs(decl1 - decl2)

            val score = (1 - actualOrb) * maxOrb

            Log.d(
                "AspectParallel",
                "💠 ${p1.body.keyName}-${p2.body.keyName}: | Dec1=$decl1 | Dec2=$decl2 | Orb=$actualOrb | MaxOrb=$maxOrb | Score=$score"
            )
            scoreDict[aspect] = score
        }


        for (aspect in nonParallels) {
            val p1 = aspect.body1
            val p2 = aspect.body2
            val h1 = houseProvider(p1)
            val h2 = houseProvider(p2)
            val cat1 = listOf("angular", "succedent", "cadent")[(h1 - 1) % 3]
            val cat2 = listOf("angular", "succedent", "cadent")[(h2 - 1) % 3]
            val prefix1 = if (luminaryChecker(p1)) "lum_$cat1" else cat1
            val prefix2 = if (luminaryChecker(p2)) "lum_$cat2" else cat2
            val key1 = "${prefix1}_${aspect.kind.name.lowercase()}"
            val key2 = "${prefix2}_${aspect.kind.name.lowercase()}"
            val orb1 = orbDictionary[key1] ?: 0.0
            val orb2 = orbDictionary[key2] ?: 0.0
            val maxOrb = maxOf(orb1, orb2)
            val actualOrb = aspect.orbDelta
            val score = (maxOrb - abs(actualOrb)).coerceAtLeast(0.0)

            Log.d("AspectStandard", "🌀 ${p1.body.keyName}-${p2.body.keyName} (${aspect.kind}) | Keys=[$key1,$key2] | Orb=${actualOrb} | Max=$maxOrb | Score=$score")
            scoreDict[aspect] = score
        }

        return scoreDict
    }

    fun allRickysNatalAspects(bodies: List<Coordinate>): List<CelestialAspect> =
        allRegularNatalAspects(bodies) + allParallelAspects(bodies)

    fun allRegularNatalAspects(bodies: List<Coordinate>): List<CelestialAspect> {
        val aspects = mutableListOf<CelestialAspect>()
        for (i in bodies.indices) {
            for (j in i + 1 until bodies.size) {
                val a = getOrbCategory(bodies[i], bodies[j])
                if (a != null && !aspects.contains(a.second)) aspects.add(a.second)
            }
        }
        return aspects
    }
    fun totalScoresByAspectType(bodies: List<Coordinate>): List<Pair<Kind, Double>> {
        val scoresByAspect = allCelestialAspectScoresByAspect(bodies)

        val aspectScoreTotals: MutableMap<Kind, Double> = mutableMapOf()

        for ((aspect, score) in scoresByAspect) {
            val kind = aspect.kind
            aspectScoreTotals[kind] = (aspectScoreTotals[kind] ?: 0.0) + score
        }

        return aspectScoreTotals.entries
            .sortedByDescending { it.value }
            .map { it.key to it.value }
    }

    fun allParallelAspects(bodies: List<Coordinate>): List<CelestialAspect> {
        val orb = 1.0
        val parallels = mutableListOf<CelestialAspect>()

        for (i in bodies.indices) {
            val b1 = bodies[i]
            for (j in i + 1 until bodies.size) {
                val b2 = bodies[j]

                val dec1Abs = abs(b1.declination)
                val dec2Abs = abs(b2.declination)
                val orbDelta = abs(dec1Abs - dec2Abs)

                if (orbDelta <= orb) {
                    val aspect = CelestialAspect.fromCoordinates(b1, b2, orb)
                    if (aspect != null) {
                        aspect.kind = Kind.Parallel // Treat both parallel and contraparallel identically
                        parallels.add(aspect)
                        Log.d(
                            "AspectParallel",
                            "💠 ${b1.body.keyName}-${b2.body.keyName}: | Dec1=${b1.declination} | Dec2=${b2.declination} | Orb=$orbDelta"
                        )
                    }
                }
            }
        }

        return parallels
    }


    fun getOrbCategory(p1: Coordinate, p2: Coordinate): Pair<Double, CelestialAspect>? {
        if (p1 == p2) return null
        val house1 = houseProvider(p1)
        val house2 = houseProvider(p2)
        val cat1 = listOf("angular", "succedent", "cadent")[(house1 - 1) % 3]
        val cat2 = listOf("angular", "succedent", "cadent")[(house2 - 1) % 3]
        val prefix1 = if (luminaryChecker(p1)) "lum_${cat1}" else cat1
        val prefix2 = if (luminaryChecker(p2)) "lum_${cat2}" else cat2

        for (kind in Kind.values().filter { it != Kind.Parallel }) {
            val key1 = "${prefix1}_${kind.name.lowercase()}"
            val key2 = "${prefix2}_${kind.name.lowercase()}"
            val orb1 = orbDictionary[key1] ?: continue
            val orb2 = orbDictionary[key2] ?: continue
            val maxOrb = maxOf(orb1, orb2)
            val a = CelestialAspect.fromCoordinates(p1, p2, maxOrb)
            if (a != null && a.kind == kind) return Pair(maxOrb, a)
        }
        return null
    }

    private fun getParallelOrbCategory(h1: Int, h2: Int, isLum1: Boolean, isLum2: Boolean): Double {
        val cat1 = listOf("angular", "succedent", "cadent")[(h1 - 1) % 3]
        val cat2 = listOf("angular", "succedent", "cadent")[(h2 - 1) % 3]
        val prefix1 = if (isLum1) "lum_${cat1}" else cat1
        val prefix2 = if (isLum2) "lum_${cat2}" else cat2
        return maxOf(
            orbDictionary["${prefix1}_parallel"] ?: 1.0,
            orbDictionary["${prefix2}_parallel"] ?: 1.0
        )
    }
}
