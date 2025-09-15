// AstrologyChartListScreen.kt
package app.lilaverse.astrostatsandroid

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.lilaverse.astrostatsandroid.ui.ChartListItem
import app.lilaverse.astrostatsandroid.model.Chart
import kotlinx.coroutines.launch
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import kotlin.math.roundToInt

@Composable
fun AstrologyChartListScreen(
    charts: List<Chart>,
    modifier: Modifier = Modifier,
    onAddChartClicked: () -> Unit,
    onChartSelected: (Chart, ChartCake) -> Unit,
    onChartEdit: (Chart) -> Unit = {}, // New parameter for edit action
    onChartDelete: (Chart) -> Unit = {} // New parameter for delete action
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column(Modifier.padding(horizontal = 16.dp)) {
            // Title
            Text(
                "AstroStats",
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

                            SwipeableChartItem(
                                chart = chart,
                                isLast = isLast,
                                isFirst = index == 0,
                                singleItem = charts.size == 1,
                                onItemClick = { chartCake -> onChartSelected(chart, chartCake) },
                                onEdit = { onChartEdit(chart) },
                                onDelete = { onChartDelete(chart) }
                            )

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

        // FAB aligned to the outer Box
        FloatingActionButton(
            onClick = onAddChartClicked,
            containerColor = Color.Black,
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
    }
}

@Composable
fun SwipeableChartItem(
    chart: Chart,
    isLast: Boolean,
    isFirst: Boolean,
    singleItem: Boolean,
    onItemClick: (ChartCake) -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    val scope = rememberCoroutineScope()

    // Width of the action areas in pixels
    val triggerDistance = with(LocalDensity.current) { 80.dp.toPx() }
    val editThreshold = -triggerDistance
    val deleteThreshold = triggerDistance

    // Offset state
    var offsetX by remember { mutableStateOf(0f) }

    // Draggable state
    val draggableState = rememberDraggableState { delta ->
        offsetX += delta
    }
    val chartCake = remember(chart) { ChartCake.from(chart) }

    Box {
        // Background with actions
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Edit action (revealed when swiping right)
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .background(Color(0xFF4CAF50)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit",
                    tint = Color.White
                )
            }

            // Delete action (revealed when swiping left)
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .background(Color(0xFFE91E63)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = Color.White
                )
            }
        }

        // Foreground item that can be swiped
        Surface(
            modifier = Modifier
                .offset { IntOffset(offsetX.roundToInt(), 0) }
                .fillMaxWidth()
                .clip(
                    when {
                        singleItem -> RoundedCornerShape(16.dp)
                        isFirst -> RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                        isLast -> RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
                        else -> RoundedCornerShape(0.dp)
                    }
                )
                .draggable(
                    state = draggableState,
                    orientation = Orientation.Horizontal,
                    onDragStopped = {
                        when {
                            offsetX > deleteThreshold -> {
                                // Swiped right (to reveal edit)
                                onEdit()
                                offsetX = 0f // Reset position
                            }
                            offsetX < editThreshold -> {
                                // Swiped left (to reveal delete)
                                onDelete()
                                offsetX = 0f // Reset position
                            }
                            else -> {
                                // Not far enough, snap back
                                offsetX = 0f
                            }

                        }
                    }
                ),
            color = Color(0xFF1A1A1A)
        ) {
            ChartListItem(chart = chart, chartCake = chartCake) {
                if (offsetX == 0f) {
                    onItemClick(chartCake)
                } else {
                    // Reset position if tapped while swiping
                    offsetX = 0f
                }
            }
        }
    }
}