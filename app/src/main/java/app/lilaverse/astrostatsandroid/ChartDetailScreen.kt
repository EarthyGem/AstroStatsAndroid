package app.lilaverse.astrostatsandroid

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.lilaverse.astrostatsandroid.model.Chart
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun ChartDetailScreen(chart: Chart, navController: NavController? = null) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(chart.name, fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))

        Text("Born in ${chart.birthPlace}")
        Text("Date: ${chart.date}")
        Text("Time Zone: ${chart.timezone}")
        Spacer(modifier = Modifier.height(8.dp))

        Text("☉ Sun: ${chart.sunSign}")
        Text("☽ Moon: ${chart.moonSign}")
        Text("↑ Rising: ${chart.risingSign}")
        Spacer(modifier = Modifier.height(16.dp))

        Divider()

        Text("Birth Chart", fontSize = 20.sp, fontWeight = FontWeight.Medium, modifier = Modifier.padding(vertical = 8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        ) {
            // 🔧 TODO: Custom chart wheel Composable (Canvas-based)
            Text("Chart wheel goes here", modifier = Modifier.align(Alignment.Center))
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Sections
        Text("Planetary Positions", fontWeight = FontWeight.Medium)
        chart.planetaryPositions.forEach {
            Text(it)
        }

        Spacer(modifier = Modifier.height(16.dp))
        // You can add Signs, Houses, and Aspects here
    }
}
