package app.lilaverse.astrostatsandroid

class HouseCusps(private val cusps: List<Cusp>) {

    init {
        require(cusps.size == 12) { "HouseCusps must contain exactly 12 cusps." }
    }

    fun getCusp(index: Int): Cusp {
        return cusps[index % 12]
    }

    fun allCusps(): List<Cusp> {
        return cusps
    }

    fun degrees(): List<Int> {
        return cusps.map { it.degreeInSign }
    }

    fun minutes(): List<Int> {
        return cusps.map { it.minuteInSign }
    }

    fun distances(): List<Double> {
        val distances = mutableListOf<Double>()
        for (i in 0 until 12) {
            val start = cusps[i].normalizedLongitude
            val end = cusps[(i + 1) % 12].normalizedLongitude
            val dist = if (end >= start) end - start else (360 + end - start)
            distances.add(dist)
        }
        return distances
    }

    fun signNames(): List<String> {
        return cusps.map { it.sign }
    }

    override fun toString(): String {
        return cusps.joinToString(separator = "\n") { cusp ->
            "House ${cusp.index}: ${cusp.sign} ${cusp.degreeInSign}°${cusp.minuteInSign}'"
        }
    }
}
