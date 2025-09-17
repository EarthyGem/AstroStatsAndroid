package app.lilaverse.astrostatsandroid

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

// SwissEphemerisHelper.kt
// Update to take timezone parameter
fun getPlanetPositionsFor(
    date: Date,
    timezone: String = TimeZone.getDefault().id,
    latitude: Double? = null,
    longitude: Double? = null
): List<String> {
    // Log for debugging
    val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss z")
    //Log.d("PlanetCalc", "Input date: ${formatter.format(date)}")
  //  Log.d("PlanetCalc", "Using timezone: $timezone")

    val planets = listOf(
        Planet.Sun,
        Planet.Moon,
        Planet.Mercury,
        Planet.Venus,
        Planet.Mars,
        Planet.Jupiter,
        Planet.Saturn,
        Planet.Uranus,
        Planet.Neptune,
        Planet.Pluto,
        Planet.SouthNode
    )

    val positions = planets.map { planet ->
        val body = CelestialObject.Planet(planet)
        // Pass timezone to ensure correct calculation
        val coordinate = Coordinate(body, date, timezone)
        coordinate.formatAsSignDegree()
    }.toMutableList()

    if (latitude != null && longitude != null) {
        try {
            val houseCusps = HouseCuspBuilder.create(latitude, longitude, date, timezone)
            val ascendantCusp = houseCusps.allCusps().firstOrNull { it.index == 1 }
            val midheavenCusp = houseCusps.allCusps().firstOrNull { it.index == 10 }

            ascendantCusp?.let {
                val ascendant = Coordinate.fromAscendant(it, date, latitude, longitude, timezone)
                positions.add(ascendant.formatAsSignDegree())
            }

            midheavenCusp?.let {
                val midheaven = Coordinate.fromMidheaven(it, date, latitude, longitude, timezone)
                positions.add(midheaven.formatAsSignDegree())
            }
        } catch (e: Exception) {
            Log.e("PlanetCalc", "Error calculating ascendant or midheaven: ${e.message}", e)
        }
    }

    return positions
}
