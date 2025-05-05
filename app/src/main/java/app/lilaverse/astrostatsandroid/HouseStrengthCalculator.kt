package app.lilaverse.astrostatsandroid

class HouseStrengthCalculator(
    private val chart: ChartCake,
    private val totalPowerScores: Map<Coordinate, Double>
) {

    fun calculateHouseStrengths(): Map<Int, Double> {
        val unoccupiedSignStrengths = SignStrengthCalculator(chart, totalPowerScores)
            .calculateUnoccupiedSignStrengths()
        val interceptedSigns = chart.houseCusps.getInterceptedSigns()
        val planetsInHouses = calculatePlanetsInHouses()

        val houseStrengths = mutableMapOf<Int, Double>()

        chart.houseCusps.allCusps().forEachIndexed { index, cusp ->
            val houseNum = index + 1
            val sign = Zodiac.fromName(cusp.sign) ?: return@forEachIndexed
            var strength = unoccupiedSignStrengths[sign] ?: 0.0

            if (interceptedSigns.contains(sign)) {
                strength += unoccupiedSignStrengths[sign] ?: 0.0
            }

            planetsInHouses[houseNum]?.forEach { obj ->
                val coord = chart.natalBodies.find { it.body == obj }
                if (coord != null) {
                    strength += totalPowerScores[coord] ?: 0.0
                }
            }

            houseStrengths[houseNum] = strength
        }

        return houseStrengths
    }

    private fun calculatePlanetsInHouses(): Map<Int, List<CelestialObject>> {
        val result = mutableMapOf<Int, MutableList<CelestialObject>>()

        chart.natalBodies.forEach { coord ->
            val adjustedLongitude = if (coord.body.keyName == "Ascendant") {
                coord.longitude + 0.5
            } else {
                coord.longitude
            }

            val house = chart.houseCusps.houseForLongitude(adjustedLongitude)
            result.getOrPut(house) { mutableListOf() }.add(coord.body)
        }

        return result
    }
}
