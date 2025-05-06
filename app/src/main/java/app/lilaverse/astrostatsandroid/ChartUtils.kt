package app.lilaverse.astrostatsandroid

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

/**
 * Utility class for chart-related functions and zodiac sign data
 */
object ChartUtils {
    // Zodiac sign glyphs for UI display
    val zodiacGlyphs = mapOf(
        "Aries" to "♈",
        "Taurus" to "♉",
        "Gemini" to "♊",
        "Cancer" to "♋",
        "Leo" to "♌",
        "Virgo" to "♍",
        "Libra" to "♎",
        "Scorpio" to "♏",
        "Sagittarius" to "♐",
        "Capricorn" to "♑",
        "Aquarius" to "♒",
        "Pisces" to "♓"
    )
    
    // Planet and point glyphs for UI display
    val planetGlyphs = mapOf(
        "Sun" to "☉",
        "Moon" to "☽",
        "Mercury" to "☿",
        "Venus" to "♀",
        "Mars" to "♂",
        "Jupiter" to "♃",
        "Saturn" to "♄",
        "Uranus" to "♅",
        "Neptune" to "♆",
        "Pluto" to "♇",
        "South Node" to "☋",
        "North Node" to "☊",
        "Ascendant" to "↑",
        "Midheaven" to "⊗"
    )
    
    // Format date for display in chart
    fun formatChartDate(date: Date): String {
        val format = SimpleDateFormat("MMM d, yyyy 'at' h:mma", Locale.getDefault())
        return format.format(date)
    }
    
    // Get formatted degrees and minutes from a decimal longitude
    fun formatDegreeMinute(longitude: Double): Pair<Int, Int> {
        val degree = longitude.toInt()
        val minute = ((longitude - degree) * 60).toInt()
        return Pair(degree, minute)
    }
    
    // Format longitude as sign, degree, minute
    fun formatLongitude(longitude: Double): String {
        val normalizedLon = ((longitude % 360) + 360) % 360
        val signIndex = (normalizedLon / 30).toInt()
        val degreeInSign = normalizedLon % 30
        val (degree, minute) = formatDegreeMinute(degreeInSign)
        
        val signName = Zodiac.signAt(signIndex).name
        val signGlyph = zodiacGlyphs[signName] ?: ""
        
        return "$degree° $minute' $signName $signGlyph"
    }
    
    // Calculate house sizes in degrees
    fun calculateHouseSizes(houseCusps: HouseCusps): List<Double> {
        val sizes = mutableListOf<Double>()
        val cusps = houseCusps.allCusps()
        
        for (i in cusps.indices) {
            val current = cusps[i].normalizedLongitude
            val next = cusps[(i + 1) % cusps.size].normalizedLongitude
            
            val size = if (next < current) {
                (360 - current) + next
            } else {
                next - current
            }
            
            sizes.add(size)
        }
        
        return sizes
    }
    
    // Adjust planet positions to avoid overlaps
    fun adjustPlanetPositions(positions: Map<CelestialObject, Double>, minDistance: Double = 5.0): Map<CelestialObject, Double> {
        if (positions.size <= 1) return positions
        
        // First, sort positions by their longitude
        val sortedEntries = positions.entries.sortedBy { it.value }
        val result = mutableMapOf<CelestialObject, Double>()
        
        // Initialize with the first planet
        result[sortedEntries.first().key] = sortedEntries.first().value
        var lastPosition = sortedEntries.first().value
        
        // Process remaining planets
        for (i in 1 until sortedEntries.size) {
            val (planet, position) = sortedEntries[i]
            val delta = normalizeAngleDifference(position - lastPosition)
            
            if (delta < minDistance) {
                // Need to adjust this planet's position
                val newPosition = (lastPosition + minDistance) % 360
                result[planet] = newPosition
                lastPosition = newPosition
            } else {
                // No adjustment needed
                result[planet] = position
                lastPosition = position
            }
        }
        
        return result
    }
    
    // Normalize angle difference to -180 to +180 range
    fun normalizeAngleDifference(diff: Double): Double {
        var result = diff % 360
        if (result > 180) result -= 360
        if (result < -180) result += 360
        return abs(result)
    }
    
    // Find strongest planet in the chart by power score
    fun findStrongestPlanet(powerScores: Map<Coordinate, Double>): String? {
        if (powerScores.isEmpty()) return null
        
        val strongest = powerScores.entries.maxByOrNull { it.value }
        return strongest?.key?.body?.keyName
    }
    
    // Find strongest sign in the chart by sign scores
    fun findStrongestSign(signScores: Map<Zodiac.Sign, Double>): String? {
        if (signScores.isEmpty()) return null
        
        val strongest = signScores.entries.maxByOrNull { it.value }
        return strongest?.key?.name
    }
    
    // Find strongest house in the chart by house scores
    fun findStrongestHouse(houseScores: Map<Int, Double>): String? {
        if (houseScores.isEmpty()) return null
        
        val strongest = houseScores.entries.maxByOrNull { it.value }
        return strongest?.key?.toString()
    }
    
    // Log chart data for debugging
    fun logChartData(chartCake: ChartCake, powerScores: Map<Coordinate, Double>) {
        Log.d("ChartUtils", "📊 Chart Data Summary:")
        
        // Log planet positions and power scores
        Log.d("ChartUtils", "🪐 Planet Positions and Power:")
        chartCake.natalBodies.forEach { body ->
            val score = powerScores[body] ?: 0.0
            Log.d("ChartUtils", "${body.body.keyName}: ${formatLongitude(body.longitude)} - Power: $score")
        }
        
        // Log house cusps
        Log.d("ChartUtils", "🏠 House Cusps:")
        val houseCusps = chartCake.houseCusps
        for (i in 0 until 12) {
            val cusp = houseCusps.getCusp(i)
            Log.d("ChartUtils", "House ${i+1}: ${formatLongitude(cusp.longitude)}")
        }
    }
}