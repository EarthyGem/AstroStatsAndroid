package app.lilaverse.astrostatsandroid

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Date

class CoordinateTest {
    @Test
    fun constructorProducesExpectedValues() {
        val date = Date(0)
        val body = CelestialObject.Planet(Planet.Sun)
        val timezone = "UTC"

        val coordinate = Coordinate(body, date, timezone)

        val companionClass = Class.forName("app.lilaverse.astrostatsandroid.Coordinate\$Companion")
        val method = companionClass.getDeclaredMethod(
            "calculateCoordinateData",
            CelestialObject::class.java,
            Date::class.java,
            String::class.java
        )
        method.isAccessible = true
        val companionInstance = companionClass.getDeclaredField("INSTANCE").get(null)
        @Suppress("UNCHECKED_CAST")
        val expected = method.invoke(companionInstance, body, date, timezone) as Triple<Double, Double, Double>

        assertEquals(expected.first, coordinate.longitude, 0.0000001)
        assertEquals(expected.second, coordinate.declination, 0.0000001)
        assertEquals(expected.third, coordinate.velocity, 0.0000001)
    }
}