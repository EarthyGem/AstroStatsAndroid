package app.lilaverse.astrostatsandroid

sealed class CelestialObject {
    abstract val keyName: String

    data class Planet(val planet: app.lilaverse.astrostatsandroid.Planet) : CelestialObject() {
        override val keyName: String
            get() = planet.keyName
    }


    data class Cusp(val cusp: app.lilaverse.astrostatsandroid.Cusp) : CelestialObject() {
        override val keyName: String
            get() = "Cusp ${cusp.index}"
    }

    object Ascendant : CelestialObject() {
        override val keyName: String = "Ascendant"
    }

    object Midheaven : CelestialObject() {
        override val keyName: String = "Midheaven"
    }

    object NorthNode : CelestialObject() {
        override val keyName: String = "North Node"
    }

    object SouthNode : CelestialObject() {
        override val keyName: String = "South Node"
    }

    companion object {
        fun fromString(name: String): CelestialObject {
            return when (name.lowercase()) {
                "ascendant" -> Ascendant
                "midheaven" -> Midheaven
                "north node" -> NorthNode
                "south node" -> SouthNode
                else -> {
                    val planet = app.lilaverse.astrostatsandroid.Planet.fromKeyName(name)
                        ?: throw IllegalArgumentException("Unknown celestial object: $name")
                    Planet(planet)
                }
            }
        }
    }
}