package app.lilaverse.astrostatsandroid

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Date

class PlanetStrengthCalculatorTest {
    private val houseProvider: (Coordinate) -> Int = { coord ->
        ((coord.longitude % 360) / 30).toInt() + 1
    }

    private val luminaryChecker: (Coordinate) -> Boolean = { coord ->
        coord.body is CelestialObject.Planet &&
                (coord.body.planet == Planet.Sun || coord.body.planet == Planet.Moon)
    }

    private val houseCuspsProvider: (Double) -> HouseCusp = { lon ->
        val house = ((lon % 360) / 30).toInt() + 1
        HouseCusp(house, (house - 1) * 30.0)
    }

    private val houseCuspValues = (0 until 12).map { it * 30.0 }

    private val calculator = PlanetStrengthCalculator(
        orbDictionary,
        houseProvider,
        luminaryChecker,
        houseCuspsProvider,
        houseCuspValues
    )

    @Test
    fun parallelAspectScoresUseAssignedOrb() {
        val date = Date()
        val mars = Coordinate(
            body = CelestialObject.Planet(Planet.Mars),
            longitude = 0.0,
            declination = 10.0,
            velocity = 1.0,
            date = date
        )
        val jupiter = Coordinate(
            body = CelestialObject.Planet(Planet.Jupiter),
            longitude = 130.0,
            declination = 10.5,
            velocity = 1.0,
            date = date
        )
        val scores = calculator.allCelestialAspectScoresByAspect(listOf(mars, jupiter))
        assertEquals(1, scores.size)
        val aspect = scores.keys.first()
        assertEquals(Kind.Parallel, aspect.kind)
        val expected = (1 - 0.5) * 12.0
        assertEquals(expected, scores[aspect]!!, 1e-6)
    }

    @Test
    fun houseScoreCalculatesOffset() {
        val date = Date()
        val venus = Coordinate(
            body = CelestialObject.Planet(Planet.Venus),
            longitude = 15.0,
            declination = 0.0,
            velocity = 0.0,
            date = date
        )
        val scores = calculator.getHouseScoreForPlanetsCo(listOf(venus))
        val expected = 15.0 - 15.0 * (2.0 / 30.0)
        assertEquals(expected, scores[venus]!!, 1e-6)
    }

    @Test
    fun ascendantHasFixedHouseScore() {
        val date = Date()
        val asc = Coordinate(
            body = CelestialObject.SpecialCusp("Ascendant", Cusp(1, 0.0)),
            longitude = 0.0,
            declination = 0.0,
            velocity = 0.0,
            date = date
        )
        val scores = calculator.getHouseScoreForPlanetsCo(listOf(asc))
        assertEquals(15.0, scores[asc]!!, 1e-6)
    }
}