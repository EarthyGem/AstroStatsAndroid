package app.lilaverse.astrostatsandroid

enum class Planet(
    val symbol: String,
    val keyName: String,
    val velocity: Double
) {
    Sun("☉", "Sun", 30.0),
    Moon("☾", "Moon", 2.25),
    Mercury("☿", "Mercury", 14.0),
    Venus("♀", "Venus", 23.0),
    Mars("♂︎", "Mars", 45.0),
    Jupiter("♃", "Jupiter", 365.0),
    Saturn("♄", "Saturn", 2.5 * 365),
    Uranus("♅", "Uranus", 7.0 * 365),
    Neptune("♆", "Neptune", 14.0 * 365),
    Pluto("♇", "Pluto", 20.67 * 365),
    SouthNode("☋", "South Node", 1.5 * 365);

    val celestialObject: CelestialObject
        get() = CelestialObject.Planet(this)

    companion object {
        val fast = listOf(Sun, Moon, Mercury, Venus, Mars)
        val slow = listOf(Jupiter, Saturn, Uranus, Neptune, Pluto, SouthNode)
        val fastBodies = fast.map { it.celestialObject }
        val slowBodies = slow.map { it.celestialObject }
        val businessPlanets = listOf(Jupiter, Saturn).map { it.celestialObject }
        val socialPlanets = listOf(Venus, Mars).map { it.celestialObject }

        fun fromKeyName(name: String): Planet? {
            return values().firstOrNull { it.keyName.equals(name, ignoreCase = true) }
        }
    }
}
