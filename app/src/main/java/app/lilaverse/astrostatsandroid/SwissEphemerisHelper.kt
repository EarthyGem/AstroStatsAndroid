package app.lilaverse.astrostatsandroid

import java.util.*

fun getPlanetPositionsFor(date: Date): List<String> {
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
        val coordinate = Coordinate(body, date)
        coordinate.formatAsSignDegree()
    }
}
