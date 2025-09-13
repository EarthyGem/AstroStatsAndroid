package app.lilaverse.astrostatsandroid
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class Cusp(
    val index: Int,
    val longitude: Double
) : Parcelable {
    val normalizedLongitude: Double
        get() = (longitude % 360 + 360) % 360

    val sign: String
        get() = Zodiac.signForDegree(normalizedLongitude)

    val degreeInSign: Int
        get() = (normalizedLongitude % 30).toInt()

    val minuteInSign: Int
        get() = (((normalizedLongitude % 30) - degreeInSign) * 60).toInt()
}
