package app.lilaverse.astrostatsandroid

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ScoreBar(label: String, percentage: Float, score: Float, barColor: Color) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, modifier = Modifier.width(80.dp), color = Color.White)
        Box(
            Modifier
                .weight(1f)
                .height(24.dp)
                .background(Color.DarkGray)
        ) {
            Box(
                Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(percentage / 30f) // adjust scale
                    .background(barColor),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = "${percentage}%",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 4.dp),
                    color = Color.White
                )
            }
        }
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = "%.1f".format(score),
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.align(Alignment.CenterVertically),
            color = Color.White
        )
    }
}
