package app.lilaverse.astrostatsandroid

import java.util.Date
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

// Small container like in your Swift
data class Metric(val power: Double, val netHarmony: Double = 0.0)

// Use US symbols so you always get a dot decimal, regardless of device locale
private val oneDecFmt = DecimalFormat("0.0", DecimalFormatSymbols(Locale.US))
fun Double.f1(): String = oneDecFmt.format(this)

fun degToDms(lon: Double): String {
    val n = ((lon % 360 + 360) % 360)
    val d = n.toInt()
    val mFloat = (n - d) * 60
    val m = mFloat.toInt()
    val s = ((mFloat - m) * 60).toInt()
    return "%d°%02d′%02d″".format(d, m, s)
}

fun ageString(from: Date, to: Date = Date()): String {
    val years = ((to.time - from.time) / (365.2425 * 24 * 3600 * 1000.0)).toInt()
    val remDays = ((to.time - from.time) / (24 * 3600 * 1000.0) - years * 365.2425)
    val months = (remDays / 30.436875).toInt()
    return "${years}y ${months}m"
}

fun prettySign(s: Zodiac.Sign): String =
    s.name.lowercase(Locale.US).replaceFirstChar { it.titlecase(Locale.US) }
