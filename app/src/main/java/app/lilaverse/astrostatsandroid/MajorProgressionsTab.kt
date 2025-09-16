package app.lilaverse.astrostatsandroid

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import app.lilaverse.astrostatsandroid.view.MajorProgressionsBiwheelView

@Composable
fun MajorProgressionsTab(chartCake: ChartCake) {
    val aspects = remember(chartCake) { calculateMajorProgressedAspects(chartCake) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Major Progressions",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .height(360.dp),
            factory = { ctx ->
                MajorProgressionsBiwheelView(ctx).apply {
                    setChartCake(chartCake)
                }
            },
            update = { view ->
                view.setChartCake(chartCake)
            }
        )

        if (aspects.isEmpty()) {
            Text(
                text = "No major progressed aspects are currently within orb.",
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        } else {
            Text(
                text = "Progressed Planet Contacts",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(aspects) { aspect ->
                    ProgressionAspectCard(aspect = aspect, chartCake = chartCake)
                }
                item { Spacer(modifier = Modifier.height(16.dp)) }
            }
        }
    }
}

private fun calculateMajorProgressedAspects(chartCake: ChartCake): List<CelestialAspect> {
    val natalPlanets = chartCake.natalBodies.filter { it.body is CelestialObject.Planet }
    val progressedPlanets = chartCake.majorBodies.filter { it.body is CelestialObject.Planet }

    val aspects = mutableListOf<CelestialAspect>()
    for (progressed in progressedPlanets) {
        for (natal in natalPlanets) {
            val angle = kotlin.math.abs(progressed.longitude - natal.longitude) % 360
            val normalized = if (angle > 180) 360 - angle else angle
            val kind = Kind.fromAngle(normalized) ?: continue
            if (kind !in Kind.primary) continue
            val aspect = CelestialAspect(
                kind = kind,
                body1 = progressed,
                body2 = natal,
                angle = normalized,
                orb = kind.orb
            )
            aspects.add(aspect)
        }
    }

    return aspects
        .sortedWith(compareBy({ it.orbDelta }, { it.kind.angle }))
}

@Composable
private fun ProgressionAspectCard(aspect: CelestialAspect, chartCake: ChartCake) {
    val progressed = aspect.body1
    val natal = aspect.body2
    val progressedHouse = chartCake.majorHouseCusps.houseForLongitude(progressed.longitude)
    val natalHouse = chartCake.houseCusps.houseForLongitude(natal.longitude)
    val orbText = "%.1f°".format(aspect.orbDelta)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = "Progressed ${progressed.body.keyName} ${aspect.kind.description.replaceFirstChar { it.uppercase() }} Natal ${natal.body.keyName}",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = "Progressed ${progressed.signName} • ${ordinal(progressedHouse)} house",
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Text(
                text = "Natal ${natal.signName} • ${ordinal(natalHouse)} house",
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Text(
                text = "Orb: $orbText",
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}

private fun ordinal(number: Int): String {
    val suffix = when {
        number % 100 in 11..13 -> "th"
        number % 10 == 1 -> "st"
        number % 10 == 2 -> "nd"
        number % 10 == 3 -> "rd"
        else -> "th"
    }
    return "$number$suffix"
}