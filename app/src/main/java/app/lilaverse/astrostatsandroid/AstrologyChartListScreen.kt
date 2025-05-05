// AstrologyChartListScreen.kt
package app.lilaverse.astrostatsandroid

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.lilaverse.astrostatsandroid.ui.ChartListItem
import app.lilaverse.astrostatsandroid.model.Chart

@Composable
fun AstrologyChartListScreen(
    charts: List<Chart>,
    modifier: Modifier = Modifier,
    onAddChartClicked: () -> Unit,
    onChartSelected: (Chart) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column(Modifier.padding(horizontal = 16.dp)) {
            // Title
            Text(
                "Astrology Charts",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(vertical = 20.dp)
            )

            // Search bar
            TextField(
                value = "",
                onValueChange = { /* Search functionality */ },
                placeholder = { Text("Search by name or place", color = Color.Gray) },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp)),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFF2A2A2A),
                    focusedContainerColor = Color(0xFF2A2A2A),
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = Color.Gray
                    )
                },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (charts.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
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
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .clip(RoundedCornerShape(16.dp))
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black)
                    ) {
                        itemsIndexed(charts) { index, chart ->
                            val isLast = index == charts.lastIndex
                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(
                                        when {
                                            charts.size == 1 -> RoundedCornerShape(16.dp)
                                            index == 0 -> RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                                            isLast -> RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
                                            else -> RoundedCornerShape(0.dp)
                                        }
                                    ),
                                color = Color(0xFF1A1A1A)
                            ) {
                                ChartListItem(chart = chart) {
                                    onChartSelected(chart)
                                }
                            }

                            if (!isLast) {
                                Divider(
                                    color = Color(0xFF333333),
                                    thickness = 1.dp
                                )
                            }
                        }
                    }
                }
            }
        }

        // 🟦 FAB aligned to the outer Box
        FloatingActionButton(
            onClick = onAddChartClicked,
            containerColor = Color(0xFF007AFF),
            contentColor = Color.White,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
                .size(60.dp)
                .clip(RoundedCornerShape(16.dp))
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add Chart",
                modifier = Modifier.size(24.dp)
            )
        }
    } // <-- closes root Box
}
