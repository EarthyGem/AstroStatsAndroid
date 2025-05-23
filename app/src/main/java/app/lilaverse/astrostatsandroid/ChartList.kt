package app.lilaverse.astrostatsandroid

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ChartList(charts: List<String>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier.padding(16.dp)) {
        items(charts) { chart ->
            Text(
                text = chart,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    }
}
