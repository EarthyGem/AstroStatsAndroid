package app.lilaverse.astrostatsandroid

import swisseph.SwissEph
import java.util.Date
import app.lilaverse.astrostatsandroid.model.Chart

class ChartCake(
    val birthDate: Date,
    val latitude: Double,
    val longitude: Double,
    val transitDate: Date,
    val houseCusps: HouseCusps
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
            CelestialObject.SouthNode
        ).map { Coordinate(it, birthDate) }

        val asc = Coordinate.fromAscendant(
            cusp = houseCusps.getCusp(0),
            date = birthDate,
            geoLatitude = latitude,
            geoLongitude = longitude
        )

        val mc = Coordinate.fromMidheaven(
            cusp = houseCusps.getCusp(9),
            date = birthDate,
            geoLatitude = latitude,
            geoLongitude = longitude
        )

        planets + asc + mc
    }

    val bodies: List<Coordinate>
        get() = natalBodies

    companion object {
        fun from(chart: Chart): ChartCake {
            val birthDate = chart.date
            val lat = chart.latitude
            val lon = chart.longitude


            val houseCusps = HouseCuspBuilder.create(lat, lon, birthDate)
            return ChartCake(
                birthDate = birthDate,
                latitude = lat,
                longitude = lon,
                houseCusps = houseCusps,
                transitDate = Date()
            )
        }
    }
}
