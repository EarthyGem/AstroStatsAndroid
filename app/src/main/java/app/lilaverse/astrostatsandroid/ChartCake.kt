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

    // ✅ Shared house cusp property (used across the app)
    val houseCusps: HouseCusps by lazy {
        HouseCuspBuilder.create(latitude, longitude, birthDate)
    }

    // ✅ All natal planets + South Node + calculated ASC/MC using correct location
    val natalBodies: List<Coordinate> by lazy {
        val basePlanets = listOf(
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
            CelestialObject.SouthNode
        ).map { Coordinate(it, birthDate) }

        val asc = Coordinate.fromAscendant(houseCusps.getCusp(0), birthDate, latitude, longitude)
        val mc = Coordinate.fromMidheaven(houseCusps.getCusp(9), birthDate, latitude, longitude)

        basePlanets + asc + mc
    }
}
