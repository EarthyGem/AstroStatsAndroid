package app.lilaverse.astrostatsandroid

import androidx.compose.ui.graphics.Color
import com.google.android.gms.maps.model.LatLng
import java.util.Date
import kotlin.math.abs
import kotlin.math.atan
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.tan

object AstrocartographyCalculator {

    fun calculatePlanetaryLines(chartCake: ChartCake): List<AstroLine> {
        val birthDate = chartCake.birthDate
        val planets = chartCake.natalBodies.filter { coordinate ->
            when (val body = coordinate.body) {
                is CelestialObject.Planet -> when (body.planet) {
                    Planet.Sun,
                    Planet.Moon,
                    Planet.Mercury,
                    Planet.Venus,
                    Planet.Mars,
                    Planet.Jupiter,
                    Planet.Saturn,
                    Planet.Uranus,
                    Planet.Neptune,
                    Planet.Pluto -> true

                    else -> false
                }

                CelestialObject.SouthNode -> true
                else -> false
            }
        }

        val lines = mutableListOf<AstroLine>()

        planets.forEach { planet ->
            val glyph = glyphForObject(planet.body)
            val baseLabel = "$glyph ${planet.body.keyName}"
            val color = colorForPlanet(planet.body)

            val ascendantLine = calculateAscendantLine(planet, birthDate)
            if (ascendantLine.isNotEmpty()) {
                lines += AstroLine(
                    planet = planet.body,
                    lineType = LineType.ASCENDANT,
                    coordinates = ascendantLine,
                    color = color.copy(alpha = 0.95f),
                    label = "$baseLabel AC"
                )
            }

            val descendantLine = calculateDescendantLine(planet, birthDate)
            if (descendantLine.isNotEmpty()) {
                lines += AstroLine(
                    planet = planet.body,
                    lineType = LineType.DESCENDANT,
                    coordinates = descendantLine,
                    color = color.copy(alpha = 0.75f),
                    label = "$baseLabel DC"
                )
            }

            val midheavenLine = calculateMidheavenLine(planet, birthDate)
            if (midheavenLine.isNotEmpty()) {
                lines += AstroLine(
                    planet = planet.body,
                    lineType = LineType.MIDHEAVEN,
                    coordinates = midheavenLine,
                    color = color,
                    label = "$baseLabel MC"
                )
            }

            val icLine = calculateICLine(planet, birthDate)
            if (icLine.isNotEmpty()) {
                lines += AstroLine(
                    planet = planet.body,
                    lineType = LineType.IMUM_COELI,
                    coordinates = icLine,
                    color = color.copy(alpha = 0.75f),
                    label = "$baseLabel IC"
                )
            }
        }

        return lines
    }

    private fun calculateAscendantLine(planet: Coordinate, date: Date): List<LatLng> {
        val result = mutableListOf<LatLng>()
        val declinationRad = planet.declination.toRadians()
        val gmst = calculateGreenwichMeanSiderealTime(date)
        val planetRA = calculateRightAscension(planet.longitude, planet.declination)

        if (abs(planet.declination) >= 89.0) return emptyList()

        for (longitude in -179..179) {
            val lst = normalizeLongitude(gmst + longitude)
            val hourAngle = normalizeLongitude(lst - planetRA)
            val hourAngleRad = hourAngle.toRadians()

            val cosDec = cos(declinationRad)
            val sinDec = sin(declinationRad)
            val cosHA = cos(hourAngleRad)

            if (abs(sinDec) > 0.001) {
                val tanLat = -(cosDec * cosHA) / sinDec
                if (abs(tanLat) < 100.0) {
                    val latitude = atan(tanLat).toDegrees()
                    if (abs(latitude) <= 85.0) {
                        result += LatLng(latitude, longitude.toDouble())
                    }
                }
            }
        }

        return breakIntoSegments(result)
    }

    private fun calculateDescendantLine(planet: Coordinate, date: Date): List<LatLng> {
        val result = mutableListOf<LatLng>()
        val declinationRad = planet.declination.toRadians()
        val gmst = calculateGreenwichMeanSiderealTime(date)
        val planetRA = calculateRightAscension(planet.longitude, planet.declination)

        if (abs(planet.declination) >= 89.0) return emptyList()

        for (longitude in -179..179) {
            val lst = normalizeLongitude(gmst + longitude)
            val hourAngle = normalizeLongitude(lst - planetRA)
            val adjusted = normalizeLongitude(hourAngle + 180.0).toRadians()

            val cosDec = cos(declinationRad)
            val sinDec = sin(declinationRad)
            val cosHA = cos(adjusted)

            if (abs(sinDec) > 0.001) {
                val tanLat = -(cosDec * cosHA) / sinDec
                if (abs(tanLat) < 100.0) {
                    val latitude = atan(tanLat).toDegrees()
                    if (abs(latitude) <= 85.0) {
                        result += LatLng(latitude, longitude.toDouble())
                    }
                }
            }
        }

        return breakIntoSegments(result)
    }

    private fun calculateMidheavenLine(planet: Coordinate, date: Date): List<LatLng> {
        val planetRA = calculateRightAscension(planet.longitude, planet.declination)
        val gmst = calculateGreenwichMeanSiderealTime(date)
        val mcLongitude = normalizeLongitude(planetRA - gmst)

        val coords = mutableListOf<LatLng>()
        for (latitude in -85..85 step 2) {
            coords += LatLng(latitude.toDouble(), mcLongitude)
        }
        return coords
    }

    private fun calculateICLine(planet: Coordinate, date: Date): List<LatLng> {
        val planetRA = calculateRightAscension(planet.longitude, planet.declination)
        val gmst = calculateGreenwichMeanSiderealTime(date)
        val icLongitude = normalizeLongitude(planetRA - gmst + 180.0)

        val coords = mutableListOf<LatLng>()
        for (latitude in -85..85 step 2) {
            coords += LatLng(latitude.toDouble(), icLongitude)
        }
        return coords
    }

    private fun breakIntoSegments(points: List<LatLng>): List<LatLng> {
        if (points.size < 2) return points

        val filtered = mutableListOf<LatLng>()
        var previous: LatLng? = null
        points.sortedBy { it.longitude }.forEach { point ->
            val prev = previous
            if (prev != null) {
                val latDiff = abs(point.latitude - prev.latitude)
                val lonDiff = abs(point.longitude - prev.longitude)
                val isJump = latDiff > 30.0 || lonDiff > 10.0
                val looksWrapped = latDiff > 20.0 && lonDiff < 5.0

                if (isJump || looksWrapped) {
                    previous = null
                    return@forEach
                }
            }

            filtered += point
            previous = point
        }
        return filtered
    }

    private fun calculateRightAscension(longitude: Double, latitude: Double): Double {
        val epsilon = 23.4397
        val lambda = longitude.toRadians()
        val beta = latitude.toRadians()
        val eps = epsilon.toRadians()

        val ra = atan2(
            sin(lambda) * cos(eps) - tan(beta) * sin(eps),
            cos(lambda)
        ).toDegrees()

        return normalizeLongitude(ra)
    }

    private fun calculateGreenwichMeanSiderealTime(date: Date): Double {
        val jd = date.julianDate()
        val t = (jd - 2451545.0) / 36525.0
        var gmst = 280.46061837 + 360.98564736629 * (jd - 2451545.0) + 0.000387933 * t * t - t * t * t / 38710000.0
        return normalizeLongitude(gmst)
    }

    private fun Date.julianDate(): Double {
        return Coordinate.calculateJulianDay(this)
    }

    private fun normalizeLongitude(value: Double): Double {
        var result = value % 360.0
        if (result < -180.0) result += 360.0
        if (result > 180.0) result -= 360.0
        return result
    }

    private fun Double.toRadians(): Double = Math.toRadians(this)
    private fun Double.toDegrees(): Double = Math.toDegrees(this)

    private fun glyphForObject(body: CelestialObject): String {
        return when (body) {
            is CelestialObject.Planet -> body.planet.symbol
            CelestialObject.SouthNode -> "☋"
            is CelestialObject.SpecialCusp -> body.keyName
            else -> body.keyName
        }
    }

    private fun colorForPlanet(body: CelestialObject): Color {
        return when (body) {
            is CelestialObject.Planet -> when (body.planet) {
                Planet.Sun -> Color(0xFFFFB74D)
                Planet.Moon -> Color(0xFF66BB6A)
                Planet.Mercury -> Color(0xFF9575CD)
                Planet.Venus -> Color(0xFFFFF176)
                Planet.Mars -> Color(0xFFE57373)
                Planet.Jupiter -> Color(0xFFBA68C8)
                Planet.Saturn -> Color(0xFF64B5F6)
                Planet.Uranus -> Color(0xFF90A4AE)
                Planet.Neptune -> Color(0xFF4DB6AC)
                Planet.Pluto -> Color(0xFF757575)
                Planet.SouthNode -> Color(0xFFBDBDBD)
            }

            CelestialObject.SouthNode -> Color(0xFFBDBDBD)
            else -> Color(0xFF90CAF9)
        }
    }
}