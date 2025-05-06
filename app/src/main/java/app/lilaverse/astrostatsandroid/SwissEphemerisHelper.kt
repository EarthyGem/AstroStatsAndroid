package app.lilaverse.astrostatsandroid

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

// SwissEphemerisHelper.kt
fun getPlanetPositionsFor(date: Date): List<String> {
    // Create a UTC calendar
    val utcCal = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
    utcCal.time = date

    // Log for debugging
    val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss z")
    Log.d("PlanetCalc", "Input date: ${formatter.format(date)}")
    formatter.timeZone = TimeZone.getTimeZone("UTC")
    Log.d("PlanetCalc", "UTC date: ${formatter.format(utcCal.time)}")

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
        val coordinate = Coordinate(body, utcCal.time) // Use UTC time for coordinate calculation
        coordinate.formatAsSignDegree()
    }
}