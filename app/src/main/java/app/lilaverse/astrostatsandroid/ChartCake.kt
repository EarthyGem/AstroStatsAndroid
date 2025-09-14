package app.lilaverse.astrostatsandroid

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import swisseph.SwissEph
import java.util.Calendar
import java.util.Date
import app.lilaverse.astrostatsandroid.model.Chart
import java.util.TimeZone
import java.time.Instant
import java.time.ZoneId
import java.time.LocalDate
import java.time.temporal.ChronoUnit
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
            geoLongitude = longitude,
            timezone = timezone
        )

        val mc = Coordinate.fromMidheaven(
            cusp = houseCusps.getCusp(9),
            date = birthDate,
            geoLatitude = latitude,
            geoLongitude = longitude,
            timezone = timezone
        )

        planets + asc + mc
    }
    @IgnoredOnParcel
    val bodies: List<Coordinate>
        get() = natalBodies
    fun returnPlanets(): String {
        val age = calculateAgeString(birthDate, Date())

        val totalPowerScores = PlanetStrengthCalculator(
            orbDictionary,
            houseProvider = { houseCusps.houseForLongitude(it.longitude) },
            luminaryChecker = { it.body.keyName in listOf("Sun", "Moon", "Mercury") },
            houseCuspsProvider = { lon ->
                val houseNum = houseCusps.houseForLongitude(lon)
                val cuspLon = houseCusps.getCusp(houseNum - 1).longitude
                HouseCusp(houseNum, cuspLon)
            },
            houseCuspValues = (0 until 12).map { houseCusps.getCusp(it).longitude }
        ).getTotalPowerScoresForPlanetsCo(natalBodies)

        val planetScores = totalPowerScores.entries
            .sortedByDescending { it.value }
            .joinToString(", ") {
                "${it.key.body.keyName}: power: ${String.format("%.1f", it.value)}"
            }

        val signScores = SignStrengthCalculator(this, totalPowerScores)
            .calculateTotalSignScores()
            .entries
            .sortedByDescending { it.value }
            .joinToString(", ") {
                "${it.key.name}: power: ${String.format("%.1f", it.value)}"
            }

        val houseScores = HouseStrengthCalculator(this, totalPowerScores)
            .calculateHouseStrengths()
            .entries
            .sortedByDescending { it.value }
            .joinToString(", ") {
                "${ordinal(it.key)} House: power: ${String.format("%.1f", it.value)}"
            }

        val houseRulerships = (1..12).associate { houseNumber ->
            "${ordinal(houseNumber)} House" to rulingBodies(houseNumber)
                .joinToString(", ") { it.keyName }
        }
        val houseRulershipsText = houseRulerships.entries.joinToString("\n") {
            "• ${it.key}: ${it.value}"
        }

        val planetInfo = """
            natal placements: ${formatNatalPlacements()}

            planet scores (sorted by power): $planetScores
            house scores (sorted by power): $houseScores
            sign scores (sorted by power): $signScores

            HOUSE RULERSHIPS:
            $houseRulershipsText

            This person is $age. Please make recommendations age appropriate.
        """.trimIndent()

        return planetInfo
    }
    // Date → "X years and Y days"
    private fun calculateAgeString(from: Date, to: Date): String {
        val birth = from.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        val selected = to.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        val years = ChronoUnit.YEARS.between(birth, selected).toInt()
        val lastBirthday = birth.plusYears(years.toLong())
        val days = ChronoUnit.DAYS.between(lastBirthday, selected).toInt()
        return "$years years and $days days"
    }

    // “Planet in Sign in the Nth house” line for each natal body
    private fun formatNatalPlacements(): String =
        natalBodies.joinToString("\n") { coord ->
            val planetName = coord.body.keyName
            val signName = coord.sign.name
            val houseName = ordinal(houseCusps.houseForLongitude(coord.longitude))
            "$planetName in $signName in the $houseName house."
        }

    // House rulers pulled from chart’s bodies (sorted by longitude)
    private fun rulingBodies(house: Int): List<Coordinate> {
        val rulingPlanets = rulingPlanetsForHouse(house)
        val matches = natalBodies.filter { coord ->
            val planet = (coord.body as? CelestialObject.Planet)?.planet
            planet != null && planet in rulingPlanets
        }
        return matches.sortedBy { it.longitude }
    }

    // Helper to map house → ruling planets
    private fun rulingPlanetsForHouse(house: Int): List<Planet> {
        return when (houseCusps.getCusp(house - 1).sign.lowercase()) {
            "aries"       -> listOf(Planet.Mars)
            "taurus"      -> listOf(Planet.Venus)
            "gemini"      -> listOf(Planet.Mercury)
            "cancer"      -> listOf(Planet.Moon)
            "leo"         -> listOf(Planet.Sun)
            "virgo"       -> listOf(Planet.Mercury)
            "libra"       -> listOf(Planet.Venus)
            "scorpio"     -> listOf(Planet.Mars, Planet.Pluto)
            "sagittarius" -> listOf(Planet.Jupiter)
            "capricorn"   -> listOf(Planet.Saturn)
            "aquarius"    -> listOf(Planet.Uranus, Planet.Saturn)
            "pisces"      -> listOf(Planet.Neptune, Planet.Jupiter)
            else          -> emptyList()
        }
    }

    fun formattedAllHouseActivationsBlockV2(): String =
        "No current house activations found."

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
