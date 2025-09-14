package app.lilaverse.astrostatsandroid

import java.util.Date
import java.util.TimeZone

/**
 * Calculates solar arc positions based on a birth date and a progressed (major) date.
 */
class CakeIcing(
    private val birthDate: Date,
    majorDate: Date,
    latitude: Double,
    longitude: Double,
    private val timezone: String = TimeZone.getDefault().id
) {
    val houseCusps: HouseCusps = HouseCuspBuilder.create(latitude, longitude, majorDate, timezone)

    private val natalSun = Coordinate(CelestialObject.Planet(Planet.Sun), birthDate, timezone)
    val sun = Coordinate(CelestialObject.Planet(Planet.Sun), majorDate, timezone)
    private val delta: Double = ((sun.longitude - natalSun.longitude).let { if (it < 0) it + 360.0 else it })

    private fun arcFor(body: CelestialObject): Coordinate {
        val base = Coordinate(body, birthDate, timezone)
        val newLon = (base.longitude + delta) % 360.0
        return base.copy(longitude = newLon)
    }

    val moon = arcFor(CelestialObject.Planet(Planet.Moon))
    val mercury = arcFor(CelestialObject.Planet(Planet.Mercury))
    val venus = arcFor(CelestialObject.Planet(Planet.Venus))
    val mars = arcFor(CelestialObject.Planet(Planet.Mars))
    val jupiter = arcFor(CelestialObject.Planet(Planet.Jupiter))
    val saturn = arcFor(CelestialObject.Planet(Planet.Saturn))
    val uranus = arcFor(CelestialObject.Planet(Planet.Uranus))
    val neptune = arcFor(CelestialObject.Planet(Planet.Neptune))
    val pluto = arcFor(CelestialObject.Planet(Planet.Pluto))
    val southNode = arcFor(CelestialObject.SouthNode)

    val ascendant: Cusp = houseCusps.getCusp(0)
    val midheaven: Cusp = houseCusps.getCusp(9)
    val ascendantCoordinate = arcFor(CelestialObject.SpecialCusp("Ascendant", ascendant))
    val midheavenCoordinate = arcFor(CelestialObject.SpecialCusp("Midheaven", midheaven))

    val planets: List<Coordinate> = listOf(
        sun,
        moon,
        mercury,
        venus,
        mars,
        jupiter,
        saturn,
        uranus,
        neptune,
        pluto
    )

    val lunarNodes: List<Coordinate> = listOf(southNode)

    val allBodies: List<Coordinate> = planets + lunarNodes + ascendantCoordinate + midheavenCoordinate

    val majorCusps: List<Cusp> = listOf(ascendant, midheaven)

    val rickysBodies: List<Coordinate> = planets + southNode + ascendantCoordinate + midheavenCoordinate
}