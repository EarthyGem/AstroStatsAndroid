package app.lilaverse.astrostatsandroid

import java.util.*
import kotlin.math.abs

data class CelestialAspect(
    val id: UUID = UUID.randomUUID(),
    val kind: Kind,
    val body1: Coordinate,
    val body2: Coordinate,
    val angle: Double,
    val orb: Double,
    val startDate: Date? = null,
    val endDate: Date? = null,
    val name1: String? = null,
    val name2: String? = null,
    val type: TypeAspect = TypeAspect.Applying
) {
    enum class TypeAspect {
        Applying,
        Separating
    }

    val orbDelta: Double
        get() = when (kind) {
            Kind.Parallel -> abs(abs(body1.declination) - abs(body2.declination))
            else -> abs(angle - kind.angle.toDouble())
        }

    val typeDescription: String
        get() = when (type) {
            TypeAspect.Applying -> "applying to"
            TypeAspect.Separating -> "separating from"
        }

    companion object {
        fun fromCoordinates(
            body1: Coordinate,
            body2: Coordinate,
            orb: Double,
            date: Date = body1.date
        ): CelestialAspect? {
            val angle = abs(body1.longitude - body2.longitude) % 360.0
            val normalized = if (angle > 180) 360 - angle else angle

            val kind = Kind.values().firstOrNull {
                abs(normalized - it.angle) <= orb
            } ?: return null

            val faster = if (body1.velocity < body2.velocity) body1 else body2
            val slower = if (body1.velocity >= body2.velocity) body1 else body2
            val applying = faster.longitude < slower.longitude

            return CelestialAspect(
                kind = kind,
                body1 = faster,
                body2 = slower,
                angle = normalized,
                orb = orb,
                type = if (applying) TypeAspect.Applying else TypeAspect.Separating,
                startDate = null,
                endDate = null,
                name1 = null,
                name2 = null
            )
        }

        fun fromCoordinateAndCusp(body: Coordinate, cusp: Cusp, orb: Double): CelestialAspect? {
            val cuspCoord = Coordinate(cusp, body.date)
            return fromCoordinates(body, cuspCoord, orb)
        }

        fun fromCusps(cusp1: Cusp, cusp2: Cusp, orb: Double): CelestialAspect? {
            val now = Date()
            val coord1 = Coordinate(cusp1, now)
            val coord2 = Coordinate(cusp2, now)
            return fromCoordinates(coord1, coord2, orb)
        }
    }
}
