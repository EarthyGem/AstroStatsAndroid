package app.lilaverse.astrostatsandroid

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

// SwissEphemerisHelper.kt
// Update to take timezone parameter
fun getPlanetPositionsFor(date: Date, timezone: String = TimeZone.getDefault().id): List<String> {
    // Log for debugging
    val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss z")
    Log.d("PlanetCalc", "Input date: ${formatter.format(date)}")
    Log.d("PlanetCalc", "Using timezone: $timezone")

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

    return planets.map { planet ->
        val body = CelestialObject.Planet(planet)
        // Pass timezone to ensure correct calculation
        val coordinate = Coordinate(body, date, timezone)
        coordinate.formatAsSignDegree()
    }
}
