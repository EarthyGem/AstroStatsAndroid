package app.lilaverse.astrostatsandroid

import java.util.*
import swisseph.SwissEph
import swisseph.SweConst
import swisseph.SweDate

/**
 * Represents a celestial coordinate (planet or cusp) with position and motion data.
 */
data class Coordinate(
    val body: CelestialObject,
    val longitude: Double,
    val declination: Double,
    val velocity: Double,
    val date: Date
) {
    constructor(body: CelestialObject, date: Date) : this(
        body = body,
        longitude = calculateLongitude(body, date),
        declination = calculateDeclination(body, date),
        velocity = calculateVelocity(body, date),
        date = date
    )

    constructor(cusp: Cusp, date: Date) : this(
        body = CelestialObject.Cusp(cusp),
        longitude = cusp.longitude,
        declination = 0.0,
        velocity = 0.0,
        date = date
    )

    /** Zodiac sign name this coordinate is in */
    val signName: String
        get() = Zodiac.signForDegree(longitude)

    /** Glyph of the zodiac sign this coordinate is in */
    val signGlyph: String
        get() = Zodiac.glyphForDegree(longitude)

    /** Degree within the zodiac sign (0.00–29.99) */
    val degreeInSign: Double
        get() = (longitude % 30 + 30) % 30

    /** Formatted degree string like "18.54" */
    val formattedDegreeInSign: String
        get() = String.format("%.2f", degreeInSign)

    /** Returns a string like: "Mars: 18.54° Leo ♌" */
    fun formatAsSignDegree(): String {
        return "${body.keyName}: $formattedDegreeInSign° $signName $signGlyph"
    }

    companion object {
        private val swe = SwissEph()

        private fun calculateJulianDay(date: Date): Double {
            val calendar = Calendar.getInstance()
            calendar.time = date

            val timeZoneOffsetInMillis = calendar.timeZone.getOffset(date.time)
            val offsetHours = timeZoneOffsetInMillis / 3600000.0

            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH) + 1
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val hour = calendar.get(Calendar.HOUR_OF_DAY) + calendar.get(Calendar.MINUTE) / 60.0

            val utcHour = hour - offsetHours

            return SweDate.getJulDay(year, month, day, utcHour, SweDate.SE_GREG_CAL)
        }


        private fun sweIdForBody(body: CelestialObject): Int? {
            return when (body) {
                is CelestialObject.Planet -> when (body.planet) {
                    Planet.Sun -> SweConst.SE_SUN
                    Planet.Moon -> SweConst.SE_MOON
                    Planet.Mercury -> SweConst.SE_MERCURY
                    Planet.Venus -> SweConst.SE_VENUS
                    Planet.Mars -> SweConst.SE_MARS
                    Planet.Jupiter -> SweConst.SE_JUPITER
                    Planet.Saturn -> SweConst.SE_SATURN
                    Planet.Uranus -> SweConst.SE_URANUS
                    Planet.Neptune -> SweConst.SE_NEPTUNE
                    Planet.Pluto -> SweConst.SE_PLUTO
                    Planet.NorthNode -> SweConst.SE_TRUE_NODE
                    Planet.SouthNode -> SweConst.SE_MEAN_NODE
                }
                else -> null
            }
        }

        private fun sweCalc(body: CelestialObject, date: Date): DoubleArray {
            val planetId = sweIdForBody(body)
            val result = DoubleArray(6)
            val errorBuffer = StringBuffer()

            if (planetId != null) {
                val jd = calculateJulianDay(date)
                val flags = SweConst.SEFLG_SWIEPH or SweConst.SEFLG_SPEED
                swe.swe_calc(jd, planetId, flags, result, errorBuffer)
            }

            return result
        }

        private fun calculateLongitude(body: CelestialObject, date: Date): Double {
            return sweCalc(body, date)[0] // longitude
        }

        private fun calculateDeclination(body: CelestialObject, date: Date): Double {
            return sweCalc(body, date)[1] // declination
        }

        private fun calculateVelocity(body: CelestialObject, date: Date): Double {
            return sweCalc(body, date)[3] // speed in longitude
        }
    }
}
