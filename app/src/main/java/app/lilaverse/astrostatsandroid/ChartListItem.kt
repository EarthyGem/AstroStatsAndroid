package app.lilaverse.astrostatsandroid.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.lilaverse.astrostatsandroid.model.Chart
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ChartListItem(chart: Chart, onClick: () -> Unit) {
    val dateFormat = SimpleDateFormat("MMM d, yyyy 'at' h:mma", Locale.getDefault())
    val dateText = dateFormat.format(chart.date)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        Text(chart.name, style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(4.dp))
        Text("$dateText • ${chart.birthPlace}", fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text("${chart.sunSign} Sun • ${chart.moonSign} Moon • ${chart.risingSign} Rising", fontSize = 14.sp)
    }
}
