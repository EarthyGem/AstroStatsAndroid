package app.lilaverse.astrostatsandroid

import androidx.compose.ui.graphics.Color
import com.google.android.gms.maps.model.Dash
import com.google.android.gms.maps.model.Gap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PatternItem
import java.util.UUID

data class AstroLine(
    val id: String = UUID.randomUUID().toString(),
    val planet: CelestialObject,
    val lineType: LineType,
    val coordinates: List<LatLng>,
    val color: Color,
    val label: String
)

enum class LineType(
    val code: String,
    val description: String,
    val pattern: List<PatternItem>?
) {
    ASCENDANT("AC", "Ascendant", listOf(Dash(28f), Gap(18f))),
    DESCENDANT("DC", "Descendant", listOf(Dash(28f), Gap(18f))),
    MIDHEAVEN("MC", "Midheaven", null),
    IMUM_COELI("IC", "Imum Coeli", null);

    val displayName: String
        get() = "$code — $description"
}