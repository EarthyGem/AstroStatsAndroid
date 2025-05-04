package app.lilaverse.astrostatsandroid

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import app.lilaverse.astrostatsandroid.ui.ChartListItem
import app.lilaverse.astrostatsandroid.model.Chart

@Composable
fun AstrologyChartListScreen(
    charts: List<Chart>,
    modifier: Modifier = Modifier,
    onAddChartClicked: () -> Unit,
    onChartSelected: (Chart) -> Unit
) {
    Box(modifier = modifier.fillMaxSize()) {
        if (charts.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "No charts yet",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = onAddChartClicked) {
                    Text("Add Your First Chart")
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(charts) { chart ->
                    ChartListItem(chart = chart) {
                        onChartSelected(chart)
                    }
                }
            }
        }
    }
}
