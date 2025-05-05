package app.lilaverse.astrostatsandroid

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import app.lilaverse.astrostatsandroid.HouseCuspBuilder
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.lilaverse.astrostatsandroid.model.Chart
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ChartInputScreen(
    onSaveComplete: (Chart) -> Unit,
    onCancel: () -> Unit
) {
    val context = LocalContext.current

    val defaultCalendar = Calendar.getInstance(TimeZone.getTimeZone("America/Los_Angeles")).apply {
        set(1977, Calendar.MAY, 21, 13, 57)
    }

    var name by remember { mutableStateOf("Ricky") }
    var place by remember { mutableStateOf("San Diego, CA") }
    var dateTimeText by remember {
        mutableStateOf(
            SimpleDateFormat("MMM dd, yyyy 'at' h:mm a", Locale.getDefault()).format(defaultCalendar.time)
        )
    }
    var selectedDateTime by remember { mutableStateOf(defaultCalendar) }
    var latitude by remember { mutableStateOf(32.7157) }
    var longitude by remember { mutableStateOf(-117.1611) }
    var timezone by remember { mutableStateOf("America/Los_Angeles") }

    var isNameError by remember { mutableStateOf(false) }
    var isPlaceError by remember { mutableStateOf(false) }
    var isDateTimeError by remember { mutableStateOf(false) }
    var isSaving by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    var planetPositions by remember { mutableStateOf(getPlanetPositionsFor(selectedDateTime.time)) }

    fun validateInputs(): Boolean {
        isNameError = name.isBlank()
        isPlaceError = place.isBlank()
        isDateTimeError = dateTimeText.isBlank()
        return !isNameError && !isPlaceError && !isDateTimeError
    }

    fun getTimezoneFromLocation(lat: Double, lng: Double) {
        val tz = TimeZone.getDefault()
        timezone = tz.id
    }

    val placeLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            val placeResult = Autocomplete.getPlaceFromIntent(data!!)
            place = placeResult.name ?: ""
            placeResult.latLng?.let {
                latitude = it.latitude
                longitude = it.longitude
                getTimezoneFromLocation(latitude, longitude)
            }
            isPlaceError = false
        }
    }

    // In your ChartInputScreen.kt file, modify the saveChart() function:
    fun saveChart() {
        if (!validateInputs()) {
            errorMessage = "Please fill in all required fields"
            return
        }

        try {
            // Calculate planetary coordinates
            val sunCoord = Coordinate(CelestialObject.Planet(Planet.Sun), selectedDateTime.time)
            val moonCoord = Coordinate(CelestialObject.Planet(Planet.Moon), selectedDateTime.time)

            // Fallback: hardcoded rising sign as placeholder
            val risingSign = "nil"

            // Create the chart
            val chart = Chart(
                name = name,
                date = selectedDateTime.time,
                birthPlace = place,
                locationName = place,
                latitude = latitude,
                longitude = longitude,
                timezone = timezone,
                planetaryPositions = planetPositions,
                sunSign = sunCoord.signName,
                moonSign = moonCoord.signName,
                risingSign = risingSign,
                houseCusps = HouseCuspBuilder.create(latitude, longitude, selectedDateTime.time)

            )

            onSaveComplete(chart)

        } catch (e: Exception) {
            errorMessage = "Error calculating chart: ${e.message}"
            e.printStackTrace()
        }
    }

    fun launchDateTimePicker() {
        val now = Calendar.getInstance()
        DatePickerDialog(context, { _, year, month, day ->
            TimePickerDialog(context, { _, hour, minute ->
                selectedDateTime.set(year, month, day, hour, minute)
                val format = SimpleDateFormat("MMM dd, yyyy 'at' h:mm a", Locale.getDefault())
                dateTimeText = format.format(selectedDateTime.time)
                isDateTimeError = false
                planetPositions = getPlanetPositionsFor(selectedDateTime.time)
            }, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), false).show()
        }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH)).show()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1A1A2E))
            .padding(16.dp)
    ) {
        Text("Create Birth Chart", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.White)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = name,
            onValueChange = {
                name = it
                isNameError = false
            },
            label = { Text("Name", color = Color.White) },
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
            modifier = Modifier.fillMaxWidth().clickable {
                val fields = listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG)
                val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields).build(context)
                placeLauncher.launch(intent)
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
        Box(modifier = Modifier.fillMaxWidth().clickable { launchDateTimePicker() }) {
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
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF202040))
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                planetPositions.forEach { line ->
                    Text(line, color = Color.White, fontSize = 16.sp, modifier = Modifier.padding(vertical = 2.dp))
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
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
                else Text("Save Chart")
            }
        }
    }
}