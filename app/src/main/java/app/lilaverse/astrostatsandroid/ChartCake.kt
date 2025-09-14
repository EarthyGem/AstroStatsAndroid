package app.lilaverse.astrostatsandroid

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import swisseph.SwissEph
import java.util.Calendar
import java.util.Date
import app.lilaverse.astrostatsandroid.model.Chart
import java.util.TimeZone
@Parcelize
class ChartCake(
    val birthDate: Date,
    val latitude: Double,
    val longitude: Double,
    val transitDate: Date,
    val houseCusps: HouseCusps,
    val timezone: String = TimeZone.getDefault().id
) : Parcelable {
    @IgnoredOnParcel
    private val swe = SwissEph()

    @IgnoredOnParcel
    val natalBodies: List<Coordinate> by lazy { bodiesForDate(birthDate, houseCusps) }

    val majorDate: Date = computeMajorDate(birthDate, transitDate)
    private val majorData by lazy { bodiesAndCuspsForDate(majorDate) }
    val majorBodies: List<Coordinate> get() = majorData.second
    val majorHouseCusps: HouseCusps get() = majorData.first

    val minorDate: Date = computeMinorDate(birthDate, transitDate)
    private val minorData by lazy { bodiesAndCuspsForDate(minorDate) }
    val minorBodies: List<Coordinate> get() = minorData.second
    val minorHouseCusps: HouseCusps get() = minorData.first

    private val transitDataPair by lazy { bodiesAndCuspsForDate(transitDate) }
    val transitBodies: List<Coordinate> get() = transitDataPair.second
    val transitHouseCusps: HouseCusps get() = transitDataPair.first

    val icing: CakeIcing by lazy { CakeIcing(birthDate, majorDate, latitude, longitude, timezone) }

    val bodies: List<Coordinate>
        get() = natalBodies

    fun bodiesFor(section: SectionType): List<Coordinate> = when (section) {
        SectionType.MAJORS -> majorBodies
        SectionType.SOLAR_ARCS -> icing.allBodies
        SectionType.MINORS -> minorBodies
        SectionType.TRANSITS -> transitBodies
    }

    fun houseCuspsFor(section: SectionType): HouseCusps = when (section) {
        SectionType.MAJORS -> majorHouseCusps
        SectionType.SOLAR_ARCS -> icing.houseCusps
        SectionType.MINORS -> minorHouseCusps
        SectionType.TRANSITS -> transitHouseCusps
    }

    fun returnPlanets(): String =
        natalBodies.joinToString(separator = ", ") { it.body.keyName }

    fun formattedAllHouseActivationsBlockV2(): String =
        "No current house activations found."
    private fun bodiesForDate(date: Date, cusps: HouseCusps): List<Coordinate> {
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
            CelestialObject.SouthNode

        ).map { Coordinate(it, date, timezone) }

        val asc = Coordinate.fromAscendant(

            cusp = cusps.getCusp(0),
            date = date,
            geoLatitude = latitude,
            geoLongitude = longitude,
            timezone = timezone
        )

        val mc = Coordinate.fromMidheaven(

            cusp = cusps.getCusp(9),
            date = date,
            geoLatitude = latitude,
            geoLongitude = longitude,
            timezone = timezone
        )


        return planets + asc + mc
    }


    private fun bodiesAndCuspsForDate(date: Date): Pair<HouseCusps, List<Coordinate>> {
        val cusps = HouseCuspBuilder.create(latitude, longitude, date, timezone)
        val list = bodiesForDate(date, cusps)
        return Pair(cusps, list)
    }

    companion object {
        fun from(chart: Chart): ChartCake {
            val birthDate = chart.date
            val lat = chart.latitude
            val lon = chart.longitude
            val tz = chart.timezone

            val houseCusps = HouseCuspBuilder.create(lat, lon, birthDate, tz)
            return ChartCake(
                birthDate = birthDate,
                latitude = lat,
                longitude = lon,
                houseCusps = houseCusps,
                transitDate = Date(),
                timezone = tz
            )
        }
    }
    private fun computeMajorDate(birthDate: Date, transitDate: Date): Date {
        val diffSeconds = (transitDate.time - birthDate.time) / 1000.0
        val progressedSeconds = diffSeconds / 365.25
        return Date(birthDate.time + (progressedSeconds * 1000).toLong())
    }

    private fun computeMinorDate(birthDate: Date, transitDate: Date): Date {
        val diffMillis = transitDate.time - birthDate.time
        val minorMillis = (diffMillis * 27.3 / 365.25).toLong()
        return Date(birthDate.time + minorMillis)
    }
}

