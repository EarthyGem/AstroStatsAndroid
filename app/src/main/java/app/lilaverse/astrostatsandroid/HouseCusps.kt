package app.lilaverse.astrostatsandroid
import kotlinx.parcelize.Parcelize
import android.os.Parcelable


@Parcelize
data class HouseCusps(val cusps: List<Cusp>) : Parcelable {


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

    /**
     * Given a planet's longitude, returns the house it falls into.
     * This compares the planet's position with the house cusp boundaries.
     */
    fun houseForLongitude(lon: Double): Int {
        val normalizedLon = (lon + 360) % 360
        val cuspsSorted = cusps.sortedBy { it.index }

        for (i in 0 until 12) {
            val start = cuspsSorted[i].normalizedLongitude
            val end = cuspsSorted[(i + 1) % 12].normalizedLongitude

            val inRange = if (start < end) {
                normalizedLon >= start && normalizedLon < end
            } else {
                // wrap around 360° (e.g., House 12 ends at 360 and House 1 starts at 0)
                normalizedLon >= start || normalizedLon < end
            }

            if (inRange) {
                return cuspsSorted[i].index
            }
        }

        // Fallback (shouldn't happen)
        return 12
    }
    fun getInterceptedSigns(): List<Zodiac.Sign> {
        val intercepted = mutableListOf<Zodiac.Sign>()

        for (i in 0 until 12) {
            val start = allCusps()[i].normalizedLongitude
            val end = allCusps()[(i + 1) % 12].normalizedLongitude

            var current = start
            while (true) {
                current = (current + 30) % 360
                if ((start < end && current >= end) || (start > end && (current >= end && current < start))) {
                    break
                }

                val sign = Zodiac.from(current)
                if (sign != Zodiac.from(start) && sign != Zodiac.from(end)) {
                    if (!intercepted.contains(sign)) intercepted.add(sign)
                }
            }
        }

        return intercepted.distinct()
    }

    override fun toString(): String {
        return cusps.joinToString(separator = "\n") { cusp ->
            "House ${cusp.index}: ${cusp.sign} ${cusp.degreeInSign}°${cusp.minuteInSign}'"
        }
    }
}
