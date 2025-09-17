package app.lilaverse.astrostatsandroid.model

import app.lilaverse.astrostatsandroid.HouseCusps
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Chart(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val date: Date,
    val birthPlace: String,
    val locationName: String,
    val latitude: Double,
    val longitude: Double,
    /**
     * Time zone identifier used for ephemeris calculations. This is stored as a resolved
     * GMT offset string (for example, "GMT-07:00") so that manual daylight-saving
     * corrections are preserved when charts are re-opened.
     */
    val timezone: String,
    /**
     * Location-based IANA time zone identifier returned by the Places / Time Zone API.
     * This value is surfaced to the user so they can confirm which region their chart
     * is associated with even if the calculation zone is a manual GMT offset.
     */
    val timezoneId: String = timezone,
    /** Human-friendly daylight-saving label from the API (e.g. "Pacific Daylight Time"). */
    val timezoneLabel: String? = null,
    /** Raw offset from UTC, in minutes, reported by the API. */
    val rawOffsetMinutes: Int = 0,
    /** Daylight-saving offset magnitude, in minutes. */
    val dstOffsetMinutes: Int = 0,
    /** Whether daylight-saving adjustments were enabled when the chart was saved. */
    val isDstActive: Boolean = false,
    val planetaryPositions: List<String>,
    val sunSign: String,
    val moonSign: String,
    val risingSign: String,
    val houseCusps: HouseCusps

)