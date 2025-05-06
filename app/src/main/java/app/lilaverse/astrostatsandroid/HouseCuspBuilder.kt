package app.lilaverse.astrostatsandroid

import swisseph.SwissEph
import swisseph.SweDate
import swisseph.SweConst
import java.util.*

object HouseCuspBuilder {
    private val swe = SwissEph()

    // Update to take timezone parameter
    private fun calculateJulianDay(date: Date, timezone: String): Double {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone(timezone))
        calendar.time = date

        val timeZoneOffset = calendar.timeZone.getOffset(calendar.timeInMillis) / 3600000.0
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val hour = calendar.get(Calendar.HOUR_OF_DAY) + calendar.get(Calendar.MINUTE) / 60.0

        val utcHour = hour - timeZoneOffset

        return SweDate.getJulDay(year, month, day, utcHour, SweDate.SE_GREG_CAL)
    }

    // Update to take timezone parameter
    fun create(latitude: Double, longitude: Double, date: Date, timezone: String = TimeZone.getDefault().id): HouseCusps {
        val jd = calculateJulianDay(date, timezone)
        val cusps = DoubleArray(13)
        val ascmc = DoubleArray(10)

        swe.swe_houses(
            jd,
            SweConst.SEFLG_SWIEPH,
            latitude,
            longitude,
            SweConst.SE_HSYS_PLACIDUS,
            cusps,
            ascmc
        )

        // Force correct ASC and MC into cusps array
        cusps[1] = ascmc[0]  // ASC
        cusps[10] = ascmc[1] // MC

        // Build the list of Cusp objects
        val cuspList = (1..12).map { index ->
            Cusp(index, cusps[index])
        }

        return HouseCusps(cuspList)
    }
}