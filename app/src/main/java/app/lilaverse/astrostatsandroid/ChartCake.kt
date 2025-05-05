package app.lilaverse.astrostatsandroid

import java.util.*
import swisseph.SwissEph
import swisseph.SweConst
import swisseph.SweDate

class ChartCake(
    val birthDate: Date,
    val latitude: Double,
    val longitude: Double,
    val transitDate: Date
) {
    private val swe = SwissEph()

    val natalBodies: List<Coordinate> by lazy {
        val planets = listOf(
            CelestialObject.Planet(Planet.Sun),
            CelestialObject.Planet(Planet.Moon),
            CelestialObject.Planet(Planet.Mercury),
            CelestialObject.Planet(Planet.Venus),
            CelestialObject.Planet(Planet.Mars),
            CelestialObject.Planet(Planet.Jupiter),
            CelestialObject.Planet(Planet.Saturn),
            CelestialObject.Planet(Planet.Uranus),
            CelestialObject.Planet(Planet.Neptune),
            CelestialObject.Planet(Planet.Pluto),
            CelestialObject.SouthNode,
            CelestialObject.ascendantFrom(houseCusps.getCusp(0)),
            CelestialObject.midheavenFrom(houseCusps.getCusp(9))
        )

        planets.map { Coordinate(it, birthDate) }
    }

    val houseCusps: HouseCusps by lazy {
        HouseCuspBuilder.create(latitude, longitude, birthDate)

    }
}
