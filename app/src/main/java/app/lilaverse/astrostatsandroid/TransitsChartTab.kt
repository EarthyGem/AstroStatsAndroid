package app.lilaverse.astrostatsandroid

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import app.lilaverse.astrostatsandroid.model.Chart
import app.lilaverse.astrostatsandroid.view.BirthChartView
import app.lilaverse.astrostatsandroid.view.ChartDisplayMode
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun TransitsTab(chart: Chart, chartCake: ChartCake) {
    val dateFormat = remember { SimpleDateFormat("MMM d, yyyy 'at' h:mma", Locale.getDefault()) }
    val transitDateText = remember(chartCake.transitDate) { dateFormat.format(chartCake.transitDate) }
    val transitPositions = remember(chartCake) {
        chartCake.transitBodies.map { it.formatAsSignDegree() }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Transits for ${chart.name}",
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )

        Text(
            text = "Transit moment: $transitDateText",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 4.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                AndroidView(
                    modifier = Modifier.fillMaxSize(),
                    factory = { context ->
                        BirthChartView(context).apply {
                            setDisplayMode(ChartDisplayMode.TRANSIT_BIWHEEL)
                            setChart(chartCake)
                        }
                    },
                    update = { view ->
                        view.setDisplayMode(ChartDisplayMode.TRANSIT_BIWHEEL)
                        view.setChart(chartCake)
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Pinch to zoom and drag to explore the biwheel.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Transiting Positions",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (transitPositions.isEmpty()) {
            Text(
                text = "No transiting bodies available.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                transitPositions.forEach { position ->
                    Text(
                        text = position,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}