package app.lilaverse.astrostatsandroid

import swisseph.SwissEph
import swisseph.SweDate
import java.util.*

object HouseCuspBuilder {
    private val swe = SwissEph()

    fun create(latitude: Double, longitude: Double, date: Date): HouseCusps {
        val jd = calculateJulianDay(date)
        val cusps = DoubleArray(13) // 1–12 are used
        val ascmc = DoubleArray(10)

        swe.swe_houses(
            jd,
            'P'.code,     // House system: Placidus
            latitude,
            longitude,
            0,            // Flag (should be 0)
            cusps,
            ascmc
        )

        val cuspList = (1..12).map { i ->
            val lon = (cusps[i] + 360.0) % 360.0
            Cusp(index = i, longitude = lon)
        }

        return HouseCusps(cuspList)
    }

    private fun calculateJulianDay(date: Date): Double {
        val cal = Calendar.getInstance()
        cal.time = date

        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH) + 1
        val day = cal.get(Calendar.DAY_OF_MONTH)
        val hour = cal.get(Calendar.HOUR_OF_DAY) + cal.get(Calendar.MINUTE) / 60.0

        return SweDate.getJulDay(year, month, day, hour, SweDate.SE_GREG_CAL)
    }
}
