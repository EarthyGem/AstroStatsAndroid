package app.lilaverse.astrostatsandroid

object Zodiac {
    private val signs = listOf(
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
        val normalized = (degree % 360 + 360) % 360
        val index = (normalized / 30).toInt()
        return signs[index % 12].name
    }

    fun glyphForDegree(degree: Double): String {
        val normalized = (degree % 360 + 360) % 360
        val index = (normalized / 30).toInt()
        return signs[index % 12].glyph
    }

    fun signAt(index: Int): Sign {
        return signs[index % 12]
    }

    data class Sign(
        val name: String,
        val glyph: String
    )
}
