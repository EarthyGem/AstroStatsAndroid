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

    fun returnPlanets(name: String): String {
        // --- Planet power via your PlanetStrengthCalculator ---
        val houseProvider: (Coordinate) -> Int = { coord ->
            // Small nudge for Ascendant like your HouseStrengthCalculator does
            val lon = if (coord.body.keyName == "Ascendant") coord.longitude + 0.5 else coord.longitude
            houseCusps.houseForLongitude(lon)
        }

        val luminaryChecker: (Coordinate) -> Boolean = { coord ->
            val k = coord.body.keyName
            k == "Sun" || k == "Moon"
        }

        val houseCuspValues: List<Double> = houseCusps.allCusps().map { it.normalizedLongitude }
        val houseCuspsProvider: (Double) -> HouseCusp = { lon ->
            val h = houseCusps.houseForLongitude(lon)
            val cuspLon = houseCusps.getCusp(h - 1).normalizedLongitude
            HouseCusp(h, cuspLon)
        }

        val pCalc = PlanetStrengthCalculator(
            orbDictionary = orbDictionary,
            houseProvider = houseProvider,
            luminaryChecker = luminaryChecker,
            houseCuspsProvider = houseCuspsProvider,
            houseCuspValues = houseCuspValues
        )

        val planetPowerMap: Map<Coordinate, Double> =
            pCalc.getTotalPowerScoresForPlanetsCo(natalBodies)

        // Convert to keys by Planet name (Sun, Moon, …), merging angles/nodes out
        val planetMetrics: Map<String, Metric> = planetPowerMap
            .filter { (coord, _) ->
                when (coord.body.keyName) {
                    "Sun","Moon","Mercury","Venus","Mars","Jupiter","Saturn","Uranus","Neptune","Pluto" -> true
                    else -> false
                }
            }
            .mapKeys { it.key.body.keyName }
            .mapValues { Metric(power = it.value, netHarmony = 0.0) } // netHarmony TBD if you separate harmony/discord later

        // --- House power via your HouseStrengthCalculator ---
        val houseStrengths: Map<Int, Double> = HouseStrengthCalculator(this, planetPowerMap)
            .calculateHouseStrengths()
        val houseMetrics: Map<Int, Metric> = houseStrengths.mapValues { Metric(power = it.value, netHarmony = 0.0) }

        // --- Sign power via your SignStrengthCalculator ---
        val signStrengths: Map<Zodiac.Sign, Double> = SignStrengthCalculator(this, planetPowerMap)
            .calculateTotalSignScores()
        val signMetrics: Map<String, Metric> = signStrengths
            .mapKeys { prettySign(it.key) }
            .mapValues { Metric(power = it.value, netHarmony = 0.0) }

        // Format like your Swift
        val planetScores = planetMetrics
            .toList()
            .sortedByDescending { it.second.power }
            .joinToString(", ") { (nameKey, m) ->
                "$nameKey: power: ${m.power.f1()}, netHarmony: ${m.netHarmony.f1()}"
            }

        val houseScores = houseMetrics
            .toList()
            .sortedByDescending { it.second.power }
            .joinToString(", ") { (houseNum, m) ->
                "House $houseNum: power: ${m.power.f1()}, netHarmony: ${m.netHarmony.f1()}"
            }
        // ---------- Net calculations (placeholders) ----------




        val signScores = signMetrics
            .toList()
            .sortedByDescending { it.second.power }
            .joinToString(", ") { (signName, m) ->
                "$signName: power: ${m.power.f1()}, netHarmony: ${m.netHarmony.f1()}"
            }

        val placements = formattedNatalPlacements(name)
        val age = ageString(birthDate, Date())

        return buildString {
            append("age: $age\n")
            append("planet scores: $planetScores\n")
            append("house scores: $houseScores\n")
            append("sign scores: $signScores\n")
            append(placements.prependIndent(""))
        }
    }


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
    // ---------- 1) Swift-parity: Natal placements ----------
    fun formattedNatalPlacements(name: String): String {
        fun lineFor(body: CelestialObject, label: String): String {
            val c = Coordinate(body, birthDate, timezone)
            val sign = prettySign(c.sign)
            val pos  = degToDms(c.longitude)
            val houseNum = houseCusps.houseForLongitude(c.longitude)
            return "$name's $label $pos $sign in the House $houseNum house"
        }

        val sunLine   = lineFor(CelestialObject.Planet(Planet.Sun),      "Sun")
        val moonLine  = lineFor(CelestialObject.Planet(Planet.Moon),     "Moon")
        val mercLine  = lineFor(CelestialObject.Planet(Planet.Mercury),  "Mercury")
        val venusLine = lineFor(CelestialObject.Planet(Planet.Venus),    "Venus")
        val marsLine  = lineFor(CelestialObject.Planet(Planet.Mars),     "Mars")
        val jupLine   = lineFor(CelestialObject.Planet(Planet.Jupiter),  "Jupiter")
        val satLine   = lineFor(CelestialObject.Planet(Planet.Saturn),   "Saturn")
        val uraLine   = lineFor(CelestialObject.Planet(Planet.Uranus),   "Uranus")
        val nepLine   = lineFor(CelestialObject.Planet(Planet.Neptune),  "Neptune")
        val pluLine   = lineFor(CelestialObject.Planet(Planet.Pluto),    "Pluto")

        // Angles (natal)
        val asc = natalBodies.first { it.body.keyName == "Ascendant" }
        val mc  = natalBodies.first { it.body.keyName == "Midheaven" }
        val ascStr = "Ascendant ${degToDms(asc.longitude)} ${prettySign(asc.sign)}"
        val mcStr  = "Midheaven ${degToDms(mc.longitude)} ${prettySign(mc.sign)}"

        // Nodes (natal) – you only include SouthNode in natalBodies, so build NorthNode explicitly

        val sn = natalBodies.firstOrNull { it.body.keyName == "South Node" }
            ?: Coordinate(CelestialObject.SouthNode, birthDate, timezone)


        val snLine = "South Node ${degToDms(sn.longitude)} ${prettySign(sn.sign)} in the House ${houseCusps.houseForLongitude(sn.longitude)} house"

        return """
        Natal Placements:
        - $sunLine
        - $moonLine
        - $mercLine
        - $venusLine
        - $marsLine
        - $jupLine
        - $satLine
        - $uraLine
        - $nepLine
        - $pluLine
        - $ascStr
        - $mcStr
        - $snLine
    """.trimIndent()
    }

    /** Determine the Moon's zodiac sign for a given date. */
    fun moonSignForDate(date: Date): Zodiac.Sign {
        val moonCoordinate = Coordinate(CelestialObject.Planet(Planet.Moon), date, timezone)
        return moonCoordinate.sign
    }

    private fun determineZodiacSign(longitude: Double): Zodiac.Sign {
        return Zodiac.from(longitude)
    }

    fun createAspect(a: Double, b: Double, orb: Double): Aspect? {
        return Aspect.detect(a, b, orb)
    }

    fun filterConjunction(aspect: Aspect?): Aspect? =
        if (aspect is Aspect.Conjunction) aspect else null

    /** Retrieve a body by name from a list of coordinates. */
    fun bodyByName(name: String, list: List<Coordinate>): Coordinate? =
        list.firstOrNull { it.body.keyName == name }

    /** Convert an integer to its ordinal string (1 -> 1st). */
    fun ordinal(n: Int): String {
        if (n % 100 in 11..13) return "${n}th"
        return when (n % 10) {
            1 -> "${n}st"
            2 -> "${n}nd"
            3 -> "${n}rd"
            else -> "${n}th"
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

