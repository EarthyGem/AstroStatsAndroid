package app.lilaverse.astrostatsandroid

import app.lilaverse.astrostatsandroid.model.Chart
import java.util.Date
import java.util.Locale
import kotlin.math.abs

/**
 * Utilities for generating synastry insights between two saved charts.
 */
object SynastryCalculator {

    private val relevantKeys = setOf(
        "Sun",
        "Moon",
        "Mercury",
        "Venus",
        "Mars",
        "Jupiter",
        "Saturn",
        "Uranus",
        "Neptune",
        "Pluto",
        "South Node ☋",
        "Ascendant",
        "Midheaven"
    )

    fun analyze(
        primary: Chart,
        primaryCake: ChartCake,
        partner: Chart,
        partnerCake: ChartCake
    ): SynastryReport {
        val aspects = computeInterAspects(
            primaryCake.natalBodies,
            partnerCake.natalBodies,
            primary.name,
            partner.name
        ).sortedByDescending { it.score }

        val topAspects = aspects.take(10)

        val primaryInPartnerHouses = computeHouseOverlay(primaryCake, partnerCake.houseCusps)
        val partnerInPrimaryHouses = computeHouseOverlay(partnerCake, primaryCake.houseCusps)

        val partnerPlacements = partnerCake
            .formattedNatalPlacements(partner.name)
            .lines()
            .map { it.trim() }
            .filter { it.isNotBlank() }

        val biwheelRows = computeBiwheel(primaryCake, partnerCake)
        val compositeSummary = computeCompositeSummary(primaryCake, partnerCake, Date())

        return SynastryReport(
            topAspects = topAspects,
            primaryInPartnerHouses = primaryInPartnerHouses,
            partnerInPrimaryHouses = partnerInPrimaryHouses,
            partnerPlacements = partnerPlacements,
            biwheelRows = biwheelRows,
            compositeSummary = compositeSummary
        )
    }

    private fun computeHouseOverlay(chartCake: ChartCake, targetCusps: HouseCusps): Map<Int, List<String>> {
        val result = mutableMapOf<Int, MutableList<String>>()
        chartCake.natalBodies
            .filter { it.body.keyName in relevantKeys }
            .forEach { coord ->
                val house = targetCusps.houseForLongitude(coord.longitude)
                result.getOrPut(house) { mutableListOf() }
                    .add(coord.body.keyName)
            }
        return result.mapValues { entry -> entry.value.sorted() }.toSortedMap()
    }

    private fun computeBiwheel(chart1: ChartCake, chart2: ChartCake): List<SynastryBiwheelRow> {
        val map1 = chart1.natalBodies.associateBy { it.body.keyName }
        val map2 = chart2.natalBodies.associateBy { it.body.keyName }

        return relevantKeys.mapNotNull { key ->
            val coord1 = map1[key] ?: return@mapNotNull null
            val coord2 = map2[key] ?: return@mapNotNull null
            SynastryBiwheelRow(
                label = displayNameFor(key),
                primaryPosition = formatCoordinate(coord1),
                partnerPosition = formatCoordinate(coord2)
            )
        }
    }

    private fun computeCompositeSummary(
        chart1: ChartCake,
        chart2: ChartCake,
        referenceDate: Date
    ): CompositeSummary {
        val bodies = buildCompositeBodies(chart1, chart2, referenceDate)
        val houseCusps = buildCompositeHouseCusps(chart1.houseCusps, chart2.houseCusps)

        val placements = bodies.map { coord ->
            val house = houseCusps.houseForLongitude(coord.longitude)
            "${displayNameFor(coord.body.keyName)} – ${formatCoordinate(coord)} (House $house)"
        }

        val topCompositeAspects = computeInternalAspects(bodies).sortedByDescending { it.score }.take(5)

        val houseEmphasis = bodies
            .groupBy { houseCusps.houseForLongitude(it.longitude) }
            .mapValues { it.value.size }
            .toSortedMap()

        return CompositeSummary(
            placements = placements,
            topAspects = topCompositeAspects,
            houseEmphasis = houseEmphasis
        )
    }

    private fun buildCompositeBodies(
        chart1: ChartCake,
        chart2: ChartCake,
        referenceDate: Date
    ): List<Coordinate> {
        val map1 = chart1.natalBodies.associateBy { it.body.keyName }
        val map2 = chart2.natalBodies.associateBy { it.body.keyName }

        return relevantKeys.mapNotNull { key ->
            val first = map1[key] ?: return@mapNotNull null
            val second = map2[key] ?: return@mapNotNull null
            val midpointLon = circularMidpoint(first.longitude, second.longitude)
            val midpointDeclination = (first.declination + second.declination) / 2.0
            val midpointVelocity = (first.velocity + second.velocity) / 2.0
            Coordinate(
                body = first.body,
                longitude = midpointLon,
                declination = midpointDeclination,
                velocity = midpointVelocity,
                date = referenceDate
            )
        }
    }

    private fun buildCompositeHouseCusps(first: HouseCusps, second: HouseCusps): HouseCusps {
        val combined = first.allCusps().zip(second.allCusps()).map { (a, b) ->
            val midpoint = circularMidpoint(a.normalizedLongitude, b.normalizedLongitude)
            Cusp(a.index, midpoint)
        }
        return HouseCusps(combined)
    }

    private fun computeInterAspects(
        firstBodies: List<Coordinate>,
        secondBodies: List<Coordinate>,
        name1: String,
        name2: String
    ): List<SynastryAspectResult> {
        val results = mutableListOf<SynastryAspectResult>()

        firstBodies
            .filter { it.body.keyName in relevantKeys }
            .forEach { body1 ->
                secondBodies
                    .filter { it.body.keyName in relevantKeys }
                    .forEach { body2 ->
                        val aspect = createAspect(body1, body2, name1, name2)
                        if (aspect != null) {
                            results.add(aspect)
                        }
                    }
            }

        return results
    }

    private fun computeInternalAspects(bodies: List<Coordinate>): List<SynastryAspectResult> {
        val results = mutableListOf<SynastryAspectResult>()
        for (i in bodies.indices) {
            val body1 = bodies[i]
            for (j in i + 1 until bodies.size) {
                val body2 = bodies[j]
                val aspect = createAspect(body1, body2, null, null)
                if (aspect != null) {
                    results.add(aspect)
                }
            }
        }
        return results
    }

    private fun createAspect(
        body1: Coordinate,
        body2: Coordinate,
        name1: String?,
        name2: String?
    ): SynastryAspectResult? {
        val angle = normalizedAngularDistance(body1.longitude, body2.longitude)
        val kind = Kind.values().firstOrNull { abs(angle - it.angle) <= it.orb } ?: return null
        val orbDelta = abs(angle - kind.angle)
        val aspect = CelestialAspect(
            kind = kind,
            body1 = body1,
            body2 = body2,
            angle = angle,
            orb = kind.orb,
            name1 = name1,
            name2 = name2
        )
        val score = (kind.orb - orbDelta).coerceAtLeast(0.0)
        return SynastryAspectResult(
            aspect = aspect,
            orbDifference = orbDelta,
            score = score
        )
    }

    private fun normalizedAngularDistance(first: Double, second: Double): Double {
        val raw = abs((first - second) % 360.0)
        return if (raw > 180.0) 360.0 - raw else raw
    }

    private fun circularMidpoint(a: Double, b: Double): Double {
        val diff = ((b - a + 540.0) % 360.0) - 180.0
        return (a + diff / 2.0 + 360.0) % 360.0
    }

    private fun displayNameFor(key: String): String = when (key) {
        "South Node ☋" -> "South Node"
        else -> key
    }

    private fun formatCoordinate(coord: Coordinate): String {
        val sign = prettySign(coord.sign)
        val deg = degToDms(coord.longitude)
        return "$deg $sign"
    }
}

/** Summary of the synastry analysis presented in the Synastry tab. */
data class SynastryReport(
    val topAspects: List<SynastryAspectResult>,
    val primaryInPartnerHouses: Map<Int, List<String>>,
    val partnerInPrimaryHouses: Map<Int, List<String>>,
    val partnerPlacements: List<String>,
    val biwheelRows: List<SynastryBiwheelRow>,
    val compositeSummary: CompositeSummary
)

/** Representation of an individual synastry aspect plus derived scores. */
data class SynastryAspectResult(
    val aspect: CelestialAspect,
    val orbDifference: Double,
    val score: Double
) {
    fun formatted(primaryName: String?, partnerName: String?): String {
        val owner1 = primaryName?.let { " ($it)" }.orEmpty()
        val owner2 = partnerName?.let { " ($it)" }.orEmpty()
        val orbText = String.format(Locale.US, "%.1f°", orbDifference)
        val scoreText = String.format(Locale.US, "%.1f", score)
        return "${aspect.body1.body.keyName}$owner1 ${aspect.kind.description} ${aspect.body2.body.keyName}$owner2 – orb $orbText (strength $scoreText)"
    }
}

/** Biwheel row showing each person's placement for a body. */
data class SynastryBiwheelRow(
    val label: String,
    val primaryPosition: String,
    val partnerPosition: String
)

/** Simple composite chart overview used in the tab. */
data class CompositeSummary(
    val placements: List<String>,
    val topAspects: List<SynastryAspectResult>,
    val houseEmphasis: Map<Int, Int>
)