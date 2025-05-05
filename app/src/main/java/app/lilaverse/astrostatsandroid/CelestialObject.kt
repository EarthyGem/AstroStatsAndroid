package app.lilaverse.astrostatsandroid

sealed class CelestialObject {
    abstract val keyName: String

    data class Planet(val planet: app.lilaverse.astrostatsandroid.Planet) : CelestialObject() {
        override val keyName: String get() = planet.keyName
    }

    data class Cusp(val cusp: app.lilaverse.astrostatsandroid.Cusp) : CelestialObject() {
        override val keyName: String get() = "Cusp ${cusp.index}"
    }

    data class SpecialCusp(val name: String, val cusp: app.lilaverse.astrostatsandroid.Cusp) : CelestialObject() {
        override val keyName: String get() = name
    }

    object SouthNode : CelestialObject() {
        override val keyName: String = "South Node"
    }

    companion object {
        fun fromString(name: String): CelestialObject {
            return when (name.lowercase()) {
                "south node" -> SouthNode
                else -> {
                    val planet = app.lilaverse.astrostatsandroid.Planet.fromKeyName(name)
                        ?: throw IllegalArgumentException("Unknown celestial object: $name")
                    Planet(planet)
                }
            }
        }

        fun ascendantFrom(cusp: app.lilaverse.astrostatsandroid.Cusp): SpecialCusp {
            require(cusp.index == 1) { "Ascendant must be Cusp 1" }
            return SpecialCusp("Ascendant", cusp)
        }

        fun midheavenFrom(cusp: app.lilaverse.astrostatsandroid.Cusp): SpecialCusp {
            require(cusp.index == 10) { "Midheaven must be Cusp 10" }
            return SpecialCusp("Midheaven", cusp)
        }
    }
}
