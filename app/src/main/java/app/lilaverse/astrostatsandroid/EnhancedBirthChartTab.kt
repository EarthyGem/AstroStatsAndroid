package app.lilaverse.astrostatsandroid

import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import app.lilaverse.astrostatsandroid.model.Chart
import app.lilaverse.astrostatsandroid.view.BirthChartView
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun EnhancedBirthChartTab(chart: Chart) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Chart info header
        ChartInfoHeader(chart)

        Spacer(modifier = Modifier.height(16.dp))

        // Birth chart wheel
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f), // Keep it square
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(androidx.compose.ui.graphics.Color.White)
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                BirthChartWheel(chart)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Planet positions summary
        PlanetaryPositionsSummary(chart)
    }
}

@Composable
fun ChartInfoHeader(chart: Chart) {
    val dateFormat = SimpleDateFormat("MMM d, yyyy 'at' h:mma", Locale.getDefault())
    val formattedDate = dateFormat.format(chart.date)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = chart.name,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Text(
            text = "$formattedDate • ${chart.birthPlace}",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Key chart info with glyphs
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            PlanetSignInfo("☉", "Sun", chart.sunSign)
            PlanetSignInfo("☽", "Moon", chart.moonSign)
            PlanetSignInfo("↑", "Rising", chart.risingSign)
        }
    }
}

@Composable
fun PlanetSignInfo(symbol: String, label: String, sign: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = symbol,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = label,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = sign,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun BirthChartWheel(chart: Chart) {
    val context = LocalContext.current

    // Create and remember the ChartCake instance
    val chartCake = remember(chart) {
        ChartCake.from(chart)
    }

    // Embed the custom native view
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { ctx ->
            BirthChartView(ctx).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            }
        },
        update = { view ->
            // Update the view with chart data when recomposed
            view.setChart(chartCake)
        }
    )
}

@Composable
fun PlanetaryPositionsSummary(chart: Chart) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Planetary Positions",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        if (chart.planetaryPositions.isNotEmpty()) {
            // Display a condensed list of planet positions
            val columnCount = 2
            val rows = (chart.planetaryPositions.size + columnCount - 1) / columnCount

            for (row in 0 until rows) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    for (col in 0 until columnCount) {
                        val index = row * columnCount + col
                        if (index < chart.planetaryPositions.size) {
                            Text(
                                text = chart.planetaryPositions[index],
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.weight(1f)
                            )
                        } else {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
            }
        } else {
            Text(
                text = "No planetary positions available",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}

// Note: We've removed the BirthChartTab function that was causing the conflict