package app.lilaverse.astrostatsandroid

import androidx.compose.ui.graphics.Color

object AstroColors {
    // Planet colors (based on their symbolic vibration)
    val Sun = Color(0xFFFFA500)       // Orange
    val Moon = Color(0xFF008B45)      // Darker Green (was #00FF7F)
    val Mercury = Color(0xFF8A2BE2)   // Violet
    val Venus = Color(0xFFB8860B)     // Dark Golden Yellow (was #FFFF00)
    val Mars = Color(0xFFFF0000)      // Red
    val Jupiter = Color(0xFF4B0082)   // Indigo
    val Saturn = Color(0xFF0000FF)    // Blue
    val Uranus = Color(0xFF000000)    // Black (was White)
    val Neptune = Color(0xFF4682B4)   // Steel Blue (darker aqua, was #AFEEEE)
    val Pluto = Color(0xFF8B0000)     // Deep Red

    // Sign mappings (masculine = light, feminine = dark)
    fun signColor(sign: String): Color = when (sign) {
        "Aries" -> Color(0xFFFFA07A)  // Light Red (Mars)
        "Taurus" -> Color(0xFFCCCC00) // Dark Yellow (Venus)
        "Gemini" -> Color(0xFFDA70D6) // Light Violet (Mercury)
        "Cancer" -> Color(0xFF006400) // Dark Green (Moon)
        "Leo" -> Color(0xFFFFD700)    // Light Orange (Sun)
        "Virgo" -> Color(0xFF800080)  // Dark Violet (Mercury)
        "Libra" -> Color(0xFFFFFF99)  // Light Yellow (Venus)
        "Scorpio" -> Color(0xFF800000) // Dark Red (Mars)
        "Sagittarius" -> Color(0xFF6A5ACD) // Light Indigo (Jupiter)
        "Capricorn" -> Color(0xFF000080) // Dark Blue (Saturn)
        "Aquarius" -> Color(0xFFDDDDFF) // Light Blue (Uranus)
        "Pisces" -> Color(0xFF40E0D0)   // Light Iridescent (Neptune)
        else -> Color.Gray
    }

    // Aspect → Planet mapping → Color
    fun aspectColor(aspect: String): Color = when (aspect) {
        "Conjunction" -> Sun
        "Semisextile" -> Moon
        "Sextile" -> Venus
        "Square" -> Mars
        "Trine" -> Jupiter
        "Inconjunct", "Quincunx" -> Neptune
        "Semisquare" -> Mercury
        "Opposition" -> Saturn
        "Sesquisquare" -> Uranus
        "Parallel" -> Pluto
        else -> Color.Gray
    }

    // Planet label → Color
    fun planetColor(planet: String): Color = when (planet) {
        "Sun" -> Sun
        "Moon" -> Moon
        "Mercury" -> Mercury
        "Venus" -> Venus
        "Mars" -> Mars
        "Jupiter" -> Jupiter
        "Saturn" -> Saturn
        "Uranus" -> Uranus
        "Neptune" -> Neptune
        "Pluto" -> Pluto
        else -> Color.Gray
    }
}
