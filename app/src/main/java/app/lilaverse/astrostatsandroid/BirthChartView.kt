package app.lilaverse.astrostatsandroid.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import app.lilaverse.astrostatsandroid.*
import kotlin.math.*

// Define Planet class in the correct package
data class Planet(val name: String, val glyph: String, val degree: Double)

class BirthChartView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val baseFontSize: Float = 18f
    private val smallBaseFontSize: Float = 14f

    private var chart: ChartCake? = null
    private var planetPositions: Map<CelestialObject, Float> = mapOf()

    // For simplified use when not using ChartCake
    var planets: List<Planet> = listOf()
        set(value) {
            field = value
            invalidate()
        }

    var houseCusps: List<Double> = List(12) { it * 30.0 }
        set(value) {
            field = value
            invalidate()
        }

    // Gesture tracking variables
    private var scaleFactor = 1f
    private var translateX = 0f
    private var translateY = 0f
    private val scaleDetector = ScaleGestureDetector(context, ScaleListener())
    private var lastTouchX = 0f
    private var lastTouchY = 0f
    private var activePointerId = MotionEvent.INVALID_POINTER_ID

    // Paint objects with improved visibility
    private val circlePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 3.0f
        color = Color.rgb(100, 100, 200) // Darker blue for better visibility
    }

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = baseFontSize
        textAlign = Paint.Align.CENTER
        color = Color.rgb(0, 0, 0) // Black for best visibility
        typeface = Typeface.create(Typeface.SERIF, Typeface.NORMAL)
    }

    private val smallTextPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = smallBaseFontSize
        textAlign = Paint.Align.CENTER
        color = Color.rgb(0, 0, 0) // Black for best visibility
        typeface = Typeface.create(Typeface.SERIF, Typeface.NORMAL)
    }

    private val houseLinePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.rgb(100, 100, 200) // Darker blue for better visibility
        strokeWidth = 2.0f // Thicker lines
    }

    private val houseNumberPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.rgb(0, 0, 200) // Bright blue for better visibility
        textSize = baseFontSize
        textAlign = Paint.Align.CENTER
        typeface = Typeface.create(Typeface.SERIF, Typeface.BOLD)
    }

    // Zodiac signs and planet glyphs
    private val zodiacGlyphs = arrayOf("♈", "♉", "♊", "♋", "♌", "♍", "♎", "♏", "♐", "♑", "♒", "♓")

    // Colors for planets and signs - more saturated for visibility
    private val planetColors = mapOf(
        "☉" to Color.rgb(255, 128, 0),     // Sun - Brighter orange
        "☽" to Color.rgb(0, 190, 180),     // Moon - Teal
        "☿" to Color.rgb(138, 43, 226),    // Mercury - Purple
        "♀" to Color.rgb(255, 215, 0),     // Venus - Gold
        "♂" to Color.rgb(255, 0, 0),       // Mars - Red
        "♃" to Color.rgb(75, 0, 130),      // Jupiter - Indigo
        "♄" to Color.rgb(0, 0, 255),       // Saturn - Blue
        "♅" to Color.rgb(0, 255, 127),     // Uranus - Green
        "♆" to Color.rgb(0, 190, 255),     // Neptune - Brighter blue
        "♇" to Color.rgb(170, 0, 0),       // Pluto - Dark red
        "☋" to Color.rgb(210, 105, 30),    // South Node - Brown
        "☊" to Color.rgb(210, 105, 30),    // North Node - Brown
        "↑" to Color.rgb(0, 0, 0),         // Ascendant - Black
        "⊗" to Color.rgb(0, 0, 0)          // Midheaven - Black
    )

    private val signColors = mapOf(
        0 to Color.rgb(255, 0, 0),       // Aries - Red
        1 to Color.rgb(160, 82, 45),     // Taurus - Brown
        2 to Color.rgb(255, 215, 0),     // Gemini - Gold
        3 to Color.rgb(0, 128, 0),       // Cancer - Green
        4 to Color.rgb(255, 140, 0),     // Leo - Orange
        5 to Color.rgb(0, 100, 0),       // Virgo - Dark green
        6 to Color.rgb(255, 215, 0),     // Libra - Gold
        7 to Color.rgb(170, 0, 0),       // Scorpio - Dark red
        8 to Color.rgb(255, 140, 0),     // Sagittarius - Orange
        9 to Color.rgb(128, 0, 128),     // Capricorn - Purple
        10 to Color.rgb(0, 0, 200),      // Aquarius - Bright blue
        11 to Color.rgb(0, 128, 128)     // Pisces - Teal
    )

    // Set chart data and trigger redraw
    fun setChart(newChart: ChartCake) {
        chart = newChart
        updateChart()
        invalidate()
    }

    // Update the positions of planets to be drawn on the chart
    private fun updateChart() {
        val houseCusps = chart?.houseCusps ?: return
        val ascendantOffset = houseCusps.getCusp(0).longitude
        val bodies = chart?.bodies ?: return

        // Calculate planet positions adjusted for ascendant at 0 degrees
        val positions = bodies.associate { body ->
            val longitude = normalizeAngle(body.longitude - ascendantOffset)
            body.body to longitude.toFloat()
        }

        // Update the planet positions map
        planetPositions = positions
    }

    // Normalize angle to 0-360 range
    private fun normalizeAngle(angle: Double): Double {
        return (angle % 360 + 360) % 360
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawColor(Color.WHITE)
        val centerX = width / 2f
        val centerY = height / 2f
        val outerRadius = min(centerX, centerY) * 0.9f

        canvas.save()
        canvas.translate(translateX, translateY)
        canvas.scale(scaleFactor, scaleFactor, centerX, centerY)

        // Draw rings
        canvas.drawCircle(centerX, centerY, outerRadius, circlePaint)
        canvas.drawCircle(centerX, centerY, outerRadius * 0.41f, circlePaint)
        canvas.drawCircle(centerX, centerY, outerRadius * 0.34f, circlePaint)

        // Draw cusps and planets
        if (chart != null) {
            drawHouseCuspsWithChartCake(canvas, centerX, centerY, outerRadius)
            drawPlanetsWithChartCake(canvas, centerX, centerY, outerRadius * 0.95f)
        } else {
            drawHouseCuspsWithData(canvas, centerX, centerY, outerRadius)
            drawPlanetsWithData(canvas, centerX, centerY, outerRadius * 0.95f)
        }

        drawLilaLogo(canvas, centerX, centerY, outerRadius * 0.2f)

        canvas.restore()
    }


    private fun drawChartBackground(canvas: Canvas, centerX: Float, centerY: Float, outerRadius: Float) {
        // Draw outer circle
        canvas.drawCircle(centerX, centerY, outerRadius, circlePaint)

        // Draw inner circles - follow the Swift code ratios
        val innerRadius1 = outerRadius * 0.41f  // House number ring
        val innerRadius2 = outerRadius * 0.34f  // Inner circle

        canvas.drawCircle(centerX, centerY, innerRadius1, circlePaint)
        canvas.drawCircle(centerX, centerY, innerRadius2, circlePaint)
    }

    private fun drawLilaLogo(canvas: Canvas, centerX: Float, centerY: Float, radius: Float) {
        // Create a more visible logo
        val path = Path()
        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            strokeWidth = 2.5f
            color = Color.rgb(100, 100, 200)
        }

        // Create a hexagonal shape
        for (i in 0 until 6) {
            val angle = Math.toRadians((i * 60).toDouble())
            val x = centerX + radius * cos(angle).toFloat()
            val y = centerY + radius * sin(angle).toFloat()
            if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
        }
        path.close()
        canvas.drawPath(path, paint)

        // Add internal lines for crystal effect
        val innerPath = Path()
        for (i in 0 until 3) {
            val angle1 = Math.toRadians((i * 60).toDouble())
            val angle2 = Math.toRadians(((i + 3) * 60).toDouble())

            val x1 = centerX + radius * cos(angle1).toFloat()
            val y1 = centerY + radius * sin(angle1).toFloat()
            val x2 = centerX + radius * cos(angle2).toFloat()
            val y2 = centerY + radius * sin(angle2).toFloat()

            innerPath.moveTo(x1, y1)
            innerPath.lineTo(x2, y2)
        }

        // Draw inner lines with a gradient color
        paint.color = Color.rgb(150, 150, 220)
        canvas.drawPath(innerPath, paint)
    }

    private fun drawHouseCuspsWithChartCake(canvas: Canvas, centerX: Float, centerY: Float, outerRadius: Float) {
        val houseCusps = chart?.houseCusps ?: return
        val ascendantOffset = houseCusps.getCusp(0).longitude
        val innerRadius1 = outerRadius * 0.41f // Match the Swift code ratio
        val innerRadius2 = outerRadius * 0.34f

        // Before drawing, calculate house distances for proper spacing
        val houseDistances = calculateHouseDistances(houseCusps, ascendantOffset)
        var accumulatedAngle = 0f

        // Draw house lines and cusps
        for (i in 0 until 12) {
            val cusp = houseCusps.getCusp(i)
            val cuspAngle = normalizeAngle(cusp.longitude - ascendantOffset)
            // IMPORTANT FIX: Add 180 degrees to flip the chart orientation
            val angle = 2 * Math.PI - (Math.toRadians(cuspAngle -90) - Math.PI / 2)

            // Draw the house cusp line
            val startX = centerX + cos(angle).toFloat() * innerRadius2
            val startY = centerY + sin(angle).toFloat() * innerRadius2
            val endX = centerX + cos(angle).toFloat() * outerRadius
            val endY = centerY + sin(angle).toFloat() * outerRadius
            canvas.drawLine(startX, startY, endX, endY, houseLinePaint)

            // Draw house number in the middle of the house
            val houseNumberAngle = 2 * Math.PI - (Math.toRadians((accumulatedAngle + houseDistances[i] / 2 -90).toDouble()) - Math.PI / 2)
            val houseNumberRadius = (innerRadius1 + innerRadius2) / 2
            val houseNumberX = centerX + cos(houseNumberAngle).toFloat() * houseNumberRadius
            val houseNumberY = centerY + sin(houseNumberAngle).toFloat() * houseNumberRadius

            houseNumberPaint.textSize = baseFontSize * scaleFactor
            canvas.drawText(
                "${(i % 12) + 1}",
                houseNumberX,
                houseNumberY + houseNumberPaint.textSize / 3,
                houseNumberPaint
            )

            // Draw cusp degree, sign, and minute
            val cuspDegree = cusp.degreeInSign
            val cuspMinute = cusp.minuteInSign
            val signIndex = (cusp.normalizedLongitude / 30).toInt()

            // Position calculations for labels
            val degreeLabelRadius = outerRadius * 1.07f
            val minuteLabelRadius = outerRadius * 1.11f

            // Draw degree
            textPaint.textSize = baseFontSize * scaleFactor
            val degreeText = "${cuspDegree.toInt()}°"
            val degreeX = centerX + cos(angle).toFloat() * degreeLabelRadius
            val degreeY = centerY + sin(angle).toFloat() * degreeLabelRadius + textPaint.textSize / 3
            canvas.drawText(degreeText, degreeX, degreeY, textPaint)

            // Draw sign symbol
            val signPaint = Paint(textPaint).apply {
                color = signColors[signIndex % 12] ?: Color.BLACK
                textSize = baseFontSize * 1.2f * scaleFactor
            }
            val signGlyph = zodiacGlyphs[signIndex % 12]
            val signRadius = outerRadius * 1.03f
            val signX = centerX + cos(angle).toFloat() * signRadius
            val signY = centerY + sin(angle).toFloat() * signRadius + signPaint.textSize / 3
            canvas.drawText(signGlyph, signX, signY, signPaint)

            // Draw minute
            smallTextPaint.textSize = smallBaseFontSize * scaleFactor
            val minuteText = "${cuspMinute.toInt()}'"
            val minuteX = centerX + cos(angle).toFloat() * minuteLabelRadius
            val minuteY = centerY + sin(angle).toFloat() * minuteLabelRadius + smallTextPaint.textSize / 3
            canvas.drawText(minuteText, minuteX, minuteY, smallTextPaint)

            // Update accumulated angle for next house
            accumulatedAngle += houseDistances[i]
        }
    }

    private fun drawHouseCuspsWithData(canvas: Canvas, centerX: Float, centerY: Float, outerRadius: Float) {
        if (houseCusps.isEmpty()) return

        val innerRadius1 = outerRadius * 0.41f
        val innerRadius2 = outerRadius * 0.34f

        // Calculate house distances for proper spacing
        var prevAngle = 0f
        val houseDistances = FloatArray(12)

        for (i in 0 until min(houseCusps.size, 12)) {
            val currentAngle = houseCusps[i].toFloat()
            val nextAngle = houseCusps[(i + 1) % houseCusps.size].toFloat()

            houseDistances[i] = if (nextAngle < currentAngle) {
                (360f - currentAngle) + nextAngle
            } else {
                nextAngle - currentAngle
            }
        }

        // Draw house cusp lines
        for (i in 0 until min(houseCusps.size, 12)) {
            val cuspAngle = houseCusps[i]
            // IMPORTANT FIX: Add 180 degrees to flip the chart orientation
            val angle = 2 * Math.PI - (Math.toRadians(cuspAngle -90) - Math.PI / 2)

            val startX = centerX + cos(angle).toFloat() * innerRadius2
            val startY = centerY + sin(angle).toFloat() * innerRadius2
            val endX = centerX + cos(angle).toFloat() * outerRadius
            val endY = centerY + sin(angle).toFloat() * outerRadius

            canvas.drawLine(startX, startY, endX, endY, houseLinePaint)

            // Draw house number in the middle of the house section
            // IMPORTANT FIX: Add 180 degrees to flip the chart orientation
            val midAngle = 2 * Math.PI - (Math.toRadians((prevAngle + houseDistances[i] / 2 -90).toDouble()) - Math.PI / 2)
            val houseNumberRadius = (innerRadius1 + innerRadius2) / 2
            val houseNumberX = centerX + cos(midAngle).toFloat() * houseNumberRadius
            val houseNumberY = centerY + sin(midAngle).toFloat() * houseNumberRadius

            houseNumberPaint.textSize = baseFontSize * scaleFactor
            canvas.drawText(
                "${(i % 12) + 1}",
                houseNumberX,
                houseNumberY + houseNumberPaint.textSize / 3,
                houseNumberPaint
            )

            prevAngle += houseDistances[i]
        }
    }

    private fun drawPlanetsWithData(canvas: Canvas, centerX: Float, centerY: Float, planetRadius: Float) {
        if (planets.isEmpty()) {
            // Debug log when no planets are available
            Log.d("BirthChartView", "No planets to draw: planets list is empty")
            return
        }

        // Group planets by position to handle overlaps
        val MIN_SPACING = 4.0

        // Debug log
        Log.d("BirthChartView", "Drawing ${planets.size} planets")
        for (planet in planets) {
            Log.d("BirthChartView", "Planet: ${planet.name}, glyph: ${planet.glyph}, degree: ${planet.degree}")
        }

        // First, sort planets by position
        val sortedPlanets = planets.sortedBy { it.degree }

        // Group planets by proximity
        val planetGroups = mutableListOf<MutableList<Planet>>()

        for (planet in sortedPlanets) {
            var addedToGroup = false

            for (group in planetGroups) {
                val lastPlanet = group.last()
                if (abs(planet.degree - lastPlanet.degree) < MIN_SPACING) {
                    group.add(planet)
                    addedToGroup = true
                    break
                }
            }

            if (!addedToGroup) {
                planetGroups.add(mutableListOf(planet))
            }
        }

        // Now draw each group with staggered radii
        for (group in planetGroups) {
            for ((index, planet) in group.withIndex()) {
                // Calculate radius based on position in group
                val radiusOffset = index * 25f * scaleFactor
                val radius = planetRadius - radiusOffset

                // Convert to angle in radians
                // IMPORTANT FIX: Add 180 degrees to flip the chart orientation
                val angle = 2 * Math.PI - (Math.toRadians(planet.degree -90) - Math.PI / 2)

                // Draw planet glyph
                val planetX = centerX + cos(angle).toFloat() * radius
                val planetY = centerY + sin(angle).toFloat() * radius

                val planetPaint = Paint(textPaint).apply {
                    color = planetColors[planet.glyph] ?: Color.BLACK
                    textSize = baseFontSize * 2.5f * scaleFactor // Much larger for better visibility
                    isFakeBoldText = true // Bold for better visibility
                }

                canvas.drawText(planet.glyph, planetX, planetY + planetPaint.textSize / 3, planetPaint)

                // Log that we're drawing the planet
                Log.d("BirthChartView", "Drawing planet ${planet.glyph} at angle ${planet.degree} degrees, radius $radius")
            }
        }
    }

    private fun drawPlanetsWithChartCake(canvas: Canvas, centerX: Float, centerY: Float, baseRadius: Float) {
        if (planetPositions.isEmpty()) {
            Log.d("BirthChartView", "No planet positions to draw from ChartCake")
            return
        }

        // Debug log
        Log.d("BirthChartView", "Drawing ${planetPositions.size} planets from ChartCake")

        // Group planets by position to handle overlaps like in Swift code
        val MIN_SPACING = 4f

        // First, sort planets by position
        val sortedPlanets = planetPositions.entries.sortedBy { it.value }

        // Group planets by proximity
        val planetGroups = mutableListOf<MutableList<Pair<CelestialObject, Float>>>()

        for ((planet, position) in sortedPlanets) {
            var addedToGroup = false

            for (group in planetGroups) {
                val lastPlanet = group.last()
                if (abs(normalizeAngleDifference(position - lastPlanet.second)) < MIN_SPACING) {
                    group.add(planet to position)
                    addedToGroup = true
                    break
                }
            }

            if (!addedToGroup) {
                planetGroups.add(mutableListOf(planet to position))
            }
        }

        // Now draw each group with staggered radii
        for (group in planetGroups) {
            for ((index, pair) in group.withIndex()) {
                val (planet, position) = pair

                // Find the body
                val body = chart?.natalBodies?.find { it.body == planet } ?: continue

                // Calculate radius based on position in group
                val radiusOffset = index * 25f * scaleFactor
                val radius = baseRadius - radiusOffset

                // Convert to angle in radians (match Swift's angle calculation)
                // IMPORTANT FIX: Add 180 degrees to flip the chart orientation
                val angle = 2 * Math.PI - (Math.toRadians((position -90).toDouble()) - Math.PI / 2)

                // Draw the planet glyph
                val glyph = when (planet) {
                    is CelestialObject.Planet -> when (planet.planet) {
                        app.lilaverse.astrostatsandroid.Planet.Sun -> "☉"
                        app.lilaverse.astrostatsandroid.Planet.Moon -> "☽"
                        app.lilaverse.astrostatsandroid.Planet.Mercury -> "☿"
                        app.lilaverse.astrostatsandroid.Planet.Venus -> "♀"
                        app.lilaverse.astrostatsandroid.Planet.Mars -> "♂"
                        app.lilaverse.astrostatsandroid.Planet.Jupiter -> "♃"
                        app.lilaverse.astrostatsandroid.Planet.Saturn -> "♄"
                        app.lilaverse.astrostatsandroid.Planet.Uranus -> "♅"
                        app.lilaverse.astrostatsandroid.Planet.Neptune -> "♆"
                        app.lilaverse.astrostatsandroid.Planet.Pluto -> "♇"
                        app.lilaverse.astrostatsandroid.Planet.SouthNode -> "☋"
                    }
                    is CelestialObject.SouthNode -> "☋"
                    else -> "?"
                }

                val planetX = centerX + cos(angle).toFloat() * radius
                val planetY = centerY + sin(angle).toFloat() * radius

                val planetPaint = Paint(textPaint).apply {
                    color = planetColors[glyph] ?: Color.BLACK
                    textSize = baseFontSize * 2.5f * scaleFactor // Much larger for better visibility
                    isFakeBoldText = true // Bold for better visibility
                }

                canvas.drawText(glyph, planetX, planetY + planetPaint.textSize / 3, planetPaint)

                // Draw degree
                val degreeInSign = body.degreeInSign.toInt()
                val degreeRadius = radius - baseFontSize * 1.8f * scaleFactor
                val degreeX = centerX + cos(angle).toFloat() * degreeRadius
                val degreeY = centerY + sin(angle).toFloat() * degreeRadius

                textPaint.textSize = baseFontSize * 1.2f * scaleFactor
                canvas.drawText("${degreeInSign}°", degreeX, degreeY + textPaint.textSize / 3, textPaint)

                // Draw sign
                val signIndex = (body.longitude / 30).toInt() % 12
                val signGlyph = zodiacGlyphs[signIndex]
                val signRadius = degreeRadius - baseFontSize * 1.8f * scaleFactor
                val signX = centerX + cos(angle).toFloat() * signRadius
                val signY = centerY + sin(angle).toFloat() * signRadius

                val signPaint = Paint(textPaint).apply {
                    color = signColors[signIndex % 12] ?: Color.BLACK
                    textSize = baseFontSize * 1.8f * scaleFactor  // Larger for visibility
                    isFakeBoldText = true // Bold for better visibility
                }
                canvas.drawText(signGlyph, signX, signY + signPaint.textSize / 3, signPaint)

                // Draw minute
                val minuteInSign = ((body.degreeInSign - degreeInSign) * 60).toInt()
                val minuteRadius = signRadius - baseFontSize * 1.8f * scaleFactor
                val minuteX = centerX + cos(angle).toFloat() * minuteRadius
                val minuteY = centerY + sin(angle).toFloat() * minuteRadius

                smallTextPaint.textSize = smallBaseFontSize * 1.2f * scaleFactor
                canvas.drawText("${minuteInSign}'", minuteX, minuteY + smallTextPaint.textSize / 3, smallTextPaint)

                // Draw retrograde indicator if velocity is negative
                if (body.velocity < 0) {
                    val retrogradeRadius = minuteRadius - baseFontSize * 1.5f * scaleFactor
                    val retrogradeX = centerX + cos(angle).toFloat() * retrogradeRadius
                    val retrogradeY = centerY + sin(angle).toFloat() * retrogradeRadius
                    canvas.drawText("R", retrogradeX, retrogradeY + smallTextPaint.textSize / 3, smallTextPaint)
                }

                // Log that we're drawing the planet
                Log.d("BirthChartView", "Drawing planet ${planet.keyName} at angle $position degrees, radius $radius")
            }
        }
    }

    // Calculate house distances similar to Swift code
    private fun calculateHouseDistances(houseCusps: HouseCusps, ascendantOffset: Double): FloatArray {
        val distances = FloatArray(12)

        for (i in 0 until 12) {
            val currentIndex = i
            val nextIndex = (i + 1) % 12

            val current = normalizeAngle(houseCusps.getCusp(currentIndex).longitude - ascendantOffset).toFloat()
            val next = normalizeAngle(houseCusps.getCusp(nextIndex).longitude - ascendantOffset).toFloat()

            distances[i] = if (next < current) {
                (360f - current) + next
            } else {
                next - current
            }
        }

        return distances
    }

    // Normalize angle difference to be between -180 and 180 degrees
    private fun normalizeAngleDifference(diff: Float): Float {
        var result = diff % 360
        if (result > 180) result -= 360
        if (result < -180) result += 360
        return abs(result)
    }

    // Handle touch events for pinch-to-zoom and panning
    override fun onTouchEvent(event: MotionEvent): Boolean {
        scaleDetector.onTouchEvent(event)

        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                lastTouchX = event.x
                lastTouchY = event.y
                activePointerId = event.getPointerId(0)
            }

            MotionEvent.ACTION_MOVE -> {
                val pointerIndex = event.findPointerIndex(activePointerId)
                if (pointerIndex != -1 && !scaleDetector.isInProgress) {
                    val x = event.getX(pointerIndex)
                    val y = event.getY(pointerIndex)

                    val dx = x - lastTouchX
                    val dy = y - lastTouchY

                    translateX += dx
                    translateY += dy

                    invalidate()

                    lastTouchX = x
                    lastTouchY = y
                }
            }

            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                activePointerId = MotionEvent.INVALID_POINTER_ID
            }

            MotionEvent.ACTION_POINTER_UP -> {
                val pointerIndex = event.actionIndex
                val pointerId = event.getPointerId(pointerIndex)
                if (pointerId == activePointerId) {
                    val newPointerIndex = if (pointerIndex == 0) 1 else 0
                    lastTouchX = event.getX(newPointerIndex)
                    lastTouchY = event.getY(newPointerIndex)
                    activePointerId = event.getPointerId(newPointerIndex)
                }
            }
        }

        performClick() // For accessibility
        return true
    }

    override fun performClick(): Boolean {
        super.performClick()
        return true
    }

    // Scale gesture listener for pinch-to-zoom
    inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            scaleFactor *= detector.scaleFactor
            scaleFactor = scaleFactor.coerceIn(0.5f, 3.0f)
            invalidate()
            return true
        }
    }
}