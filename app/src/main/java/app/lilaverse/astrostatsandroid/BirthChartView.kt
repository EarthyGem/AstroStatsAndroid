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

class BirthChartView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val baseFontSize: Float = 12f
    private val smallBaseFontSize: Float = 8f

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

    // Paint objects
    private val circlePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 2f
        color = Color.rgb(120, 120, 220) // Light purple color
    }

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = baseFontSize
        textAlign = Paint.Align.CENTER
        color = Color.BLACK
    }

    private val smallTextPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = smallBaseFontSize
        textAlign = Paint.Align.CENTER
        color = Color.BLACK
    }

    private val houseLinePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.rgb(150, 150, 220)
        strokeWidth = 1f
    }

    private val planetPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
        textSize = baseFontSize * 1.2f
        textAlign = Paint.Align.CENTER
    }

    private val zodiacGlyphs = arrayOf("♈", "♉", "♊", "♋", "♌", "♍", "♎", "♏", "♐", "♑", "♒", "♓")
    private val planetGlyphs = mapOf(
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

        val centerX = width / 2f
        val centerY = height / 2f
        val outerRadius = min(centerX, centerY) * 0.9f

        // Apply transformations for zoom and pan
        canvas.save()
        canvas.translate(translateX, translateY)
        canvas.scale(scaleFactor, scaleFactor, centerX, centerY)

        // Draw the chart wheel components
        drawChartWheelBackground(canvas, centerX, centerY, outerRadius)

        if (chart != null) {
            // Draw using ChartCake data
            drawHouseCuspsWithChartCake(canvas, centerX, centerY, outerRadius)
            drawZodiacSigns(canvas, centerX, centerY, outerRadius)
            drawPlanetsWithChartCake(canvas, centerX, centerY, outerRadius * 0.8f)
        } else {
            // Draw using direct planet and house cusp data
            drawHouseCuspsWithData(canvas, centerX, centerY, outerRadius)
            drawZodiacSigns(canvas, centerX, centerY, outerRadius)
            drawPlanetsWithData(canvas, centerX, centerY, outerRadius * 0.8f)
        }

        canvas.restore()
    }

    private fun drawChartWheelBackground(canvas: Canvas, centerX: Float, centerY: Float, outerRadius: Float) {
        // Draw outer circle
        canvas.drawCircle(centerX, centerY, outerRadius, circlePaint)

        // Draw inner circles
        val innerRadius1 = outerRadius * 0.8f
        val innerRadius2 = outerRadius * 0.6f
        canvas.drawCircle(centerX, centerY, innerRadius1, circlePaint)
        canvas.drawCircle(centerX, centerY, innerRadius2, circlePaint)

        // Draw center logo/symbol
        val logoRadius = outerRadius * 0.2f
        val logoRect = RectF(
            centerX - logoRadius,
            centerY - logoRadius,
            centerX + logoRadius,
            centerY + logoRadius
        )

        // Draw hexagon as a placeholder for the logo
        val path = Path()
        for (i in 0 until 6) {
            val angle = Math.toRadians((i * 60).toDouble())
            val x = centerX + logoRadius * cos(angle).toFloat()
            val y = centerY + logoRadius * sin(angle).toFloat()
            if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
        }
        path.close()

        val logoPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            strokeWidth = 2f
            color = Color.rgb(100, 180, 180)
        }
        canvas.drawPath(path, logoPaint)

        // Draw a second hexagon with different color for layered effect
        val path2 = Path()
        val logoRadius2 = logoRadius * 0.8f
        for (i in 0 until 6) {
            val angle = Math.toRadians((i * 60 + 30).toDouble())
            val x = centerX + logoRadius2 * cos(angle).toFloat()
            val y = centerY + logoRadius2 * sin(angle).toFloat()
            if (i == 0) path2.moveTo(x, y) else path2.lineTo(x, y)
        }
        path2.close()

        val logoPaint2 = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            strokeWidth = 2f
            color = Color.rgb(120, 100, 180)
        }
        canvas.drawPath(path2, logoPaint2)
    }

    private fun drawHouseCuspsWithChartCake(canvas: Canvas, centerX: Float, centerY: Float, outerRadius: Float) {
        val houseCusps = chart?.houseCusps ?: return
        val ascendantAngle = houseCusps.getCusp(0).longitude
        val innerRadius = outerRadius * 0.6f

        // Draw house cusp lines
        for (i in 0 until 12) {
            val cusp = houseCusps.getCusp(i)
            val cuspAngle = normalizeAngle(cusp.longitude - ascendantAngle)
            val angle = Math.toRadians(cuspAngle) - Math.PI / 2 // Adjust for canvas coordinate system

            val startX = centerX + cos(angle) * innerRadius
            val startY = centerY + sin(angle) * innerRadius
            val endX = centerX + cos(angle) * outerRadius
            val endY = centerY + sin(angle) * outerRadius

            canvas.drawLine(startX.toFloat(), startY.toFloat(), endX.toFloat(), endY.toFloat(), houseLinePaint)

            // Draw house number
            val houseNumberRadius = innerRadius + (outerRadius - innerRadius) * 0.5f
            val houseNumberX = centerX + cos(angle) * houseNumberRadius
            val houseNumberY = centerY + sin(angle) * houseNumberRadius

            smallTextPaint.textSize = smallBaseFontSize * scaleFactor
            canvas.drawText(
                (i + 1).toString(),
                houseNumberX.toFloat(),
                houseNumberY.toFloat() + smallTextPaint.textSize / 3,
                smallTextPaint
            )

            // Draw cusp degree and sign
            val cuspDegree = cusp.degreeInSign
            val cuspMinute = cusp.minuteInSign
            val signIndex = (cusp.normalizedLongitude / 30).toInt()
            val signGlyph = zodiacGlyphs[signIndex]

            val labelRadius = outerRadius + 10
            val labelAngle = angle.toFloat()

            // Draw sign glyph
            val signX = centerX + cos(labelAngle) * labelRadius
            val signY = centerY + sin(labelAngle) * labelRadius
            textPaint.textSize = baseFontSize * scaleFactor
            canvas.drawText(signGlyph, signX, signY, textPaint)

            // Draw degree
            val degreeX = centerX + cos(labelAngle) * (labelRadius + textPaint.textSize * 1.2f)
            val degreeY = centerY + sin(labelAngle) * (labelRadius + textPaint.textSize * 1.2f)
            canvas.drawText("${cuspDegree}°", degreeX, degreeY, textPaint)

            // Draw minute
            val minuteX = centerX + cos(labelAngle) * (labelRadius + textPaint.textSize * 2.4f)
            val minuteY = centerY + sin(labelAngle) * (labelRadius + textPaint.textSize * 2.4f)
            canvas.drawText("${cuspMinute}'", minuteX, minuteY, smallTextPaint)
        }
    }

    private fun drawHouseCuspsWithData(canvas: Canvas, centerX: Float, centerY: Float, outerRadius: Float) {
        val innerRadius = outerRadius * 0.6f

        // Draw house cusp lines
        for (i in 0 until min(houseCusps.size, 12)) {
            val cuspAngle = houseCusps[i].toFloat()
            val angle = Math.toRadians(cuspAngle.toDouble()) - Math.PI / 2 // Adjust for canvas coordinate system

            val startX = centerX + cos(angle) * innerRadius
            val startY = centerY + sin(angle) * innerRadius
            val endX = centerX + cos(angle) * outerRadius
            val endY = centerY + sin(angle) * outerRadius

            canvas.drawLine(startX.toFloat(), startY.toFloat(), endX.toFloat(), endY.toFloat(), houseLinePaint)

            // Draw house number
            val houseNumberRadius = innerRadius + (outerRadius - innerRadius) * 0.5f
            val houseNumberX = centerX + cos(angle) * houseNumberRadius
            val houseNumberY = centerY + sin(angle) * houseNumberRadius

            smallTextPaint.textSize = smallBaseFontSize * scaleFactor
            canvas.drawText(
                (i + 1).toString(),
                houseNumberX.toFloat(),
                houseNumberY.toFloat() + smallTextPaint.textSize / 3,
                smallTextPaint
            )
        }
    }

    private fun drawZodiacSigns(canvas: Canvas, centerX: Float, centerY: Float, outerRadius: Float) {
        val zodiacRadius = outerRadius + 20

        for (i in 0 until 12) {
            val angle = Math.toRadians(i * 30.0) - Math.PI / 2 // Adjust for canvas coordinate system
            val x = centerX + cos(angle) * zodiacRadius
            val y = centerY + sin(angle) * zodiacRadius

            textPaint.textSize = baseFontSize * 1.2f * scaleFactor
            canvas.drawText(zodiacGlyphs[i], x.toFloat(), y.toFloat() + textPaint.textSize / 3, textPaint)
        }
    }

    private fun drawPlanetsWithChartCake(canvas: Canvas, centerX: Float, centerY: Float, planetRadius: Float) {
        if (planetPositions.isEmpty()) return

        // Sort planets by position to handle overlaps
        val sortedPlanets = planetPositions.entries.sortedBy { it.value }
        val planetSpacing = 20f // Minimum spacing between planets in degrees

        // Adjust positions to avoid overlaps
        val adjustedPositions = mutableMapOf<CelestialObject, Float>()
        var lastPosition = -planetSpacing

        for ((planet, position) in sortedPlanets) {
            var adjustedPosition = position

            // If this planet is too close to the last one, adjust its position
            if (position - lastPosition < planetSpacing) {
                adjustedPosition = lastPosition + planetSpacing
            }

            adjustedPositions[planet] = adjustedPosition
            lastPosition = adjustedPosition
        }

        // Draw planets with their adjusted positions
        for ((planet, position) in adjustedPositions) {
            val angle = Math.toRadians(position.toDouble()) - Math.PI / 2 // Adjust for canvas coordinate system

            // Find the corresponding coordinate for more details
            val coordinate = chart?.natalBodies?.find { it.body == planet } ?: continue

            // Draw planet glyph
            val glyph = planetGlyphs[planet.keyName] ?: "?"
            val planetX = centerX + cos(angle) * planetRadius
            val planetY = centerY + sin(angle) * planetRadius

            planetPaint.textSize = baseFontSize * 1.4f * scaleFactor
            canvas.drawText(glyph, planetX.toFloat(), planetY.toFloat() + planetPaint.textSize / 3, planetPaint)

            // Draw degree in sign
            val degreeInSign = coordinate.degreeInSign.toInt()
            val degreeX = centerX + cos(angle) * (planetRadius - 25f)
            val degreeY = centerY + sin(angle) * (planetRadius - 25f)

            textPaint.textSize = baseFontSize * scaleFactor
            canvas.drawText("${degreeInSign}°", degreeX.toFloat(), degreeY.toFloat() + textPaint.textSize / 3, textPaint)

            // Draw sign glyph
            // Use index instead of ordinal to fix the error
            val signIndex = (coordinate.longitude / 30).toInt() % 12
            val signGlyph = zodiacGlyphs[signIndex]
            val signX = centerX + cos(angle) * (planetRadius - 50f)
            val signY = centerY + sin(angle) * (planetRadius - 50f)
            canvas.drawText(signGlyph, signX.toFloat(), signY.toFloat() + textPaint.textSize / 3, textPaint)

            // Draw minute
            val minuteInSign = ((coordinate.degreeInSign - degreeInSign) * 60).toInt()
            val minuteX = centerX + cos(angle) * (planetRadius - 75f)
            val minuteY = centerY + sin(angle) * (planetRadius - 75f)

            smallTextPaint.textSize = smallBaseFontSize * scaleFactor
            canvas.drawText("${minuteInSign}'", minuteX.toFloat(), minuteY.toFloat() + smallTextPaint.textSize / 3, smallTextPaint)

            // Optional: Draw retrograde symbol for retrograde planets
            // We'd need to add logic to determine if a planet is retrograde
        }
    }

    private fun drawPlanetsWithData(canvas: Canvas, centerX: Float, centerY: Float, planetRadius: Float) {
        if (planets.isEmpty()) return

        // Draw each planet
        for (planet in planets) {
            val angle = Math.toRadians(planet.degree) - Math.PI / 2 // Adjust for canvas coordinate system

            // Draw planet glyph
            val planetX = centerX + cos(angle) * planetRadius
            val planetY = centerY + sin(angle) * planetRadius

            planetPaint.textSize = baseFontSize * 1.4f * scaleFactor
            canvas.drawText(
                planet.glyph,
                planetX.toFloat(),
                planetY.toFloat() + planetPaint.textSize / 3,
                planetPaint
            )
        }
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

// Helper data class for using the BirthChartView without ChartCake
data class Planet(val name: String, val glyph: String, val degree: Double)