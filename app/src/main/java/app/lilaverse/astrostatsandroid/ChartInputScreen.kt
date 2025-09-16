package app.lilaverse.astrostatsandroid

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.lilaverse.astrostatsandroid.model.Chart
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import java.text.SimpleDateFormat
import java.util.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.json.JSONObject
import java.net.URL
import java.net.HttpURLConnection

@Composable
fun ChartInputScreen(
    editChart: Chart? = null,
    onSaveComplete: (Chart) -> Unit,
    onCancel: () -> Unit
) {
    val context = LocalContext.current

    // Initialize with either the edit chart values or defaults
    val defaultCalendar = Calendar.getInstance(
        if (editChart?.timezone != null)
            TimeZone.getTimeZone(editChart.timezone)
        else
            TimeZone.getTimeZone("America/Los_Angeles")
    )

    if (editChart == null) {
        defaultCalendar.set(1977, Calendar.MAY, 21, 13, 57)
    } else {
        defaultCalendar.time = editChart.date
    }

    var name by remember { mutableStateOf(editChart?.name ?: "") }
    var place by remember { mutableStateOf(editChart?.birthPlace ?: "") }
    var dateTimeText by remember { mutableStateOf("") }

    var selectedDateTime by remember { mutableStateOf(defaultCalendar) }
    var latitude by remember { mutableStateOf(editChart?.latitude ?: 32.7157) }
    var longitude by remember { mutableStateOf(editChart?.longitude ?: -117.1611) }
    var timezone by remember { mutableStateOf(editChart?.timezone ?: "America/Los_Angeles") }

    var isNameError by remember { mutableStateOf(false) }
    var isPlaceError by remember { mutableStateOf(false) }
    var isDateTimeError by remember { mutableStateOf(false) }
    var isSaving by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    // Initialize planet positions based on either edit chart or current values
    var planetPositions by remember {
        mutableStateOf(
            if (editChart != null)
                editChart.planetaryPositions
            else
                getPlanetPositionsFor(selectedDateTime.time, timezone)
        )
    }

    // If we have an edit chart, format the date initially
    LaunchedEffect(editChart) {
        if (editChart != null) {
            val format = SimpleDateFormat("MMM dd, yyyy 'at' h:mm a", Locale.getDefault())
            format.timeZone = TimeZone.getTimeZone(editChart.timezone)
            dateTimeText = format.format(editChart.date)
        }
    }

    // Function to update timezone and recalculate positions
    fun updateSelectedDateTimeWithTimezone(newTimezone: String) {
        // Get the current date components
        val currentCal = Calendar.getInstance()
        currentCal.time = selectedDateTime.time

        // Create a new calendar with the birth location's timezone
        val locationCal = Calendar.getInstance(TimeZone.getTimeZone(newTimezone))

        // Copy the date components
        locationCal.set(Calendar.YEAR, currentCal.get(Calendar.YEAR))
        locationCal.set(Calendar.MONTH, currentCal.get(Calendar.MONTH))
        locationCal.set(Calendar.DAY_OF_MONTH, currentCal.get(Calendar.DAY_OF_MONTH))
        locationCal.set(Calendar.HOUR_OF_DAY, currentCal.get(Calendar.HOUR_OF_DAY))
        locationCal.set(Calendar.MINUTE, currentCal.get(Calendar.MINUTE))

        // Update the selected date/time
        selectedDateTime = locationCal

        // Update the display text
        val format = SimpleDateFormat("MMM dd, yyyy 'at' h:mm a", Locale.getDefault())
        format.timeZone = TimeZone.getTimeZone(newTimezone)
        dateTimeText = format.format(selectedDateTime.time)

        // Update planet positions with the new timezone
        planetPositions = getPlanetPositionsFor(selectedDateTime.time, newTimezone)
    }

    fun fallbackToDeviceTimezone(callback: (String) -> Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            callback(TimeZone.getDefault().id)
        }
    }

    fun getTimezoneFromCoordinates(latitude: Double, longitude: Double, callback: (String) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Current timestamp in seconds
                val timestamp = System.currentTimeMillis() / 1000

                // Create URL for the API request
                val apiKey = "AIzaSyCZ0oIHzY9HILZDvSkOR9q4yQsV80Ae0s8" // Replace with your actual key
                val url = URL("https://maps.googleapis.com/maps/api/timezone/json?location=$latitude,$longitude&timestamp=$timestamp&key=$apiKey")

                // Make the request
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"

                // Read the response
                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val response = connection.inputStream.bufferedReader().use { it.readText() }
                    val jsonResponse = JSONObject(response)

                    // Get the timezone ID
                    if (jsonResponse.getString("status") == "OK") {
                        val timeZoneId = jsonResponse.getString("timeZoneId")

                        // Run on main thread to update UI
                        CoroutineScope(Dispatchers.Main).launch {
                            callback(timeZoneId)

                            // If we have a date selected, update it to use this timezone
                            if (dateTimeText.isNotBlank()) {
                                updateSelectedDateTimeWithTimezone(timeZoneId)
                            }
                        }
                    } else {
                        Log.e("Timezone", "Error: ${jsonResponse.getString("status")}")
                        fallbackToDeviceTimezone(callback)
                    }
                } else {
                    Log.e("Timezone", "HTTP Error: $responseCode")
                    fallbackToDeviceTimezone(callback)
                }
            } catch (e: Exception) {
                Log.e("Timezone", "Exception: ${e.message}")
                fallbackToDeviceTimezone(callback)
            }
        }
    }

    fun validateInputs(): Boolean {
        isNameError = name.isBlank()
        isPlaceError = place.isBlank()
        isDateTimeError = dateTimeText.isBlank()
        return !isNameError && !isPlaceError && !isDateTimeError
    }

    fun launchDateTimePicker() {
        // Use the current selected calendar for initial values
        val year = selectedDateTime.get(Calendar.YEAR)
        val month = selectedDateTime.get(Calendar.MONTH)
        val day = selectedDateTime.get(Calendar.DAY_OF_MONTH)
        val hour = selectedDateTime.get(Calendar.HOUR_OF_DAY)
        val minute = selectedDateTime.get(Calendar.MINUTE)

        DatePickerDialog(context, { _, newYear, newMonth, newDay ->
            TimePickerDialog(context, { _, newHour, newMinute ->
                // Create calendar in the birth location's timezone
                val tz = TimeZone.getTimeZone(timezone)
                val cal = Calendar.getInstance(tz)
                cal.set(newYear, newMonth, newDay, newHour, newMinute)

                // Update our state
                selectedDateTime = cal

                // Format for display, showing in birth location's timezone
                val format = SimpleDateFormat("MMM dd, yyyy 'at' h:mm a", Locale.getDefault())
                format.timeZone = tz
                dateTimeText = format.format(cal.time)

                // Update planet positions
                planetPositions = getPlanetPositionsFor(cal.time, timezone)

                isDateTimeError = false
            }, hour, minute, false).show()
        }, year, month, day).show()
    }

    fun saveChart() {
        if (!validateInputs()) {
            errorMessage = "Please fill in all required fields"
            return
        }

        try {
            isSaving = true

            // Create a calendar in the birth location's timezone
            val birthTimezone = TimeZone.getTimeZone(timezone)
            val birthCalendar = Calendar.getInstance(birthTimezone)
            birthCalendar.time = selectedDateTime.time

            // Use this timezone-aware calendar for calculations
            val sunCoord = Coordinate(CelestialObject.Planet(Planet.Sun), birthCalendar.time, timezone)
            val moonCoord = Coordinate(CelestialObject.Planet(Planet.Moon), birthCalendar.time, timezone)

            val houseCusps = HouseCuspBuilder.create(latitude, longitude, birthCalendar.time, timezone)
            val ascCusp = houseCusps.getCusp(0)
            val ascendantSign = Zodiac.signForDegree(ascCusp.longitude)

            val chart = Chart(
                id = editChart?.id ?: 0, // Keep ID if editing, or use default
                name = name,
                date = birthCalendar.time,
                birthPlace = place,
                locationName = place,
                latitude = latitude,
                longitude = longitude,
                timezone = timezone,
                planetaryPositions = getPlanetPositionsFor(birthCalendar.time, timezone),
                sunSign = sunCoord.signName,
                moonSign = moonCoord.signName,
                risingSign = ascendantSign,
                houseCusps = houseCusps
            )
            onSaveComplete(chart)
        } catch (e: Exception) {
            errorMessage = "Error calculating chart: ${e.message}"
            Log.e("ChartInputScreen", "Error saving chart", e)
            e.printStackTrace()
        } finally {
            isSaving = false
        }
    }

    // Initialize Places API
    LaunchedEffect(Unit) {
        try {
            if (!Places.isInitialized()) {
                Places.initialize(context, "AIzaSyCZ0oIHzY9HILZDvSkOR9q4yQsV80Ae0s8")
                Log.d("ChartInputScreen", "Places API initialized successfully")
            }
        } catch (e: Exception) {
            Log.e("ChartInputScreen", "Error initializing Places API: ${e.message}", e)
        }
    }

    val placeLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            try {
                val data = result.data
                if (data != null) {
                    val placeResult = Autocomplete.getPlaceFromIntent(data)
                    place = placeResult.name ?: ""
                    placeResult.latLng?.let {
                        latitude = it.latitude
                        longitude = it.longitude

                        // Get timezone from Google Time Zone API
                        getTimezoneFromCoordinates(latitude, longitude) { timeZoneId ->
                            timezone = timeZoneId
                            Log.d("ChartInputScreen", "Retrieved timezone: $timezone")
                        }
                    }
                    isPlaceError = false
                }
            } catch (e: Exception) {
                Log.e("ChartInputScreen", "Error processing place result: ${e.message}", e)
                errorMessage = "Error selecting place: ${e.message}"
            }
        }
    }
    val scrollState = rememberScrollState()

    // UI starts here
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1A1A2E))
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        Text(
            text = if (editChart == null) "Create Birth Chart" else "Edit Birth Chart",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = name,
            onValueChange = {
                name = it
                isNameError = false
            },
            label = { Text("Name", color = Color.White) },
            placeholder = { Text("e.g. Julie", color = Color.Gray) },
            textStyle = TextStyle(color = Color.White),
            isError = isNameError,
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF4A80F0),
                unfocusedBorderColor = if (isNameError) Color.Red else Color(0xFF4F4F6F),
                focusedContainerColor = Color(0xFF252542),
                unfocusedContainerColor = Color(0xFF252542)
            )
        )

        if (isNameError) Text("Name is required", color = Color.Red, fontSize = 12.sp)
        Spacer(modifier = Modifier.height(12.dp))

        Text("Birthplace", fontSize = 14.sp, color = Color.White, modifier = Modifier.padding(start = 4.dp, bottom = 4.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    try {
                        if (!Places.isInitialized()) {
                            Places.initialize(context, "AIzaSyCZ0oIHzY9HILZDvSkOR9q4yQsV80Ae0s8")
                        }

                        val fields = listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG)
                        val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields).build(context)
                        placeLauncher.launch(intent)
                    } catch (e: Exception) {
                        Log.e("ChartInputScreen", "Error launching place picker: ${e.message}", e)
                        errorMessage = "Error launching place picker: ${e.message}"
                    }
                }
        ) {
            OutlinedTextField(
                value = place,
                onValueChange = {},
                label = { Text("Select location", color = Color.Gray) },
                textStyle = TextStyle(color = Color.White),
                readOnly = true,
                isError = isPlaceError,
                modifier = Modifier.fillMaxWidth(),
                enabled = false,
                colors = OutlinedTextFieldDefaults.colors(
                    disabledTextColor = Color.White,
                    disabledBorderColor = if (isPlaceError) Color.Red else Color(0xFF4F4F6F),
                    disabledContainerColor = Color(0xFF252542),
                    disabledLabelColor = Color.Gray
                )
            )
        }

        if (isPlaceError) Text("Birthplace is required", color = Color.Red, fontSize = 12.sp)
        Spacer(modifier = Modifier.height(12.dp))

        Text("Birth Date & Time", fontSize = 14.sp, color = Color.White, modifier = Modifier.padding(start = 4.dp, bottom = 4.dp))
        Box(modifier = Modifier
            .fillMaxWidth()
            .clickable { launchDateTimePicker() }) {
            OutlinedTextField(
                value = dateTimeText,
                onValueChange = {},
                label = { Text("Select date and time", color = Color.Gray) },
                textStyle = TextStyle(color = Color.White),
                readOnly = true,
                isError = isDateTimeError,
                modifier = Modifier.fillMaxWidth(),
                enabled = false,
                colors = OutlinedTextFieldDefaults.colors(
                    disabledTextColor = Color.White,
                    disabledBorderColor = if (isDateTimeError) Color.Red else Color(0xFF4F4F6F),
                    disabledContainerColor = Color(0xFF252542),
                    disabledLabelColor = Color.Gray
                )
            )
        }
        if (isDateTimeError) Text("Birth date and time are required", color = Color.Red, fontSize = 12.sp)

        if (timezone.isNotBlank()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text("Timezone: $timezone", color = Color.LightGray)
        }

        if (errorMessage.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(errorMessage, color = Color.Red, fontSize = 14.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("Planetary Positions", fontSize = 20.sp, color = Color.White)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF202040))
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                planetPositions.forEach { line ->
                    Text(line, color = Color.White, fontSize = 16.sp, modifier = Modifier.padding(vertical = 2.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedButton(
                onClick = onCancel,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
            ) { Text("Cancel") }

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = { saveChart() },
                enabled = !isSaving,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4A80F0), contentColor = Color.White)
            ) {
                if (isSaving) CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
                else Text(if (editChart == null) "Save Chart" else "Update Chart")
            }
        }
    }
}