package app.lilaverse.astrostatsandroid

enum class Kind(
    val angle: Int,
    val orb: Double,
    val degrees: String,
    val symbol: String,
    val description: String,
    val extendedDescription: String
) {
    Conjunction(0, 8.0, "0º", "☌", "conjunction", "Conjunction (Prominence)"),
    Semisextile(30, 2.0, "30º", "⚺", "semisextile", "Semisextile (Growth)"),
    Semisquare(45, 2.5, "45º", "∠", "semisquare", "Semisquare (Friction)"),
    Sextile(60, 4.0, "60º", "⚹", "sextile", "Sextile (Opportunity)"),
    Square(90, 6.0, "90º", "□", "square", "Square (Obstacle)"),
    Trine(120, 6.0, "120º", "△", "trine", "Trine (Luck)"),
    Sesquisquare(135, 2.5, "135º", "⚼", "sesquisquare", "Sesquisquare (Agitation)"),
    Inconjunction(150, 2.0, "150º", "⚻", "inconjunction", "Inconjunction (Expansion)"),
    Opposition(180, 8.0, "180º", "☍", "opposition", "Opposition (Separation)"),
    Parallel(0, 1.5, "0º Declination", "∥", "parallel", "Parallel (Intensity)");

    val isEasy get() = this in listOf(Sextile, Trine, Semisextile)
    val isHard get() = this in listOf(Square, Opposition, Semisquare, Sesquisquare)

    companion object {
        val primary = listOf(Conjunction, Sextile, Square, Trine, Opposition)
        val secondary = listOf(Semisextile, Semisquare, Sesquisquare, Inconjunction, Parallel)
        val harmoniousAspects = listOf(Semisextile, Sextile, Trine)
        val discordantAspects = listOf(Semisquare, Square, Sesquisquare, Opposition)
        val hardAspects = listOf(Conjunction, Square, Opposition)

        fun fromAngle(angle: Double): Kind? {
            val norm = angle % 360
            val closest = values().firstOrNull {
                val diff = kotlin.math.abs(norm - it.angle)
                diff <= it.orb
            }
            return closest
        }
    }
}
