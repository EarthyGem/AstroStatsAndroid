package app.lilaverse.astrostatsandroid

import android.content.Intent
import android.location.Geocoder
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import app.lilaverse.astrostatsandroid.chat.ChatActivity
import app.lilaverse.astrostatsandroid.model.Chart
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AstrocartographyTab(
    chart: Chart,
    chartCake: ChartCake
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var allLines by remember { mutableStateOf(emptyList<AstroLine>()) }
    var isLoading by remember { mutableStateOf(true) }
    var selectedLine by remember { mutableStateOf<AstroLine?>(null) }
    var searchQuery by remember { mutableStateOf("") }
    var selectedLocation by remember { mutableStateOf<LatLng?>(null) }
    var selectedLocationName by remember { mutableStateOf<String?>(null) }

    val availablePlanets = remember {
        listOf(
            CelestialObject.Planet(Planet.Sun),
            CelestialObject.Planet(Planet.Moon),
            CelestialObject.Planet(Planet.Mercury),
            CelestialObject.Planet(Planet.Venus),
            CelestialObject.Planet(Planet.Mars),
            CelestialObject.Planet(Planet.Jupiter),
            CelestialObject.Planet(Planet.Saturn),
            CelestialObject.Planet(Planet.Uranus),
            CelestialObject.Planet(Planet.Neptune),
            CelestialObject.Planet(Planet.Pluto),
            CelestialObject.SouthNode
        )
    }

    val availableLineTypes = remember { LineType.values().toList() }

    var selectedPlanets by remember { mutableStateOf(availablePlanets.toSet()) }
    var selectedLineTypes by remember { mutableStateOf(availableLineTypes.toSet()) }

    val filteredLines by remember {
        derivedStateOf {
            allLines.filter { line ->
                selectedPlanets.contains(line.planet) && selectedLineTypes.contains(line.lineType)
            }
        }
    }

    val initialCameraPosition = remember(chart.latitude, chart.longitude) {
        val latitude = chart.latitude.takeIf { !it.isNaN() } ?: 0.0
        val longitude = chart.longitude.takeIf { !it.isNaN() } ?: 0.0
        CameraPosition.fromLatLngZoom(LatLng(latitude, longitude), 2f)
    }
    val cameraPositionState: CameraPositionState = rememberCameraPositionState {
        position = initialCameraPosition
    }

    val relocatedChartCake = remember(selectedLocation) {
        selectedLocation?.let { location ->
            chartCake.createRelocatedChart(location.latitude, location.longitude)
        }
    }

    LaunchedEffect(chartCake) {
        isLoading = true
        val lines = withContext(Dispatchers.Default) {
            AstrocartographyCalculator.calculatePlanetaryLines(chartCake)
        }
        allLines = lines
        isLoading = false
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Search for a place") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            trailingIcon = {
                IconButton(onClick = {
                    if (searchQuery.isBlank()) {
                        Toast.makeText(context, "Enter a location", Toast.LENGTH_SHORT).show()
                    } else {
                        coroutineScope.launch {
                            val result = geocodeLocation(context, searchQuery)
                            if (result != null) {
                                cameraPositionState.animate(CameraUpdateFactory.newLatLngZoom(result, 4f))
                                selectedLocation = result
                                selectedLocationName = reverseGeocode(context, result)
                                selectedLine = null
                            } else {
                                Toast.makeText(context, "Location not found", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }) {
                    Icon(Icons.Default.Search, contentDescription = "Search")
                }
            }
        )

        FilterSection(
            selectedPlanets = selectedPlanets,
            onTogglePlanet = { planet ->
                selectedPlanets = if (selectedPlanets.contains(planet)) {
                    selectedPlanets - planet
                } else {
                    selectedPlanets + planet
                }
            },
            selectedLineTypes = selectedLineTypes,
            onToggleLineType = { lineType ->
                selectedLineTypes = if (selectedLineTypes.contains(lineType)) {
                    selectedLineTypes - lineType
                } else {
                    selectedLineTypes + lineType
                }
            },
            availablePlanets = availablePlanets,
            availableLineTypes = availableLineTypes
        )

        Box(modifier = Modifier.weight(1f)) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                properties = MapProperties(),
                uiSettings = MapUiSettings(zoomControlsEnabled = false, compassEnabled = true),
                onMapClick = { latLng ->
                    selectedLocation = latLng
                    selectedLine = null
                    coroutineScope.launch {
                        selectedLocationName = reverseGeocode(context, latLng)
                    }
                }
            ) {
                filteredLines.forEach { line ->
                    if (line.coordinates.size > 1) {
                        Polyline(
                            points = line.coordinates,
                            color = line.color,
                            width = if (selectedLine?.id == line.id) 10f else 6f,
                            geodesic = true,
                            pattern = line.lineType.pattern,
                            clickable = true,
                            zIndex = if (selectedLine?.id == line.id) 2f else 1f,
                            onClick = {
                                selectedLine = line
                            }
                        )
                    }
                }

                selectedLocation?.let { location ->
                    Marker(
                        state = MarkerState(position = location),
                        title = selectedLocationName ?: formatCoordinate(location)
                    )
                }
            }

            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.25f)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }

        selectedLocation?.let { location ->
            val locationTitle = selectedLocationName ?: formatCoordinate(location)
            val selectedSummary = selectedLine?.let { line ->
                "${line.label} • ${line.lineType.description}"
            } ?: "Tap a planetary line to highlight it."

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = locationTitle,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = selectedSummary,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            relocatedChartCake?.let { relocated ->
                                val intent = Intent(context, ChatActivity::class.java).apply {
                                    putExtra("chartCake", chartCake)
                                    putExtra("userName", chart.name)
                                    putExtra(ChatActivity.EXTRA_IS_RELOCATED, true)
                                    putExtra(ChatActivity.EXTRA_RELOCATED_CHART, relocated)
                                    putExtra(ChatActivity.EXTRA_LOCATION_NAME, locationTitle)
                                    putExtra(ChatActivity.EXTRA_LOCATION_LATITUDE, location.latitude)
                                    putExtra(ChatActivity.EXTRA_LOCATION_LONGITUDE, location.longitude)
                                    putExtra(ChatActivity.EXTRA_ORIGINAL_LOCATION_NAME, chart.locationName.ifBlank { chart.birthPlace })
                                    putExtra(ChatActivity.EXTRA_ORIGINAL_LATITUDE, chart.latitude)
                                    putExtra(ChatActivity.EXTRA_ORIGINAL_LONGITUDE, chart.longitude)
                                }
                                context.startActivity(intent)
                            }
                        },
                        enabled = relocatedChartCake != null
                    ) {
                        Text("Chat with Lila about this location")
                    }
                }
            }
        }
    }
}

@Composable
private fun FilterSection(
    selectedPlanets: Set<CelestialObject>,
    onTogglePlanet: (CelestialObject) -> Unit,
    selectedLineTypes: Set<LineType>,
    onToggleLineType: (LineType) -> Unit,
    availablePlanets: List<CelestialObject>,
    availableLineTypes: List<LineType>
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(availablePlanets, key = { it.keyName }) { planet ->
                val isSelected = selectedPlanets.contains(planet)
                FilterChip(
                    selected = isSelected,
                    onClick = { onTogglePlanet(planet) },
                    label = { Text(planetLabel(planet)) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Public,
                            contentDescription = null
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        selectedContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                items = availableLineTypes,
                key = { it.code }
            ) { lineType ->
                val isSelected = selectedLineTypes.contains(lineType)
                FilterChip(
                    selected = isSelected,
                    onClick = { onToggleLineType(lineType) },
                    label = { Text(lineType.displayName) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.FilterAlt,
                            contentDescription = null
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        selectedContainerColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.25f)
                    )
                )
            }
        }
    }
}

private fun planetLabel(celestialObject: CelestialObject): String {
    return when (celestialObject) {
        is CelestialObject.Planet -> "${celestialObject.planet.symbol} ${celestialObject.planet.keyName}"
        CelestialObject.SouthNode -> "☋ South Node"
        else -> celestialObject.keyName
    }
}

private suspend fun geocodeLocation(context: android.content.Context, query: String): LatLng? {
    if (!Geocoder.isPresent()) return null
    return withContext(Dispatchers.IO) {
        try {
            @Suppress("DEPRECATION")
            val results = Geocoder(context, Locale.getDefault()).getFromLocationName(query, 1)
            if (!results.isNullOrEmpty()) {
                val location = results.first()
                LatLng(location.latitude, location.longitude)
            } else {
                null
            }
        } catch (io: IOException) {
            null
        }
    }
}

private suspend fun reverseGeocode(context: android.content.Context, latLng: LatLng): String? {
    if (!Geocoder.isPresent()) return null
    return withContext(Dispatchers.IO) {
        try {
            @Suppress("DEPRECATION")
            val results = Geocoder(context, Locale.getDefault()).getFromLocation(latLng.latitude, latLng.longitude, 1)
            if (!results.isNullOrEmpty()) {
                val place = results.first()
                listOfNotNull(place.locality, place.adminArea, place.countryName)
                    .joinToString(", ")
                    .ifBlank { place.featureName ?: formatCoordinate(latLng) }
            } else {
                null
            }
        } catch (io: IOException) {
            null
        }
    }
}

private fun formatCoordinate(latLng: LatLng): String {
    return String.format(Locale.getDefault(), "%.2f°, %.2f°", latLng.latitude, latLng.longitude)
}