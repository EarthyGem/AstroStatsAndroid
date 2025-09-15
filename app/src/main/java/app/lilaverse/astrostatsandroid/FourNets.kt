package app.lilaverse.astrostatsandroid

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Calendar
import kotlin.math.abs

/**
 * Net One implementation – most important activations.
 */
fun ChartCake.netOne(): List<String> {
    val events = mutableListOf<String>()

    // 1. Progressed Sun changing Sign or House
    val progSun = bodyByName("Sun", majorBodies) ?: return emptyList()
    houseCusps.allCusps().forEach { cusp ->
        val aspect = CelestialAspect.fromCoordinateAndCusp(progSun, cusp, 0.5)
        if (aspect?.kind == Kind.Conjunction) {
            events.add("Progressed Sun entering the ${ordinal(cusp.index)} house")
        }
    }
    val sunDeg = progSun.degreeInSign
    if (sunDeg > 29.5) {
        events.add("Progressed Sun at the end of ${progSun.sign}")
    }
    if (sunDeg <= 0.5) {
        events.add("Progressed Sun at the beginning of ${progSun.sign}")
    }

    // 2. Progressed Moon entering any Angular House
    val progMoon = bodyByName("Moon", majorBodies)!!
    val angularIndices = listOf(0, 6) // 1st and 7th
    angularIndices.map { houseCusps.getCusp(it) }.forEach { angle ->
        val aspect = CelestialAspect.fromCoordinateAndCusp(progMoon, angle, 2.5)
        if (aspect?.kind == Kind.Conjunction) {
            events.add("Progressed Moon entering the ${ordinal(angle.index)} house")
        }
    }

    // 3. Progressed planets hard aspects to natal Sun, Moon, angles, nodes
    val progressedPlanets = listOf("Mercury", "Venus", "Mars")
        .mapNotNull { bodyByName(it, majorBodies) }
    val natalTargets = natalBodies.filter {
        it.body.keyName in listOf("Sun", "Moon", "Ascendant", "Midheaven", "South Node")
    }
    progressedPlanets.forEach { planet ->
        natalTargets.forEach { target ->
            val aspect = CelestialAspect.fromCoordinates(planet, target, 2.0)
            if (aspect != null && aspect.kind in listOf(Kind.Conjunction, Kind.Opposition, Kind.Square)) {
                val house = houseCusps.houseForLongitude(planet.longitude)
                val targetName = when (target.body.keyName) {
                    "Ascendant" -> "Asc"
                    "Midheaven" -> "MC"
                    else -> target.body.keyName
                }
                events.add("Progressed ${planet.body.keyName} in House $house ${aspect.kind.description} natal $targetName")
            }
        }
    }

    // 4. Outer planet transits hard aspects to natal luminaries/angles
    val outerTransits = listOf("Saturn", "Uranus", "Neptune", "Pluto")
        .mapNotNull { bodyByName(it, transitBodies) }
    outerTransits.forEach { transit ->
        natalTargets.forEach { target ->
            val aspect = CelestialAspect.fromCoordinates(transit, target, 2.0)
            if (aspect != null && aspect.kind in listOf(Kind.Conjunction, Kind.Opposition, Kind.Square)) {
                val house = houseCusps.houseForLongitude(transit.longitude)
                val targetName = when (target.body.keyName) {
                    "Ascendant" -> "Asc"
                    "Midheaven" -> "MC"
                    else -> target.body.keyName
                }
                events.add("Transit ${transit.body.keyName} in House $house ${aspect.kind.description} natal $targetName")
            }
        }
    }

    // 5. Saturn Return
    val natalSaturn = natalBodies.find { it.body.keyName == "Saturn" }
    val transitSaturn = bodyByName("Saturn", transitBodies)
    if (natalSaturn != null && transitSaturn != null) {
        val aspect = CelestialAspect.fromCoordinates(natalSaturn, transitSaturn, 3.5)
        if (aspect?.kind == Kind.Conjunction) {
            val house = houseCusps.houseForLongitude(natalSaturn.longitude)
            events.add("Saturn return in ${natalSaturn.sign} in House $house")
        }
    }

    // 6. Uranus Opposition and Conjunction
    val natalUranus = natalBodies.find { it.body.keyName == "Uranus" }
    val transitUranus = bodyByName("Uranus", transitBodies)
    if (natalUranus != null && transitUranus != null) {
        val aspect = CelestialAspect.fromCoordinates(natalUranus, transitUranus, 3.0)
        if (aspect?.kind == Kind.Opposition) {
            val transitHouse = houseCusps.houseForLongitude(transitUranus.longitude)
            val natalHouse = houseCusps.houseForLongitude(natalUranus.longitude)
            events.add("Transit Uranus in ${transitUranus.sign} in House $transitHouse opposing natal ${natalUranus.sign} in House $natalHouse")
        }
        if (aspect?.kind == Kind.Conjunction) {
            val natalHouse = houseCusps.houseForLongitude(natalUranus.longitude)
            events.add("Great Convergence in ${natalUranus.sign} in House $natalHouse")
        }
    }

    // 7. Progressed Sun hard aspects to any natal planet or angle
    natalBodies.forEach { natal ->
        val aspect = CelestialAspect.fromCoordinates(progSun, natal, 2.0)
        if (aspect != null && aspect.kind in listOf(Kind.Conjunction, Kind.Opposition, Kind.Square)) {
            val house = houseCusps.houseForLongitude(progSun.longitude)
            val targetName = when (natal.body.keyName) {
                "Ascendant" -> "Asc"
                "Midheaven" -> "MC"
                else -> natal.body.keyName
            }
            events.add("Progressed Sun in House $house ${aspect.kind.description} natal $targetName")
        }
    }

    // 8. Progressed Moon in Balsamic or New phase
    val moonHouse = houseCusps.houseForLongitude(progMoon.longitude)
    when (calculateLunarPhase(majorBodies)) {
        "Balsamic", "Waning Crescent" ->
            events.add("Progressed Moon in Balsamic Phase in ${progMoon.sign} in House $moonHouse")
        "New Moon" ->
            events.add("Progressed Moon in New Moon Phase in ${progMoon.sign} in House $moonHouse")
    }

    // 9. Progressed Moon Out of Bounds
    if (abs(progMoon.declination) >= 23.44) {
        events.add("Progressed Moon Out of Bounds in ${progMoon.sign} in House $moonHouse")
    }

    // 10. Solar Arc nodal axis hard aspects
    val solarArcNode = icing.allBodies.find { it.body.keyName == "South Node" }
    if (solarArcNode != null) {
        natalBodies.forEach { natal ->
            val aspect = CelestialAspect.fromCoordinates(solarArcNode, natal, 1.0)
            if (aspect != null && aspect.kind in listOf(Kind.Conjunction, Kind.Opposition, Kind.Square)) {
                val saHouse = houseCusps.houseForLongitude(solarArcNode.longitude)
                val targetName = when (natal.body.keyName) {
                    "Ascendant" -> "Asc"
                    "Midheaven" -> "MC"
                    else -> natal.body.keyName
                }
                events.add("Solar Arc South Node in House $saHouse ${aspect.kind.description} natal $targetName")
            }
        }
    }

    return events
}

/**
 * Net Two implementation – supporting activations.
 */
fun ChartCake.netTwo(): List<String> {
    val events = mutableListOf<String>()

    val progSun = bodyByName("Sun", majorBodies) ?: return emptyList()
    natalBodies.forEach { natal ->
        val aspect = CelestialAspect.fromCoordinates(progSun, natal, 2.0)
        if (aspect != null && (aspect.kind == Kind.Trine || aspect.kind == Kind.Sextile)) {
            events.add("Progressed Sun ${aspect.kind.description} natal ${natal.body.keyName}")
        }
    }

    val progressedPlanets = listOf("Mercury", "Venus", "Mars").mapNotNull { bodyByName(it, majorBodies) }
    progressedPlanets.forEach { planet ->
        natalBodies.forEach { natal ->
            val aspect = CelestialAspect.fromCoordinates(planet, natal, 2.0)
            if (aspect != null && aspect.kind in listOf(Kind.Conjunction, Kind.Opposition, Kind.Square)) {
                val house = houseCusps.houseForLongitude(planet.longitude)
                val natalName = when (natal.body.keyName) {
                    "Ascendant" -> "Asc"
                    "Midheaven" -> "MC"
                    else -> natal.body.keyName
                }
                events.add("Progressed ${planet.body.keyName} in House $house ${aspect.kind.description} natal $natalName")
            }
        }
    }

    // Progressed planets changing sign or house
    progressedPlanets.forEach { planet ->
        houseCusps.allCusps().forEach { cusp ->
            val aspect = CelestialAspect.fromCoordinateAndCusp(planet, cusp, 0.5)
            if (aspect?.kind == Kind.Conjunction) {
                events.add("Progressed ${planet.body.keyName} entering House ${cusp.index}")
            }
        }
        val degree = planet.degreeInSign
        if (degree > 29.0) events.add("Progressed ${planet.body.keyName} at the end of ${planet.sign}")
        if (degree <= 1.0) events.add("Progressed ${planet.body.keyName} at the beginning of ${planet.sign}")
    }

    // Outer planet transits hard aspects to natal inner planets
    val outerTransits = listOf("Saturn", "Uranus", "Neptune", "Pluto").mapNotNull { bodyByName(it, transitBodies) }
    val innerNatals = listOf("Mercury", "Venus", "Mars", "Jupiter", "Saturn", "Uranus", "Neptune", "Pluto")
        .mapNotNull { name -> natalBodies.find { it.body.keyName == name } }
    outerTransits.forEach { transit ->
        innerNatals.forEach { natal ->
            val aspect = CelestialAspect.fromCoordinates(transit, natal, 2.0)
            if (aspect != null && aspect.kind in listOf(Kind.Conjunction, Kind.Opposition, Kind.Square)) {
                val tHouse = houseCusps.houseForLongitude(transit.longitude)
                val nHouse = houseCusps.houseForLongitude(natal.longitude)
                events.add("Transit ${transit.body.keyName} in House $tHouse ${aspect.kind.description} natal ${natal.body.keyName} in House $nHouse")
            }
        }
    }

    // Jupiter transits to luminaries and angles
    val jupiter = bodyByName("Jupiter", transitBodies)
    if (jupiter != null) {
        natalBodies.filter { it.body.keyName in listOf("Sun", "Moon", "Ascendant", "Midheaven", "South Node") }
            .forEach { natal ->
                val aspect = CelestialAspect.fromCoordinates(jupiter, natal, 2.5)
                if (aspect != null && aspect.kind in listOf(Kind.Conjunction, Kind.Opposition, Kind.Square)) {
                    val jHouse = houseCusps.houseForLongitude(jupiter.longitude)
                    val nName = when (natal.body.keyName) {
                        "Ascendant" -> "Asc"
                        "Midheaven" -> "MC"
                        else -> natal.body.keyName
                    }
                    events.add("Transit Jupiter in House $jHouse ${aspect.kind.description} natal $nName")
                }
            }
    }

    // Outer planets entering non-angular houses
    val nonAngular = listOf(1,2,4,5,7,8,10,11)
    val outerPlusJupiter = listOf("Jupiter","Saturn","Uranus","Neptune","Pluto").mapNotNull { bodyByName(it, transitBodies) }
    outerPlusJupiter.forEach { planet ->
        nonAngular.map { houseCusps.getCusp(it) }.forEach { cusp ->
            val aspect = CelestialAspect.fromCoordinateAndCusp(planet, cusp, 1.0)
            if (aspect?.kind == Kind.Conjunction) {
                events.add("Transit ${planet.body.keyName} entering House ${cusp.index}")
            }
        }
    }

    // Progressed Moon entering non-angular houses and sign changes
    val progMoon = bodyByName("Moon", majorBodies)!!
    nonAngular.map { houseCusps.getCusp(it) }.forEach { cusp ->
        val aspect = CelestialAspect.fromCoordinateAndCusp(progMoon, cusp, 4.0)
        if (aspect?.kind == Kind.Conjunction) {
            events.add("Progressed Moon entering House ${cusp.index}")
        }
    }
    val moonDeg = progMoon.degreeInSign
    if (moonDeg > 28.0) events.add("Progressed Moon at the end of ${progMoon.sign}")
    if (moonDeg <= 2.0) events.add("Progressed Moon at the beginning of ${progMoon.sign}")

    // Solar Arc hard aspects
    icing.allBodies.forEach { saPlanet ->
        natalBodies.forEach { natal ->
            val aspect = CelestialAspect.fromCoordinates(saPlanet, natal, 1.0)
            if (aspect != null && aspect.kind in listOf(Kind.Conjunction, Kind.Opposition, Kind.Square)) {
                val house = houseCusps.houseForLongitude(saPlanet.longitude)
                val nName = when (natal.body.keyName) {
                    "Ascendant" -> "Asc"
                    "Midheaven" -> "MC"
                    else -> natal.body.keyName
                }
                events.add("Solar Arc ${saPlanet.body.keyName} in House $house ${aspect.kind.description} natal $nName")
            }
        }
    }

    // Progressed Full Moon
    if (calculateLunarPhase(majorBodies) == "Full Moon") {
        val moonHouse = houseCusps.houseForLongitude(progMoon.longitude)
        events.add("Progressed Moon in Full Moon Phase in ${progMoon.sign} in House $moonHouse")
    }

    return events
}

/**
 * Net Three implementation – moderately important events.
 */
fun ChartCake.netThree(): List<String> {
    val events = mutableListOf<String>()

    val outerTransits = listOf("Saturn", "Uranus", "Neptune", "Pluto")
        .mapNotNull { bodyByName(it, transitBodies) }
    outerTransits.forEach { transit ->
        natalBodies.forEach { natal ->
            val aspect = CelestialAspect.fromCoordinates(transit, natal, 2.0)
            if (aspect != null && aspect.kind in listOf(Kind.Trine, Kind.Sextile)) {
                val house = houseCusps.houseForLongitude(transit.longitude)
                val nName = when (natal.body.keyName) {
                    "Ascendant" -> "Asc"
                    "Midheaven" -> "MC"
                    else -> natal.body.keyName
                }
                events.add("Transit ${transit.body.keyName} in House $house ${aspect.kind.description} natal $nName")
            }
        }
    }

    val progressedPlanets = listOf("Mercury", "Venus", "Mars").mapNotNull { bodyByName(it, majorBodies) }
    progressedPlanets.forEach { planet ->
        natalBodies.forEach { natal ->
            val aspect = CelestialAspect.fromCoordinates(planet, natal, 2.0)
            if (aspect != null && aspect.kind in listOf(Kind.Trine, Kind.Sextile)) {
                val house = houseCusps.houseForLongitude(planet.longitude)
                val nName = when (natal.body.keyName) {
                    "Ascendant" -> "Asc"
                    "Midheaven" -> "MC"
                    else -> natal.body.keyName
                }
                events.add("Progressed ${planet.body.keyName} in House $house ${aspect.kind.description} natal $nName")
            }
        }
    }

    val jupiter = bodyByName("Jupiter", transitBodies)
    if (jupiter != null) {
        natalBodies.forEach { natal ->
            val aspect = CelestialAspect.fromCoordinates(jupiter, natal, 3.0)
            if (aspect != null && aspect.kind in listOf(Kind.Trine, Kind.Sextile)) {
                val house = houseCusps.houseForLongitude(jupiter.longitude)
                val nName = when (natal.body.keyName) {
                    "Ascendant" -> "Asc"
                    "Midheaven" -> "MC"
                    else -> natal.body.keyName
                }
                events.add("Transit Jupiter in House $house ${aspect.kind.description} natal $nName")
            }
        }
    }

    val progMoon = bodyByName("Moon", majorBodies)!!
    natalBodies.forEach { natal ->
        val aspect = CelestialAspect.fromCoordinates(progMoon, natal, 4.0)
        if (aspect?.kind == Kind.Conjunction) {
            val house = houseCusps.houseForLongitude(progMoon.longitude)
            val nName = when (natal.body.keyName) {
                "Ascendant" -> "Asc"
                "Midheaven" -> "MC"
                else -> natal.body.keyName
            }
            events.add("Progressed Moon in House $house conjunct natal $nName")
        }
    }

    icing.allBodies.forEach { saPlanet ->
        natalBodies.forEach { natal ->
            val aspect = CelestialAspect.fromCoordinates(saPlanet, natal, 1.0)
            if (aspect != null && aspect.kind in listOf(Kind.Trine, Kind.Sextile)) {
                val house = houseCusps.houseForLongitude(saPlanet.longitude)
                val nName = when (natal.body.keyName) {
                    "Ascendant" -> "Asc"
                    "Midheaven" -> "MC"
                    else -> natal.body.keyName
                }
                events.add("Solar Arc ${saPlanet.body.keyName} in House $house ${aspect.kind.description} natal $nName")
            }
        }
    }

    val southNode = bodyByName("South Node", transitBodies)
    if (southNode != null) {
        natalBodies.forEach { natal ->
            val aspect = CelestialAspect.fromCoordinates(southNode, natal, 2.0)
            if (aspect != null && aspect.kind in listOf(Kind.Conjunction, Kind.Opposition)) {
                val house = houseCusps.houseForLongitude(southNode.longitude)
                val nName = when (natal.body.keyName) {
                    "Ascendant" -> "Asc"
                    "Midheaven" -> "MC"
                    else -> natal.body.keyName
                }
                events.add("Transit South Node in House $house ${aspect.kind.description} natal $nName")
            }
        }
    }

    return events
}

/**
 * Net Four implementation – daily triggers and minor activations.
 */
fun ChartCake.netFour(): List<String> {
    val events = mutableListOf<String>()

    val innerTransits = listOf("Sun", "Moon", "Mercury", "Venus", "Mars")
        .mapNotNull { bodyByName(it, transitBodies) }
    innerTransits.forEach { transit ->
        val orb = if (transit.body.keyName in listOf("Mars", "Moon")) 4.0 else 2.5
        natalBodies.forEach { natal ->
            val aspect = CelestialAspect.fromCoordinates(transit, natal, orb)
            if (aspect != null && aspect.kind in listOf(
                    Kind.Conjunction, Kind.Opposition, Kind.Square, Kind.Trine, Kind.Sextile
                )) {
                val house = houseCusps.houseForLongitude(transit.longitude)
                val nName = when (natal.body.keyName) {
                    "Ascendant" -> "Asc"
                    "Midheaven" -> "MC"
                    else -> natal.body.keyName
                }
                events.add("Transit ${transit.body.keyName} in House $house ${aspect.kind.description} natal $nName")
            }
        }
    }

    val progMoon = bodyByName("Moon", majorBodies)!!
    natalBodies.forEach { natal ->
        val aspect = CelestialAspect.fromCoordinates(progMoon, natal, 4.0)
        if (aspect != null && aspect.kind in listOf(Kind.Opposition, Kind.Square, Kind.Trine, Kind.Sextile)) {
            val house = houseCusps.houseForLongitude(progMoon.longitude)
            val nName = when (natal.body.keyName) {
                "Ascendant" -> "Asc"
                "Midheaven" -> "MC"
                else -> natal.body.keyName
            }
            events.add("Progressed Moon in House $house ${aspect.kind.description} natal $nName")
        }
    }

    return events
}

// -------- FourNet profile builders --------

data class FourNetProfile(
    val natalProfile: UserChartProfile,
    val transitDate: Date,
    val isPast: Boolean,
    val isFuture: Boolean,
    val yearsApart: Int,
    val monthsApart: Int,
    val daysApart: Int,
    val netOne: List<String>,
    val netTwo: List<String>,
    val netThree: List<String>,
    val netFour: List<String>
)

fun ChartCake.buildFourNetProfile(): FourNetProfile? {
    val calendar = Calendar.getInstance()
    val now = Date()
    val isPast = transitDate < now
    val isFuture = transitDate > now
    calendar.timeInMillis = transitDate.time - birthDate.time

    val natalProfile = buildUserChartProfile()

    return FourNetProfile(
        natalProfile = natalProfile,
        transitDate = transitDate,
        isPast = isPast,
        isFuture = isFuture,
        yearsApart = calendar.get(Calendar.YEAR),
        monthsApart = calendar.get(Calendar.MONTH),
        daysApart = calendar.get(Calendar.DAY_OF_MONTH),
        netOne = netOne(),
        netTwo = netTwo(),
        netThree = netThree(),
        netFour = netFour()
    )
}

fun ChartCake.buildFourNetProfileString(): String {
    val calendar = Calendar.getInstance()
    val now = Date()
    val isPast = transitDate < now
    val isFuture = transitDate > now
    calendar.timeInMillis = transitDate.time - now.time

    val yearsApart = calendar.get(Calendar.YEAR)
    val monthsApart = calendar.get(Calendar.MONTH)
    val daysApart = calendar.get(Calendar.DAY_OF_MONTH)

    val age = calculateAge(birthDate, transitDate)
    val sdf = SimpleDateFormat("MMM dd, yyyy")

    return """
        ⏳ TIME CONTEXT:
        - ${if (isPast) "Past" else if (isFuture) "Future" else "Present"} date
        - ${if (yearsApart > 0) "${yearsApart} years" else if (monthsApart > 0) "${monthsApart} months" else "${daysApart} days"} ${if (isPast) "ago" else "from now"}
        
        TRANSIT DATA for ${sdf.format(transitDate)}:
        - Most important activations: ${netOne()}
        - Supporting activations: ${netTwo()}
        - Moderately important: ${netThree()}
        - Daily triggers: ${netFour()}
        
        This person is $age years old. Please make recommendations age-appropriate.
        
        **Transits and Progressions** reveal how life unfolds as an evolutionary journey of integration.
        Your role is to help appreciate why **meaningful events have occurred, are occurring, and will occur**—not as random fate, but as opportunities for growth.
        
        💡 **Life happens for us, not to us.** Every planetary activation represents a **moment in our evolutionary path where we are ready to integrate the two planets in aspect in the 1 or more areas ruled by the natal planet being aspected**.
    """.trimIndent()
}

// -------- Helpers --------

private fun calculateLunarPhase(bodies: List<Coordinate>): String {
    val sun = bodies.find { it.body.keyName == "Sun" } ?: return "Unknown"
    val moon = bodies.find { it.body.keyName == "Moon" } ?: return "Unknown"
    val angle = abs(moon.longitude - sun.longitude)
    val norm = if (angle > 180) 360 - angle else angle
    return when {
        norm <= 15 -> "New Moon"
        norm <= 45 -> "Waxing Crescent"
        norm <= 75 -> "First Quarter"
        norm <= 105 -> "Waxing Gibbous"
        norm <= 165 -> "Full Moon"
        norm <= 195 -> "Waning Gibbous"
        norm <= 225 -> "Last Quarter"
        norm <= 255 -> "Waning Crescent"
        else -> "Balsamic"
    }
}

private fun calculateAge(birthDate: Date, currentDate: Date): Int {
    val birthCal = Calendar.getInstance().apply { time = birthDate }
    val currentCal = Calendar.getInstance().apply { time = currentDate }
    var age = currentCal.get(Calendar.YEAR) - birthCal.get(Calendar.YEAR)
    if (currentCal.get(Calendar.DAY_OF_YEAR) < birthCal.get(Calendar.DAY_OF_YEAR)) age--
    return age
}