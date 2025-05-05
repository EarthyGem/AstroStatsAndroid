package app.lilaverse.astrostatsandroid

object Zodiac {

    val signs = listOf(
        Sign("Aries", "♈︎"),
        Sign("Taurus", "♉︎"),
        Sign("Gemini", "♊︎"),
        Sign("Cancer", "♋︎"),
        Sign("Leo", "♌︎"),
        Sign("Virgo", "♍︎"),
        Sign("Libra", "♎︎"),
        Sign("Scorpio", "♏︎"),
        Sign("Sagittarius", "♐︎"),
        Sign("Capricorn", "♑︎"),
        Sign("Aquarius", "♒︎"),
        Sign("Pisces", "♓︎")
    )

    fun signForDegree(degree: Double): String {
        return from(degree).name
    }

    fun glyphForDegree(degree: Double): String {
        return from(degree).glyph
    }

    fun from(degree: Double): Sign {
        val normalized = (degree % 360 + 360) % 360
        val index = (normalized / 30).toInt()
        return signs[index % 12]
    }

    fun signAt(index: Int): Sign = signs[index % 12]

    fun fromName(name: String): Sign? =
        signs.firstOrNull { it.name.equals(name, ignoreCase = true) }

    fun allSigns(): List<Sign> = signs

    data class Sign(
        val name: String,
        val glyph: String
    ) {
        override fun toString(): String = "$name $glyph"
    }
}
