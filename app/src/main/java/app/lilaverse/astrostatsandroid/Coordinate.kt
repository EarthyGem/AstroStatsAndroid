package app.lilaverse.astrostatsandroid

import swisseph.*
import java.util.*
import android.util.Log
import kotlin.math.sin
import kotlin.math.asin
import kotlin.math.cos

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
    // Update the constructor to take timezone
    constructor(body: CelestialObject, date: Date, timezone: String = TimeZone.getDefault().id) : this(
        body = body,
        longitude = calculateCoordinateData(body, date, timezone).first,
        declination = calculateCoordinateData(body, date, timezone).second,
        velocity = calculateCoordinateData(body, date, timezone).third,
        date = date
    )

    constructor(cusp: Cusp, date: Date, timezone: String = TimeZone.getDefault().id) : this(
        body = CelestialObject.Cusp(cusp),
        longitude = cusp.longitude,
        declination = 0.0,
        velocity = 0.0,
        date = date
    )

    val sign: Zodiac.Sign get() = Zodiac.from(longitude)
    val signName: String get() = sign.toString()
    val signGlyph: String get() = sign.glyph
    val degreeInSign: Double get() = (longitude % 30 + 30) % 30
    val formattedDegreeInSign: String get() = String.format("%.2f", degreeInSign)

    fun formatAsSignDegree(): String {
        return "${body.keyName}: $formattedDegreeInSign° $signName $signGlyph"
    }

    companion object {
        private val swe = SwissEph()

        /**
         * Calculate Julian Day from a Date object
         */

        // Modify calculateJulianDay to use the provided timezone
        fun calculateJulianDay(date: Date, timezone: String = TimeZone.getDefault().id): Double {
            val calendar = Calendar.getInstance(TimeZone.getTimeZone(timezone))
            calendar.time = date
            val timeZoneOffset = calendar.timeZone.getOffset(date.time)
            val offsetHours = timeZoneOffset / 3600000.0

            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH) + 1
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val hour = calendar.get(Calendar.HOUR_OF_DAY) + calendar.get(Calendar.MINUTE) / 60.0
            val utcHour = hour - offsetHours

            return SweDate.getJulDay(year, month, day, utcHour, SweDate.SE_GREG_CAL)
        }

        /**
         * Get Swiss Ephemeris ID for a celestial body
         */
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
                    Planet.SouthNode -> SweConst.SE_MEAN_NODE
                }
                CelestialObject.SouthNode -> SweConst.SE_MEAN_NODE
                else -> null
            }
        }

        /**
         * Calculate coordinate data (longitude, declination, velocity) for a celestial body
         */
        private fun calculateCoordinateData(body: CelestialObject, date: Date, timezone: String): Triple<Double, Double, Double> {
            val jd = calculateJulianDay(date, timezone)
            val id = sweIdForBody(body) ?: return Triple(0.0, 0.0, 0.0)
            val errorBuffer = StringBuffer()

            // Calculate with timezone-aware Julian day
            val resultEcl = DoubleArray(6)
            swe.swe_calc(jd, id, SweConst.SEFLG_SWIEPH or SweConst.SEFLG_SPEED, resultEcl, errorBuffer)
            val rawLon = resultEcl[0]
            val velocity = resultEcl[3]
            val longitude = if (body == CelestialObject.SouthNode) (rawLon + 180.0) % 360 else rawLon

            val resultEq = DoubleArray(6)
            swe.swe_calc(jd, id, SweConst.SEFLG_SWIEPH or SweConst.SEFLG_SPEED or SweConst.SEFLG_EQUATORIAL, resultEq, errorBuffer)
            val declination = resultEq[1]

            return Triple(longitude, declination, velocity)
        }

        /**
         * Create a Coordinate from a cusp
         */
        fun fromCusp(cusp: Cusp, date: Date): Coordinate {
            return Coordinate(cusp, date)
        }

        /**
         * Get the obliquity of the ecliptic for a given Julian Day
         */
        fun getObliquity(jd: Double): Double {
            val xx = DoubleArray(6)
            swe.swe_calc(jd, SweConst.SE_ECL_NUT, 0, xx, null)
            return xx[0] // This is the obliquity in degrees
        }

        /**
         * Calculate declination for a point on the ecliptic
         * Formula: sin(δ) = sin(ε) × sin(λ)
         * Where:
         * - δ is declination
         * - ε is obliquity of the ecliptic
         * - λ is celestial longitude
         */
        fun calculatePointDeclination(longitude: Double, obliquity: Double, latitude: Double = 0.0): Double {
            // Convert to radians
            val longitudeRad = Math.toRadians(longitude)
            val obliquityRad = Math.toRadians(obliquity)

            // For ecliptical coordinates with latitude = 0 (like house cusps):
            // sin(declination) = sin(obliquity) * sin(longitude)
            val sinDec = sin(obliquityRad) * sin(longitudeRad)

            // Convert back to degrees
            return Math.toDegrees(asin(sinDec))
        }

        /**
         * Create a Coordinate for the Ascendant with proper declination calculation
         */
        fun fromAscendant(cusp: Cusp, date: Date, geoLatitude: Double, geoLongitude: Double, timezone: String = TimeZone.getDefault().id): Coordinate {
            require(cusp.index == 1)
            val jd = calculateJulianDay(date, timezone)
            val ascmc = DoubleArray(10)
            val cusps = DoubleArray(13)
            swe.swe_houses(jd, SweConst.SEFLG_SWIEPH, geoLatitude, geoLongitude, SweConst.SE_HSYS_PLACIDUS, cusps, ascmc)
            val ascLon = ascmc[0]

            // Calculate AC declination properly
            val obliquity = getObliquity(jd)
            val ascDec = calculatePointDeclination(ascLon, obliquity)

            Log.d("DeclinationCalc", "AC: Lon=${ascLon}°, Calculated Dec=${ascDec}°, JD=$jd, Obliq=$obliquity°")

            return Coordinate(
                body = CelestialObject.SpecialCusp("Ascendant", cusp),
                longitude = ascLon,
                declination = ascDec,
                velocity = 0.0, // Ascendant doesn't have velocity in the same sense as planets
                date = date
            )
        }

        /**
         * Create a Coordinate for the Midheaven with proper declination calculation
         */
        fun fromMidheaven(cusp: Cusp, date: Date, geoLatitude: Double, geoLongitude: Double, timezone: String = TimeZone.getDefault().id): Coordinate {
            require(cusp.index == 10)
            val jd = calculateJulianDay(date, timezone)
            val ascmc = DoubleArray(10)
            val cusps = DoubleArray(13)
            swe.swe_houses(jd, SweConst.SEFLG_SWIEPH, geoLatitude, geoLongitude, SweConst.SE_HSYS_PLACIDUS, cusps, ascmc)
            val mcLon = ascmc[1]

            // Calculate MC declination properly
            val obliquity = getObliquity(jd)
            val mcDec = calculatePointDeclination(mcLon, obliquity)

            Log.d("DeclinationCalc", "MC: Lon=${mcLon}°, Calculated Dec=${mcDec}°, JD=$jd, Obliq=$obliquity°")

            return Coordinate(
                body = CelestialObject.SpecialCusp("Midheaven", cusp),
                longitude = mcLon,
                declination = mcDec,
                velocity = 0.0, // MC doesn't have velocity in the same sense as planets
                date = date
            )
        }

        /**
         * Calculate declination for a full range of longitudes (for testing)
         */
        fun testDeclinationCalculation(obliquity: Double = 23.44): Map<Int, Double> {
            val results = mutableMapOf<Int, Double>()
            for (lon in 0..359) {
                val dec = calculatePointDeclination(lon.toDouble(), obliquity)
                results[lon] = dec
            }
            return results
        }

        /**
         * Log declination values for all zodiac signs (for verification)
         */
        fun logZodiacDeclinations(obliquity: Double = 23.44) {
            val signs = listOf("Aries", "Taurus", "Gemini", "Cancer", "Leo", "Virgo",
                "Libra", "Scorpio", "Sagittarius", "Capricorn", "Aquarius", "Pisces")

            Log.d("ZodiacDeclination", "Declination values at start of each sign (obliquity = $obliquity°):")

            for (i in 0..11) {
                val longitude = i * 30.0
                val declination = calculatePointDeclination(longitude, obliquity)
                Log.d("ZodiacDeclination", "${signs[i]} (${longitude}°): ${String.format("%.2f", declination)}°")
            }
        }
    }
}