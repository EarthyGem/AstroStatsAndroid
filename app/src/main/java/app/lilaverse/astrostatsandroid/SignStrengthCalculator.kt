package app.lilaverse.astrostatsandroid

class SignStrengthCalculator(
    private val chart: ChartCake,
    private val totalPowerScores: Map<Coordinate, Double>
) {

    fun calculateTotalSignScores(): Map<Zodiac.Sign, Double> {
        val planetScores = calculatePlanetSignScores()
        val unoccupiedScores = calculateUnoccupiedSignStrengths()
        val angleScores = calculateAngleSignScores()

        return Zodiac.allSigns().associateWith { sign ->
            (planetScores[sign] ?: 0.0) +
            (unoccupiedScores[sign] ?: 0.0) +
            (angleScores[sign] ?: 0.0)
        }
    }

    private fun calculatePlanetSignScores(): Map<Zodiac.Sign, Double> {
        val scores = mutableMapOf<Zodiac.Sign, Double>()
        chart.natalBodies.forEach { coord ->
            val power = totalPowerScores[coord] ?: 0.0
            scores[coord.sign] = (scores[coord.sign] ?: 0.0) + power
        }
        return scores
    }

    fun calculateUnoccupiedSignStrengths(): Map<Zodiac.Sign, Double> {
        val intercepted = chart.houseCusps.getInterceptedSigns()
        val houseSigns = chart.houseCusps.signNames().mapNotNull { Zodiac.fromName(it) }
        val rulerScores = chart.natalBodies.associateBy({ it.body }, { totalPowerScores[it] ?: 0.0 })
        val result = mutableMapOf<Zodiac.Sign, Double>()

        Zodiac.allSigns().forEach { sign ->
            val rulers = getRulersForSign(sign)
            val strengths = rulers.mapNotNull { rulerScores[it] }
            if (strengths.isEmpty()) {
                result[sign] = 0.0
                return@forEach
            }
            val avg = strengths.average()

            result[sign] = when {
                intercepted.contains(sign) -> avg * 0.25
                houseSigns.count { it == sign } > 1 -> avg * 0.5
                houseSigns.contains(sign) -> avg * 0.5
                else -> avg * 0.25
            }
        }

        return result
    }

    private fun calculateAngleSignScores(): Map<Zodiac.Sign, Double> {
        return listOf(
            chart.houseCusps.getCusp(0).sign,
            chart.houseCusps.getCusp(9).sign
        ).mapNotNull { Zodiac.fromName(it) }.associateWith { 0.0 } // Placeholder for now
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
}
