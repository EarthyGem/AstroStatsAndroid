package app.lilaverse.astrostatsandroid.model

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
    val timezone: String,
    val planetaryPositions: List<String>,
    val sunSign: String,
    val moonSign: String,
    val risingSign: String
)
