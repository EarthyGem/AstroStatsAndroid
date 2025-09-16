package app.lilaverse.astrostatsandroid.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import app.lilaverse.astrostatsandroid.CelestialObject
import app.lilaverse.astrostatsandroid.ChartCake
import app.lilaverse.astrostatsandroid.Coordinate
import app.lilaverse.astrostatsandroid.HouseCusps
import app.lilaverse.astrostatsandroid.Planet
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

/**
 * Custom view that renders a bi-wheel chart showing natal placements on the inner wheel
 * and major progressed placements on the outer wheel. The implementation mirrors the
 * drawing rules used by [BirthChartView] so that glyph sizes, spacing logic and overlap
 * handling remain consistent across chart presentations.
 */
class MajorProgressionsBiwheelView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val baseFontSize: Float = 18f
    private val smallBaseFontSize: Float = 14f

    private var chartCake: ChartCake? = null
    private var natalHouseCusps: HouseCusps? = null
    private var progressedHouseCusps: HouseCusps? = null
    private var natalBodies: List<Coordinate> = emptyList()
    private var progressedBodies: List<Coordinate> = emptyList()
    private var natalPositions: Map<CelestialObject, Float> = emptyMap()
    private var progressedPositions: Map<CelestialObject, Float> = emptyMap()

    // Gesture tracking variables (match BirthChartView)
    private var scaleFactor = 1f
    private var translateX = 0f
    private var translateY = 0f
    private val scaleDetector = ScaleGestureDetector(context, ScaleListener())
    private var lastTouchX = 0f
    private var lastTouchY = 0f
    private var activePointerId = MotionEvent.INVALID_POINTER_ID

    // Shared paints
    private val outerRingPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 3f
        color = Color.rgb(90, 90, 170)
    }
    private val innerRingPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 2.5f
        color = Color.rgb(150, 150, 210)
    }

    private val progressedLinePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.rgb(60, 120, 220)
        strokeWidth = 2.5f
    }
    private val natalLinePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.rgb(170, 70, 160)
        strokeWidth = 2.5f
    }

    private val progressedNumberPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.rgb(50, 100, 190)
        textSize = baseFontSize
        textAlign = Paint.Align.CENTER
        typeface = Typeface.create(Typeface.SERIF, Typeface.BOLD)
    }
    private val natalNumberPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.rgb(150, 50, 130)
        textSize = baseFontSize
        textAlign = Paint.Align.CENTER
        typeface = Typeface.create(Typeface.SERIF, Typeface.BOLD)
    }

    private val progressedTextPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.DKGRAY
        textSize = baseFontSize
        textAlign = Paint.Align.CENTER
        typeface = Typeface.create(Typeface.SERIF, Typeface.NORMAL)
    }
    private val progressedSmallTextPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.DKGRAY
        textSize = smallBaseFontSize
        textAlign = Paint.Align.CENTER
        typeface = Typeface.create(Typeface.SERIF, Typeface.NORMAL)
    }
    private val natalTextPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.rgb(80, 40, 120)
        textSize = baseFontSize
        textAlign = Paint.Align.CENTER
        typeface = Typeface.create(Typeface.SERIF, Typeface.NORMAL)
    }
    private val natalSmallTextPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.rgb(80, 40, 120)
        textSize = smallBaseFontSize
        textAlign = Paint.Align.CENTER
        typeface = Typeface.create(Typeface.SERIF, Typeface.NORMAL)
    }

    private val zodiacGlyphs = arrayOf("♈", "♉", "♊", "♋", "♌", "♍", "♎", "♏", "♐", "♑", "♒", "♓")

    private val planetColors = mapOf(
        "☉" to Color.rgb(255, 128, 0),
        "☽" to Color.rgb(0, 190, 180),
        "☿" to Color.rgb(138, 43, 226),
        "♀" to Color.rgb(255, 215, 0),
        "♂" to Color.rgb(255, 0, 0),
        "♃" to Color.rgb(75, 0, 130),
        "♄" to Color.rgb(0, 0, 255),
        "♅" to Color.rgb(0, 255, 127),
        "♆" to Color.rgb(0, 190, 255),
        "♇" to Color.rgb(170, 0, 0),
        "☋" to Color.rgb(210, 105, 30)
    )

    private val signColors = mapOf(
        0 to Color.rgb(255, 0, 0),
        1 to Color.rgb(160, 82, 45),
        2 to Color.rgb(255, 215, 0),
        3 to Color.rgb(0, 128, 0),
        4 to Color.rgb(255, 140, 0),
        5 to Color.rgb(0, 100, 0),
        6 to Color.rgb(255, 215, 0),
        7 to Color.rgb(170, 0, 0),
        8 to Color.rgb(255, 140, 0),
        9 to Color.rgb(128, 0, 128),
        10 to Color.rgb(0, 0, 200),
        11 to Color.rgb(0, 128, 128)
    )

    fun setChartCake(chartCake: ChartCake) {
        this.chartCake = chartCake
        updateChartData()
        invalidate()
    }

    fun clearChart() {
        chartCake = null
        natalHouseCusps = null
        progressedHouseCusps = null
        natalBodies = emptyList()
        progressedBodies = emptyList()
        natalPositions = emptyMap()
        progressedPositions = emptyMap()
        invalidate()
    }

    private fun updateChartData() {
        val cake = chartCake ?: return
        natalHouseCusps = cake.houseCusps
        progressedHouseCusps = cake.majorHouseCusps
        natalBodies = cake.natalBodies
        progressedBodies = cake.majorBodies

        natalPositions = computePositions(natalBodies, cake.houseCusps)
        progressedPositions = computePositions(progressedBodies, cake.majorHouseCusps)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(Color.WHITE)

        val centerX = width / 2f
        val centerY = height / 2f
        val baseRadius = min(centerX, centerY) * 0.9f

        canvas.save()
        canvas.translate(translateX, translateY)
        canvas.scale(scaleFactor, scaleFactor, centerX, centerY)

        drawChartBackground(canvas, centerX, centerY, baseRadius)
        drawProgressedLayer(canvas, centerX, centerY, baseRadius)
        drawNatalLayer(canvas, centerX, centerY, baseRadius * 0.68f)
        drawLilaLogo(canvas, centerX, centerY, baseRadius * 0.18f)

        canvas.restore()
    }

    private fun drawChartBackground(canvas: Canvas, centerX: Float, centerY: Float, outerRadius: Float) {
        canvas.drawCircle(centerX, centerY, outerRadius, outerRingPaint)
        canvas.drawCircle(centerX, centerY, outerRadius * 0.78f, innerRingPaint)
        canvas.drawCircle(centerX, centerY, outerRadius * 0.68f, innerRingPaint)
        canvas.drawCircle(centerX, centerY, outerRadius * 0.46f, innerRingPaint)
    }

    private fun drawProgressedLayer(canvas: Canvas, centerX: Float, centerY: Float, outerRadius: Float) {
        val cusps = progressedHouseCusps ?: return
        drawHouseRing(
            canvas = canvas,
            houseCusps = cusps,
            centerX = centerX,
            centerY = centerY,
            outerRadius = outerRadius,
            innerRadius = outerRadius * 0.78f,
            numberPaint = progressedNumberPaint.apply { textSize = baseFontSize * scaleFactor },
            degreePaint = progressedTextPaint,
            minutePaint = progressedSmallTextPaint,
            linePaint = progressedLinePaint,
            degreeRadius = outerRadius * 1.05f,
            signRadius = outerRadius * 1.01f,
            minuteRadius = outerRadius * 1.09f
        )
        drawPlanets(
            canvas = canvas,
            centerX = centerX,
            centerY = centerY,
            baseRadius = outerRadius * 0.92f,
            bodies = progressedBodies,
            positions = progressedPositions,
            glyphScale = 2.6f,
            textScale = 1.2f,
            glyphColorTransform = { it },
            textColor = Color.DKGRAY,
            minuteColor = Color.DKGRAY
        )
    }

    private fun drawNatalLayer(canvas: Canvas, centerX: Float, centerY: Float, ringOuterRadius: Float) {
        val cusps = natalHouseCusps ?: return
        drawHouseRing(
            canvas = canvas,
            houseCusps = cusps,
            centerX = centerX,
            centerY = centerY,
            outerRadius = ringOuterRadius,
            innerRadius = ringOuterRadius * 0.78f,
            numberPaint = natalNumberPaint.apply { textSize = baseFontSize * 0.9f * scaleFactor },
            degreePaint = natalTextPaint,
            minutePaint = natalSmallTextPaint,
            linePaint = natalLinePaint,
            degreeRadius = ringOuterRadius * 0.94f,
            signRadius = ringOuterRadius * 0.88f,
            minuteRadius = ringOuterRadius * 0.82f
        )
        drawPlanets(
            canvas = canvas,
            centerX = centerX,
            centerY = centerY,
            baseRadius = ringOuterRadius * 0.9f,
            bodies = natalBodies,
            positions = natalPositions,
            glyphScale = 2.2f,
            textScale = 1.0f,
            glyphColorTransform = { lightenColor(it, 0.55f) },
            textColor = natalTextPaint.color,
            minuteColor = natalSmallTextPaint.color
        )
    }

    private fun drawHouseRing(
        canvas: Canvas,
        houseCusps: HouseCusps,
        centerX: Float,
        centerY: Float,
        outerRadius: Float,
        innerRadius: Float,
        numberPaint: Paint,
        degreePaint: Paint,
        minutePaint: Paint,
        linePaint: Paint,
        degreeRadius: Float,
        signRadius: Float,
        minuteRadius: Float
    ) {
        val ascendantOffset = houseCusps.getCusp(0).longitude
        val houseDistances = calculateHouseDistances(houseCusps, ascendantOffset)
        var accumulatedAngle = 0f

        for (i in 0 until 12) {
            val cusp = houseCusps.getCusp(i)
            val cuspAngle = normalizeAngle(cusp.longitude - ascendantOffset)
            val angle = 2 * Math.PI - (Math.toRadians(cuspAngle - 90) - Math.PI / 2)

            val startX = centerX + cos(angle).toFloat() * innerRadius
            val startY = centerY + sin(angle).toFloat() * innerRadius
            val endX = centerX + cos(angle).toFloat() * outerRadius
            val endY = centerY + sin(angle).toFloat() * outerRadius
            canvas.drawLine(startX, startY, endX, endY, linePaint)

            val houseNumberAngle = 2 * Math.PI - (Math.toRadians((accumulatedAngle + houseDistances[i] / 2 - 90).toDouble()) - Math.PI / 2)
            val numberRadius = (outerRadius + innerRadius) / 2
            val numberX = centerX + cos(houseNumberAngle).toFloat() * numberRadius
            val numberY = centerY + sin(houseNumberAngle).toFloat() * numberRadius
            canvas.drawText("${(i % 12) + 1}", numberX, numberY + numberPaint.textSize / 3, numberPaint)

            val degreePaintCopy = Paint(degreePaint).apply { textSize = baseFontSize * scaleFactor }
            val degreeText = "${cusp.degreeInSign.toInt()}°"
            val degreeX = centerX + cos(angle).toFloat() * degreeRadius
            val degreeY = centerY + sin(angle).toFloat() * degreeRadius + degreePaintCopy.textSize / 3
            canvas.drawText(degreeText, degreeX, degreeY, degreePaintCopy)

            val signIndex = (cusp.normalizedLongitude / 30).toInt() % 12
            val signPaint = Paint(degreePaint).apply {
                color = signColors[signIndex] ?: degreePaint.color
                textSize = baseFontSize * 1.2f * scaleFactor
                isFakeBoldText = true
            }
            val signGlyph = zodiacGlyphs[signIndex]
            val signX = centerX + cos(angle).toFloat() * signRadius
            val signY = centerY + sin(angle).toFloat() * signRadius + signPaint.textSize / 3
            canvas.drawText(signGlyph, signX, signY, signPaint)

            val minutePaintCopy = Paint(minutePaint).apply { textSize = smallBaseFontSize * scaleFactor }
            val minuteText = "${cusp.minuteInSign.toInt()}'"
            val minuteX = centerX + cos(angle).toFloat() * minuteRadius
            val minuteY = centerY + sin(angle).toFloat() * minuteRadius + minutePaintCopy.textSize / 3
            canvas.drawText(minuteText, minuteX, minuteY, minutePaintCopy)

            accumulatedAngle += houseDistances[i]
        }
    }

    private fun drawPlanets(
        canvas: Canvas,
        centerX: Float,
        centerY: Float,
        baseRadius: Float,
        bodies: List<Coordinate>,
        positions: Map<CelestialObject, Float>,
        glyphScale: Float,
        textScale: Float,
        glyphColorTransform: (Int) -> Int,
        textColor: Int,
        minuteColor: Int
    ) {
        if (positions.isEmpty() || bodies.isEmpty()) return

        val sortedPlanets = positions.entries.sortedBy { it.value }
        val groups = mutableListOf<MutableList<Pair<CelestialObject, Float>>>()
        val minSpacing = 4f

        for ((planet, position) in sortedPlanets) {
            var added = false
            for (group in groups) {
                val last = group.last()
                if (abs(normalizeAngleDifference(position - last.second)) < minSpacing) {
                    group.add(planet to position)
                    added = true
                    break
                }
            }
            if (!added) {
                groups.add(mutableListOf(planet to position))
            }
        }

        for (group in groups) {
            group.forEachIndexed { index, (planet, position) ->
                val body = bodies.find { it.body == planet } ?: return@forEachIndexed
                val radiusOffset = index * 24f * scaleFactor
                val radius = baseRadius - radiusOffset
                val angle = 2 * Math.PI - (Math.toRadians((position - 90).toDouble()) - Math.PI / 2)

                val glyph = glyphFor(planet)
                val glyphPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                    textAlign = Paint.Align.CENTER
                    textSize = baseFontSize * glyphScale * scaleFactor
                    isFakeBoldText = true
                    typeface = Typeface.create(Typeface.SERIF, Typeface.BOLD)
                    color = glyphColorTransform(planetColors[glyph] ?: textColor)
                }
                val planetX = centerX + cos(angle).toFloat() * radius
                val planetY = centerY + sin(angle).toFloat() * radius
                canvas.drawText(glyph, planetX, planetY + glyphPaint.textSize / 3, glyphPaint)

                val degreePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                    textAlign = Paint.Align.CENTER
                    textSize = baseFontSize * textScale * scaleFactor
                    typeface = Typeface.create(Typeface.SERIF, Typeface.NORMAL)
                    color = textColor
                }
                val degreeRadius = radius - baseFontSize * 1.4f * scaleFactor
                val degreeX = centerX + cos(angle).toFloat() * degreeRadius
                val degreeY = centerY + sin(angle).toFloat() * degreeRadius + degreePaint.textSize / 3
                canvas.drawText("${body.degreeInSign.toInt()}°", degreeX, degreeY, degreePaint)

                val signIndex = (body.longitude / 30).toInt() % 12
                val signPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                    textAlign = Paint.Align.CENTER
                    textSize = baseFontSize * 1.2f * scaleFactor
                    typeface = Typeface.create(Typeface.SERIF, Typeface.BOLD)
                    color = signColors[signIndex] ?: textColor
                }
                val signRadius = degreeRadius - baseFontSize * 1.2f * scaleFactor
                val signX = centerX + cos(angle).toFloat() * signRadius
                val signY = centerY + sin(angle).toFloat() * signRadius + signPaint.textSize / 3
                canvas.drawText(zodiacGlyphs[signIndex], signX, signY, signPaint)

                val minutePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                    textAlign = Paint.Align.CENTER
                    textSize = smallBaseFontSize * scaleFactor
                    typeface = Typeface.create(Typeface.SERIF, Typeface.NORMAL)
                    color = minuteColor
                }
                val minuteRadius = signRadius - baseFontSize * 1.0f * scaleFactor
                val minuteX = centerX + cos(angle).toFloat() * minuteRadius
                val minuteY = centerY + sin(angle).toFloat() * minuteRadius + minutePaint.textSize / 3
                val minutes = ((body.degreeInSign - body.degreeInSign.toInt()) * 60).toInt()
                canvas.drawText("${minutes}'", minuteX, minuteY, minutePaint)

                if (body.velocity < 0) {
                    val retroPaint = Paint(minutePaint).apply {
                        textSize = smallBaseFontSize * 0.9f * scaleFactor
                        color = minuteColor
                    }
                    val retroRadius = minuteRadius - baseFontSize * 0.9f * scaleFactor
                    val retroX = centerX + cos(angle).toFloat() * retroRadius
                    val retroY = centerY + sin(angle).toFloat() * retroRadius + retroPaint.textSize / 3
                    canvas.drawText("R", retroX, retroY, retroPaint)
                }
            }
        }
    }

    private fun glyphFor(planet: CelestialObject): String = when (planet) {
        is CelestialObject.Planet -> when (planet.planet) {
            Planet.Sun -> "☉"
            Planet.Moon -> "☽"
            Planet.Mercury -> "☿"
            Planet.Venus -> "♀"
            Planet.Mars -> "♂"
            Planet.Jupiter -> "♃"
            Planet.Saturn -> "♄"
            Planet.Uranus -> "♅"
            Planet.Neptune -> "♆"
            Planet.Pluto -> "♇"
            Planet.SouthNode -> "☋"
        }
        CelestialObject.SouthNode -> "☋"
        is CelestialObject.SpecialCusp -> when (planet.name) {
            "Ascendant" -> "↑"
            "Midheaven" -> "⊗"
            else -> planet.keyName
        }
        is CelestialObject.Cusp -> planet.keyName
    }

    private fun drawLilaLogo(canvas: Canvas, centerX: Float, centerY: Float, radius: Float) {
        val path = Path()
        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            strokeWidth = 2.5f
            color = Color.rgb(100, 100, 200)
        }
        for (i in 0 until 6) {
            val angle = Math.toRadians((i * 60).toDouble())
            val x = centerX + radius * cos(angle).toFloat()
            val y = centerY + radius * sin(angle).toFloat()
            if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
        }
        path.close()
        canvas.drawPath(path, paint)

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
        paint.color = Color.rgb(150, 150, 220)
        canvas.drawPath(innerPath, paint)
    }

    private fun computePositions(
        bodies: List<Coordinate>,
        houseCusps: HouseCusps
    ): Map<CelestialObject, Float> {
        if (bodies.isEmpty()) return emptyMap()
        val ascendantOffset = houseCusps.getCusp(0).longitude
        val rawLongitudes = bodies.map { it.longitude.toFloat() }
        val fixedLongitudes = fixPositionsIfNecessary(rawLongitudes, houseCusps)
        return bodies.mapIndexed { index, coordinate ->
            val normalized = normalizeAngle(fixedLongitudes[index] - ascendantOffset).toFloat()
            coordinate.body to normalized
        }.toMap()
    }

    private fun normalizeAngle(angle: Double): Double {
        var result = angle % 360
        if (result < 0) result += 360
        return result
    }

    private fun normalizeAngleDifference(diff: Float): Float {
        var result = diff % 360
        if (result > 180) result -= 360
        if (result < -180) result += 360
        return result
    }

    private fun calculateHouseDistances(houseCusps: HouseCusps, ascendantOffset: Double): FloatArray {
        val distances = FloatArray(12)
        for (i in 0 until 12) {
            val current = normalizeAngle(houseCusps.getCusp(i).longitude - ascendantOffset).toFloat()
            val next = normalizeAngle(houseCusps.getCusp((i + 1) % 12).longitude - ascendantOffset).toFloat()
            distances[i] = if (next < current) {
                (360f - current) + next
            } else {
                next - current
            }
        }
        return distances
    }

    private fun fixPositionsIfNecessary(planets: List<Float>, houseCusps: HouseCusps): List<Float> {
        data class PlanetDeg(var index: Int, var degree: Float)

        val cuspBuffer = 3f
        val minPlanetDistance = 5.5f

        fun rolloverNormalize(value: Float): Float {
            var d = value % 360f
            if (d < 0f) d += 360f
            return d
        }

        val cuspList = (0 until 12).map { houseCusps.getCusp(it).longitude.toFloat() }
        val planetDegrees = planets.mapIndexed { idx, deg -> PlanetDeg(idx, rolloverNormalize(deg)) }.toMutableList()

        fun circDist(a: Float, b: Float): Float {
            val diff = abs(a - b)
            return min(diff, 360f - diff)
        }

        fun houseIndex(deg: Float): Int {
            for (i in cuspList.indices) {
                val start = cuspList[i]
                val end = cuspList[(i + 1) % cuspList.size]
                if (start < end) {
                    if (deg >= start && deg < end) return i
                } else {
                    if (deg >= start || deg < end) return i
                }
            }
            return 0
        }

        fun span(from: Float, to: Float): Float {
            val result = if (to >= from) to - from else 360f - (from - to)
            return if (result >= 0) result else result + 360f
        }

        fun clampToHouse(deg: Float, houseIdx: Int): Float {
            val start = rolloverNormalize(cuspList[houseIdx] + cuspBuffer)
            val end = rolloverNormalize(cuspList[(houseIdx + 1) % cuspList.size] - cuspBuffer)
            return if (start < end) {
                deg.coerceIn(start, end)
            } else {
                if (deg >= start || deg <= end) {
                    deg
                } else {
                    val distStart = circDist(deg, start)
                    val distEnd = circDist(deg, end)
                    if (distStart < distEnd) start else end
                }
            }
        }

        fun circularSort(planetsIdx: List<Int>, houseIdx: Int): List<Int> {
            val start = cuspList[houseIdx]
            val end = cuspList[(houseIdx + 1) % cuspList.size]
            val houseSpansZero = start > end
            return planetsIdx.sortedBy { pid ->
                val deg = planetDegrees[pid].degree
                if (houseSpansZero) span(start, deg) else deg
            }
        }

        val planetsPerHouse = Array(12) { mutableListOf<Int>() }
        for (pd in planetDegrees) {
            val hIdx = houseIndex(pd.degree)
            planetsPerHouse[hIdx].add(pd.index)
        }

        for (hIdx in 0 until 12) {
            val housePlanets = planetsPerHouse[hIdx]
            val n = housePlanets.size
            if (n <= 1) continue

            val start = rolloverNormalize(cuspList[hIdx] + cuspBuffer)
            val end = rolloverNormalize(cuspList[(hIdx + 1) % 12] - cuspBuffer)
            val usableArc = span(start, end)

            if (n > 4) {
                val spacing = usableArc / (n - 1)
                val sorted = circularSort(housePlanets, hIdx)
                var current = start
                for (pid in sorted) {
                    planetDegrees[pid].degree = current
                    current = rolloverNormalize(current + spacing)
                }
            } else {
                val sorted = circularSort(housePlanets, hIdx)
                val mid = rolloverNormalize(start + usableArc / 2f)
                val offsets = when (n) {
                    2 -> listOf(-minPlanetDistance / 2f, minPlanetDistance / 2f)
                    3 -> listOf(-minPlanetDistance, 0f, minPlanetDistance)
                    4 -> listOf(-1.5f * minPlanetDistance, -0.5f * minPlanetDistance, 0.5f * minPlanetDistance, 1.5f * minPlanetDistance)
                    else -> listOf(0f)
                }
                for ((idx, pid) in sorted.withIndex()) {
                    val offset = offsets.getOrElse(idx) { 0f }
                    val deg = clampToHouse(mid + offset, hIdx)
                    planetDegrees[pid].degree = deg
                }
            }
        }

        for (pd in planetDegrees) {
            val pDeg = pd.degree
            val hIdx = houseIndex(pDeg)
            val start = rolloverNormalize(cuspList[hIdx] + cuspBuffer)
            val end = rolloverNormalize(cuspList[(hIdx + 1) % 12] - cuspBuffer)
            val inBounds = if (start < end) {
                pDeg >= start && pDeg <= end
            } else {
                pDeg >= start || pDeg <= end
            }
            if (!inBounds) {
                val distStart = circDist(pDeg, start)
                val distEnd = circDist(pDeg, end)
                pd.degree = if (distStart < distEnd) start else end
            }
        }

        planetDegrees.sortBy { it.index }
        return planetDegrees.map { it.degree }
    }

    private fun lightenColor(color: Int, factor: Float): Int {
        val clamped = factor.coerceIn(0f, 1f)
        val r = Color.red(color)
        val g = Color.green(color)
        val b = Color.blue(color)
        val newR = (r + (255 - r) * clamped).toInt().coerceIn(0, 255)
        val newG = (g + (255 - g) * clamped).toInt().coerceIn(0, 255)
        val newB = (b + (255 - b) * clamped).toInt().coerceIn(0, 255)
        return Color.rgb(newR, newG, newB)
    }

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
                    translateX += x - lastTouchX
                    translateY += y - lastTouchY
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
        performClick()
        return true
    }

    override fun performClick(): Boolean {
        super.performClick()
        return true
    }

    inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            scaleFactor *= detector.scaleFactor
            scaleFactor = scaleFactor.coerceIn(0.5f, 3.0f)
            invalidate()
            return true
        }
    }
}