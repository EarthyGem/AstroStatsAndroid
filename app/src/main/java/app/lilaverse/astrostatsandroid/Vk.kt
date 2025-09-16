package app.lilaverse.astrostatsandroid

import java.util.Locale
import kotlin.math.abs

/**
 * Validation kernel that mirrors the behaviour of the iOS implementation. It
 * verifies placements, aspects and strength metrics mentioned by the language
 * model so that responses remain consistent with chart data available to the
 * Android client.
 */
object VK {

    enum class Kind { NATAL, SYNASTRY, TRANSITS, PROGRESSIONS }

    data class Result(
        val corrected: String,
        val notes: List<String>,
        val warnings: List<String>,
        val criticalFix: Boolean
    )

    data class Context(
        val kind: Kind,
        val primary: ChartCake,
        val partner: ChartCake? = null,
        val allowed: AllowedAspects,
        val contextBanner: String
    )

    data class AllowedAspects(
        val natal: Set<String> = emptySet(),
        val synastry: Set<String> = emptySet(),
        val transits: Set<String> = emptySet(),
        val progressions: Set<String> = emptySet()
    ) {
        data class Precomputed(
            val natal: Set<String>,
            val synastry: Set<String>,
            val transits: Set<String>,
            val progressions: Set<String>
        )

        companion object {
            fun from(precomputed: Precomputed): AllowedAspects = AllowedAspects(
                natal = precomputed.natal,
                synastry = precomputed.synastry,
                transits = precomputed.transits,
                progressions = precomputed.progressions
            )

            fun fromNatalCelestial(
                aspects: List<CelestialAspect>,
                ownerPrefix: String = "user"
            ): Set<String> {
                val out = mutableSetOf<String>()
                for (aspect in aspects) {
                    val p1 = aspect.body1.body.keyName.lowercase(Locale.US)
                    val p2 = aspect.body2.body.keyName.lowercase(Locale.US)
                    val asp = aspect.kind.description.lowercase(Locale.US)
                    out.add("${ownerPrefix}:${p1}_${asp}_${ownerPrefix}:${p2}")
                    out.add("${ownerPrefix}:${p2}_${asp}_${ownerPrefix}:${p1}")
                }
                return out
            }

            fun fromSynastryCelestial(aspects: List<CelestialAspect>): Set<String> {
                val out = mutableSetOf<String>()
                for (aspect in aspects) {
                    val p1 = aspect.body1.body.keyName.lowercase(Locale.US)
                    val p2 = aspect.body2.body.keyName.lowercase(Locale.US)
                    val asp = aspect.kind.description.lowercase(Locale.US)
                    out.add("user:${p1}_${asp}_partner:${p2}")
                    out.add("partner:${p2}_${asp}_user:${p1}")
                }
                return out
            }

            fun fromTimingCelestial(aspects: List<CelestialAspect>): Set<String> {
                val out = mutableSetOf<String>()
                for (aspect in aspects) {
                    val p1 = aspect.body1.body.keyName.lowercase(Locale.US)
                    val p2 = aspect.body2.body.keyName.lowercase(Locale.US)
                    val asp = aspect.kind.description.lowercase(Locale.US)
                    out.add("user:${p1}_${asp}_user:${p2}")
                    out.add("user:${p2}_${asp}_user:${p1}")
                }
                return out
            }
        }
    }

    class Bag<T> {
        private val set = mutableSetOf<T>()
        fun once(value: T, block: () -> Unit) {
            if (set.add(value)) block()
        }
    }

    fun validate(draft: String, context: Context): Result {
        var text = draft
        val notes = mutableListOf<String>()
        val warnings = mutableListOf<String>()
        val bag = Bag<String>()
        var criticalFix = false

        if (!hasContextBanner(text, context.contextBanner)) {
            text = "CONTEXT: ${context.contextBanner}\n\n" + text
            bag.once("banner") {
                notes.add("Prepended context banner: ${context.contextBanner}")
            }
        }

        if (containsIdenticalCharts(text)) {
            text = replaceIdenticalCharts(text)
            criticalFix = true
            bag.once("identical") {
                notes.add("Replaced identical chart claim with softer phrasing")
            }
        }

        val userFacts = NatalFacts.from(context.primary)
        val partnerFacts = context.partner?.let { NatalFacts.from(it) }
        val metrics = AstroMetrics.from(context.primary)

        text = correctPlacements(text, userFacts, owner = Owner.USER, bag, notes)
        if (partnerFacts != null) {
            text = correctPlacements(text, partnerFacts, owner = Owner.PARTNER, bag, notes)
        } else {
            if (Regex("(?i)partner\\b").containsMatchIn(text)) {
                bag.once("partner-missing") {
                    warnings.add("Mentioned partner placements but partner chart not provided; left unchanged.")
                }
            }
        }

        text = softenInvalidAspects(text, context, userFacts, partnerFacts, bag, notes, warnings)
        text = validateMetricClaims(text, metrics, bag, notes)

        if (context.kind == Kind.SYNASTRY) {
            text = PronounAuditor.audit(text)
        }

        if (notes.isNotEmpty()) {
            text = Humility.inject(text)
        }

        return Result(text, notes, warnings, criticalFix)
    }

    enum class Owner { USER, PARTNER }

    private data class NatalFacts(
        val signs: Map<String, String>,
        val houses: Map<String, Int>
    ) {
        companion object {
            private val tracked = setOf(
                "sun", "moon", "mercury", "venus", "mars",
                "jupiter", "saturn", "uranus", "neptune", "pluto"
            )

            fun from(chart: ChartCake): NatalFacts {
                val signMap = mutableMapOf<String, String>()
                val houseMap = mutableMapOf<String, Int>()
                val cusps = chart.houseCusps
                for (coord in chart.natalBodies) {
                    val key = coord.body.keyName.lowercase(Locale.US)
                    if (key !in tracked) continue
                    signMap[key] = coord.sign.name.lowercase(Locale.US)
                    houseMap[key] = cusps.houseForLongitude(coord.longitude)
                }
                return NatalFacts(signMap, houseMap)
            }
        }
    }

    private data class AstroMetrics(
        val planetTotals: Map<String, Double>,
        val planetAspectPower: Map<String, Double>,
        val housePower: Map<Int, Double>,
        val signPower: Map<String, Double>,
        val strongestPlanet: String?,
        val strongestHouse: Int?,
        val strongestSign: String?
    ) {
        companion object {
            private val planets = setOf(
                "sun", "moon", "mercury", "venus", "mars",
                "jupiter", "saturn", "uranus", "neptune", "pluto"
            )

            fun from(chart: ChartCake): AstroMetrics {
                val calculator = PlanetStrengthCalculator(
                    orbDictionary,
                    houseProvider = { chart.houseCusps.houseForLongitude(it.longitude) },
                    luminaryChecker = { it.body.keyName in listOf("Sun", "Moon", "Mercury") },
                    houseCuspsProvider = { lon ->
                        val houseNum = chart.houseCusps.houseForLongitude(lon)
                        val cuspLon = chart.houseCusps.getCusp(houseNum - 1).longitude
                        HouseCusp(houseNum, cuspLon)
                    },
                    houseCuspValues = chart.houseCusps.allCusps().map { it.longitude }
                )

                val bodies = chart.natalBodies
                val totalScores = calculator.getTotalPowerScoresForPlanetsCo(bodies)
                val aspectScores = calculator.allCelestialAspectScoresByAspect(bodies)
                val aspectPower = mutableMapOf<String, Double>()
                for ((aspect, score) in aspectScores) {
                    val p1 = aspect.body1.body.keyName.lowercase(Locale.US)
                    val p2 = aspect.body2.body.keyName.lowercase(Locale.US)
                    if (p1 in planets) aspectPower[p1] = (aspectPower[p1] ?: 0.0) + score
                    if (p2 in planets) aspectPower[p2] = (aspectPower[p2] ?: 0.0) + score
                }

                val planetTotals = totalScores
                    .filter { it.key.body.keyName.lowercase(Locale.US) in planets }
                    .mapKeys { it.key.body.keyName.lowercase(Locale.US) }

                val signScores = SignStrengthCalculator(chart, totalScores)
                    .calculateTotalSignScores()
                    .mapKeys { it.key.name.lowercase(Locale.US) }

                val houseScores = HouseStrengthCalculator(chart, totalScores)
                    .calculateHouseStrengths()

                val strongestPlanet = planetTotals.maxByOrNull { it.value }?.key
                val strongestHouse = houseScores.maxByOrNull { it.value }?.key
                val strongestSign = signScores.maxByOrNull { it.value }?.key

                return AstroMetrics(
                    planetTotals = planetTotals,
                    planetAspectPower = aspectPower,
                    housePower = houseScores,
                    signPower = signScores,
                    strongestPlanet = strongestPlanet,
                    strongestHouse = strongestHouse,
                    strongestSign = strongestSign
                )
            }
        }
    }

    private fun hasContextBanner(text: String, banner: String): Boolean =
        text.lowercase(Locale.US).contains("context: ${banner.lowercase(Locale.US)}")

    private fun containsIdenticalCharts(text: String): Boolean {
        val lower = text.lowercase(Locale.US)
        return listOf(
            "identical chart",
            "exact same chart",
            "same planetary placements",
            "astrological twin"
        ).any { lower.contains(it) }
    }

    private fun replaceIdenticalCharts(text: String): String {
        var updated = text
        val replacements = listOf(
            "identical chart" to "overlapping themes in your charts, though each chart is distinct",
            "exact same chart" to "overlapping themes in your charts, though each chart is distinct",
            "same planetary placements" to "similar planetary themes while each chart remains unique",
            "astrological twin" to "remarkably similar chart themes, yet still individually unique"
        )
        for ((needle, replacement) in replacements) {
            updated = updated.replace(needle, replacement, ignoreCase = true)
        }
        return updated
    }

    private fun correctPlacements(
        text: String,
        facts: NatalFacts,
        owner: Owner,
        bag: Bag<String>,
        notes: MutableList<String>
    ): String {
        var updated = text
        val planetRegex = "(sun|moon|mercury|venus|mars|jupiter|saturn|uranus|neptune|pluto)"
        val signRegex = Regex(
            "(?i)((?:my|your|his|her|their|partner(?:'s)?)?\\s*)$planetRegex\\s+(?:is\\s+in|in|into|through)\\s+(aries|taurus|gemini|cancer|leo|virgo|libra|scorpio|sagittarius|capricorn|aquarius|pisces)"
        )
        updated = signRegex.replace(updated) { match ->
            val planet = match.groupValues[2].lowercase(Locale.US)
            val claimed = match.groupValues[3].lowercase(Locale.US)
            val actual = facts.signs[planet]
            if (actual != null && actual != claimed) {
                bag.once("sign-$owner-$planet") {
                    notes.add("Corrected ${ownerWord(owner)} ${planet.cap()} sign → ${actual.cap()}")
                }
                match.value.replace(match.groupValues[3], actual.cap(), ignoreCase = true)
            } else match.value
        }

        val houseRegex = Regex(
            "(?i)$planetRegex\\s+(?:is\\s+in|in|into|through)\\s+(?:the\\s+)?((?:first|second|third|fourth|fifth|sixth|seventh|eighth|ninth|tenth|eleventh|twelfth)|\\d{1,2}(?:st|nd|rd|th)?)\\s+house"
        )
        updated = houseRegex.replace(updated) { match ->
            val planet = match.groupValues[1].lowercase(Locale.US)
            val claimedText = match.groupValues[2]
            val claimed = normalizeHouse(claimedText)
            val actual = facts.houses[planet]
            if (claimed != null && actual != null && claimed != actual) {
                bag.once("house-$owner-$planet") {
                    notes.add("Corrected ${ownerWord(owner)} ${planet.cap()} house → ${ordinal(actual)}")
                }
                match.value.replace(claimedText, "${ordinal(actual)}", ignoreCase = true)
            } else match.value
        }

        return updated
    }

    private fun softenInvalidAspects(
        text: String,
        context: Context,
        userFacts: NatalFacts,
        partnerFacts: NatalFacts?,
        bag: Bag<String>,
        notes: MutableList<String>,
        warnings: MutableList<String>
    ): String {
        var updated = text
        val regex = Regex(
            "(?i)((?:my|your|his|her|their|partner(?:'s)?)?\\s*)(sun|moon|mercury|venus|mars|jupiter|saturn|uranus|neptune|pluto)\\s+" +
                    "(conjunct|conjunction|opposite|opposition|square|trine|sextile|quincunx|inconjunct)\\s+" +
                    "((?:my|your|his|her|their|partner(?:'s)?)?\\s*)?(sun|moon|mercury|venus|mars|jupiter|saturn|uranus|neptune|pluto)"
        )
        regex.findAll(updated).forEach { match ->
            val ownerA = ownerFromWord(match.groupValues[1])
            val planetA = match.groupValues[2].lowercase(Locale.US)
            val aspect = normalizeAspect(match.groupValues[3])
            val ownerB = ownerFromWord(match.groupValues[4])
            val planetB = match.groupValues[5].lowercase(Locale.US)
            val key = aspectKey(ownerA, planetA, aspect, ownerB, planetB)
            val permitted = aspectExists(key, context)
            if (!permitted) {
                val sentence = sentenceContaining(match.range, updated)
                if (sentence != null) {
                    val softened = soften(sentence)
                    updated = updated.replace(sentence, softened)
                    bag.once("soft-$key") { notes.add("Softened unverified aspect: $key") }
                }
                return@forEach
            }

            val sameOwner = (ownerA == Owner.USER && ownerB == Owner.USER) || (ownerA == Owner.PARTNER && ownerB == Owner.PARTNER)
            if (sameOwner) {
                val facts = if (ownerA == Owner.USER) userFacts else partnerFacts ?: userFacts
                val signA = facts.signs[planetA]
                val signB = facts.signs[planetB]
                if (signA != null && signB != null && !signsCanForm(signA, signB, aspect)) {
                    val sentence = sentenceContaining(match.range, updated)
                    if (sentence != null) {
                        val softened = soften(sentence)
                        updated = updated.replace(sentence, softened)
                        bag.once("geom-$key") {
                            notes.add("Softened low-plausibility natal aspect by sign geometry: $key")
                        }
                    }
                }
            }
        }
        return updated
    }

    private fun validateMetricClaims(
        text: String,
        metrics: AstroMetrics,
        bag: Bag<String>,
        notes: MutableList<String>
    ): String {
        var updated = text

        metrics.strongestPlanet?.let { strongest ->
            val regex = Regex("(?i)(strongest\\s+planet\\s+is\\s+)(sun|moon|mercury|venus|mars|jupiter|saturn|uranus|neptune|pluto)")
            updated = regex.replace(updated) { match ->
                val claimed = match.groupValues[2].lowercase(Locale.US)
                if (claimed != strongest) {
                    bag.once("strongest-planet") {
                        notes.add("Corrected strongest planet: ${claimed.cap()} → ${strongest.cap()}")
                    }
                    match.groupValues[1] + strongest.cap()
                } else match.value
            }
        }

        metrics.strongestSign?.let { strongest ->
            val regex = Regex("(?i)(strongest\\s+sign\\s+is\\s+)(aries|taurus|gemini|cancer|leo|virgo|libra|scorpio|sagittarius|capricorn|aquarius|pisces)")
            updated = regex.replace(updated) { match ->
                val claimed = match.groupValues[2].lowercase(Locale.US)
                if (claimed != strongest) {
                    bag.once("strongest-sign") {
                        notes.add("Corrected strongest sign: ${claimed.cap()} → ${strongest.cap()}")
                    }
                    match.groupValues[1] + strongest.cap()
                } else match.value
            }
        }

        metrics.strongestHouse?.let { strongest ->
            val regex = Regex("(?i)(strongest\\s+house\\s+is\\s+)(\\d{1,2})")
            updated = regex.replace(updated) { match ->
                val claimed = match.groupValues[2].toIntOrNull()
                if (claimed != null && claimed != strongest) {
                    bag.once("strongest-house") {
                        notes.add("Corrected strongest house: ${ordinal(claimed)} → ${ordinal(strongest)}")
                    }
                    match.groupValues[1] + strongest.toString()
                } else match.value
            }
        }

        val planetTotalRegex = Regex(
            "(?i)(sun|moon|mercury|venus|mars|jupiter|saturn|uranus|neptune|pluto)([^\\n]{0,40}?)(?:total|overall)?\\s*(?:power|astrodynes)[:\\s]+(\\d+\\.?\\d*)"
        )
        updated = planetTotalRegex.replace(updated) { match ->
            val planet = match.groupValues[1].lowercase(Locale.US)
            val claimed = match.groupValues[3].toDoubleOrNull()
            val actual = metrics.planetTotals[planet]
            if (claimed != null && actual != null && abs(claimed - actual) > 0.5) {
                bag.once("planet-total-$planet") {
                    notes.add("Corrected ${planet.cap()} total power: ${claimed.format1()} → ${actual.format1()}")
                }
                "${planet.cap()}${match.groupValues[2]}power: ${actual.format1()}"
            } else match.value
        }

        val planetAspectRegex = Regex(
            "(?i)(sun|moon|mercury|venus|mars|jupiter|saturn|uranus|neptune|pluto)([^\\n]{0,40}?)(?:aspect\\s+(?:power|score))[:\\s]+(\\d+\\.?\\d*)"
        )
        updated = planetAspectRegex.replace(updated) { match ->
            val planet = match.groupValues[1].lowercase(Locale.US)
            val claimed = match.groupValues[3].toDoubleOrNull()
            val actual = metrics.planetAspectPower[planet]
            if (claimed != null && actual != null && abs(claimed - actual) > 0.5) {
                bag.once("planet-aspect-$planet") {
                    notes.add("Corrected ${planet.cap()} aspect power: ${claimed.format1()} → ${actual.format1()}")
                }
                "${planet.cap()}${match.groupValues[2]}aspect power: ${actual.format1()}"
            } else match.value
        }

        val housePowerRegex = Regex("(?i)(house\\s+)(\\d{1,2})([^\\n]{0,20}?)(?:astrodynes|power|score)[:\\s]+(\\d+\\.?\\d*)")
        updated = housePowerRegex.replace(updated) { match ->
            val house = match.groupValues[2].toIntOrNull()
            val claimed = match.groupValues[4].toDoubleOrNull()
            if (house != null && claimed != null) {
                val actual = metrics.housePower[house]
                if (actual != null && abs(claimed - actual) > 0.5) {
                    bag.once("house-power-$house") {
                        notes.add("Corrected House $house power: ${claimed.format1()} → ${actual.format1()}")
                    }
                    match.groupValues[1] + house.toString() + match.groupValues[3] + "power: ${actual.format1()}"
                } else match.value
            } else match.value
        }

        return updated
    }

    private fun normalizeHouse(text: String): Int? {
        val lower = text.lowercase(Locale.US)
        Regex("(\\d+)").find(lower)?.let { return it.groupValues[1].toIntOrNull() }
        val mapping = mapOf(
            "first" to 1,
            "second" to 2,
            "third" to 3,
            "fourth" to 4,
            "fifth" to 5,
            "sixth" to 6,
            "seventh" to 7,
            "eighth" to 8,
            "ninth" to 9,
            "tenth" to 10,
            "eleventh" to 11,
            "twelfth" to 12
        )
        for ((word, value) in mapping) {
            if (lower.contains(word)) return value
        }
        return null
    }

    private fun ownerFromWord(word: String): Owner {
        val cleaned = word.trim().lowercase(Locale.US)
        return if (cleaned in listOf("his", "her", "their", "partner", "partner's")) Owner.PARTNER else Owner.USER
    }

    private fun ownerWord(owner: Owner): String = if (owner == Owner.USER) "your" else "your partner's"

    private fun aspectExists(key: String, context: Context): Boolean = when (context.kind) {
        Kind.NATAL -> context.allowed.natal.contains(key)
        Kind.SYNASTRY -> context.allowed.synastry.contains(key) || context.allowed.natal.contains(key)
        Kind.TRANSITS -> context.allowed.transits.contains(key)
        Kind.PROGRESSIONS -> context.allowed.progressions.contains(key)
    }

    private fun aspectKey(
        ownerA: Owner,
        planetA: String,
        aspect: String,
        ownerB: Owner,
        planetB: String
    ): String {
        val a = if (ownerA == Owner.USER) "user" else "partner"
        val b = if (ownerB == Owner.USER) "user" else "partner"
        return "${a}:${planetA}_${aspect}_${b}:${planetB}"
    }

    private fun normalizeAspect(text: String): String {
        val lower = text.lowercase(Locale.US)
        return when (lower) {
            "conjunct" -> "conjunction"
            "opposite" -> "opposition"
            "quincunx", "inconjunct" -> "inconjunction"
            else -> lower
        }
    }

    private fun sentenceContaining(range: IntRange, text: String): String? {
        var start = range.first
        var end = range.last
        while (start > 0 && !".?!\n".contains(text[start - 1])) start--
        while (end < text.length - 1 && !".?!\n".contains(text[end])) end++
        return text.substring(start, end + 1).trim().takeIf { it.isNotEmpty() }
    }

    private fun soften(sentence: String): String {
        var result = sentence
        result = result.replace(" is ", " may be ", ignoreCase = true)
        result = result.replace(" are ", " might be ", ignoreCase = true)
        result = result.replace(" exact ", " notable ", ignoreCase = true)
        return result
    }

    private fun signsCanForm(signA: String, signB: String, aspect: String): Boolean {
        val order = listOf(
            "aries", "taurus", "gemini", "cancer", "leo", "virgo",
            "libra", "scorpio", "sagittarius", "capricorn", "aquarius", "pisces"
        )
        val a = order.indexOf(signA)
        val b = order.indexOf(signB)
        if (a == -1 || b == -1) return true
        val diff = abs(a - b)
        val distance = minOf(diff, 12 - diff)
        return when (aspect) {
            "conjunction", "conjunct" -> distance == 0
            "opposition" -> distance == 6
            "trine" -> distance == 4
            "square" -> distance == 3
            "sextile" -> distance == 2
            "inconjunction" -> distance == 5
            else -> true
        }
    }

    private fun ordinal(num: Int): String = when (num) {
        1 -> "1st"
        2 -> "2nd"
        3 -> "3rd"
        else -> "${num}th"
    }

    private fun String.cap(): String = replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.US) else it.toString() }

    private fun Double.format1(): String = String.format(Locale.US, "%.1f", this)

    private object PronounAuditor {
        fun audit(text: String): String {
            var updated = text
            updated = updated.replace("your ", "", ignoreCase = true)
            updated = updated.replace("his ", "", ignoreCase = true)
            updated = updated.replace("her ", "", ignoreCase = true)
            updated = updated.replace("their ", "", ignoreCase = true)
            updated = updated.replace("partner's", "partner", ignoreCase = true)
            updated = updated.replace("partner’s", "partner", ignoreCase = true)
            return updated
        }
    }

    private object Humility {
        fun inject(text: String): String = text
    }
}
