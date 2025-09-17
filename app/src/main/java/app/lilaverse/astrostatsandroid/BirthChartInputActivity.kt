package app.lilaverse.astrostatsandroid

import android.app.*
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

class BirthChartInputActivity : AppCompatActivity() {

    private val TAG = "BirthChartInputActivity" // Tag for logging

    private lateinit var nameField: EditText
    private lateinit var placeField: TextView
    private lateinit var dateButton: Button
    private lateinit var timeButton: Button
    private lateinit var debugText: TextView  // Added for debugging messages

    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    private var timezone: String = ""
    private var birthDate = Calendar.getInstance()

    // Place selection launcher
    private val placeLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
     //   Log.d(TAG, "Place launcher result received: ${result.resultCode}")
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            if (data != null) {
                try {
                    val place = Autocomplete.getPlaceFromIntent(data)
                    placeField.text = place.name ?: ""
                    place.latLng?.let {
                        latitude = it.latitude
                        longitude = it.longitude
                        getTimezoneFromLocation(latitude, longitude)
                        updateDebugText("Place selected: ${place.name}, Lat: $latitude, Long: $longitude")
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Error processing place result: ${e.message}", e)
                    updateDebugText("Error processing place result: ${e.message}")
                }
            } else {
                Log.e(TAG, "Place result data is null")
                updateDebugText("Place result data is null")
            }
        } else {
         //   Log.d(TAG, "Place selection cancelled or failed: ${result.resultCode}")
            updateDebugText("Place selection cancelled or failed: ${result.resultCode}")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // Log.d(TAG, "onCreate called")

        // Initialize Places API with extensive error logging
        try {
            // Check if Places is already initialized
            val isInitialized = try {
                Places.isInitialized()
            } catch (e: Exception) {
                Log.e(TAG, "Error checking Places initialization: ${e.message}", e)
                false
            }

          //  Log.d(TAG, "Places API initialized status: $isInitialized")

            if (!isInitialized) {
                // Replace with your actual API key here for testing
                val apiKey = "AIzaSyCZ0oIHzY9HILZDvSkOR9q4yQsV80Ae0s8"
              //  Log.d(TAG, "Initializing Places API with key length: ${apiKey.length}")

                try {
                    Places.initialize(applicationContext, apiKey)
                 //   Log.d(TAG, "Places API initialization successful")
                } catch (e: Exception) {
                    Log.e(TAG, "Error during Places.initialize(): ${e.message}", e)
                    // Show error to user
                    Toast.makeText(this, "Failed to initialize Places API: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Fatal error initializing Places API: ${e.message}", e)
            Toast.makeText(this, "Fatal error initializing Places API: ${e.message}", Toast.LENGTH_LONG).show()
        }

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(40, 40, 40, 40)
        }

        val titleText = TextView(this).apply {
            text = "Enter Birth Chart Info"
            textSize = 24f
            setPadding(0, 0, 0, 30)
        }

        nameField = EditText(this).apply {
            hint = "Name"
            setPadding(20, 30, 20, 30)
        }

        // Using a Button instead of TextView for better clickability
        placeField = Button(this).apply {
            text = "Select Place of Birth"
            setPadding(20, 30, 20, 30)
            setOnClickListener {
           //     Log.d(TAG, "Place field clicked")
                updateDebugText("Place field clicked, launching picker...")
                launchPlacePicker()
            }
        }

        dateButton = Button(this).apply {
            text = "Select Date"
            setOnClickListener {
           //     Log.d(TAG, "Date button clicked")
                showDatePicker()
            }
        }

        timeButton = Button(this).apply {
            text = "Select Time"
            setOnClickListener {
             //   Log.d(TAG, "Time button clicked")
                showTimePicker()
            }
        }

        // Debug text view for showing messages
        debugText = TextView(this).apply {
            text = "Debug messages will appear here"
            textSize = 12f
            setPadding(20, 20, 20, 20)
            setBackgroundColor(0x22FFFF00) // Light yellow background
        }

        val saveButton = Button(this).apply {
            text = "Save"
            setPadding(30, 30, 30, 30)
            setOnClickListener {
           //     Log.d(TAG, "Save button clicked")
                val chartName = nameField.text.toString()
                val place = placeField.text.toString()
                val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US)
                val formattedDateTime = formatter.format(birthDate.time)

                val resultIntent = Intent().apply {
                    putExtra("chartName", chartName)
                    putExtra("chartPlace", place)
                    putExtra("chartDateTime", formattedDateTime)
                    putExtra("latitude", latitude)
                    putExtra("longitude", longitude)
                    putExtra("timezone", timezone)
                }
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
        }

        layout.apply {
            addView(titleText)
            addView(TextView(this@BirthChartInputActivity).apply {
                text = "Name"
                setPadding(0, 10, 0, 5)
            })
            addView(nameField)
            addView(TextView(this@BirthChartInputActivity).apply {
                text = "Birthplace"
                setPadding(0, 20, 0, 5)
            })
            addView(placeField)
            addView(dateButton)
            addView(timeButton)
            addView(debugText) // Add debug text view
            addView(saveButton)
        }

        setContentView(layout)
        updateDebugText("Activity created. Places API initialized: ${Places.isInitialized()}")
    }

    private fun updateDebugText(message: String) {
     //   Log.d(TAG, message)
        runOnUiThread {
            debugText.text = message
        }
    }

    private fun launchPlacePicker() {
        try {
            updateDebugText("Attempting to launch place picker...")

            // Check if Places is initialized
            if (!Places.isInitialized()) {
                val message = "Google Places API not initialized"
                Log.e(TAG, message)
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                updateDebugText(message)
                return
            }

            val fields = listOf(
                Place.Field.ID,
                Place.Field.NAME,
                Place.Field.LAT_LNG,
                Place.Field.ADDRESS
            )

            try {
              //  Log.d(TAG, "Building Autocomplete intent")
                val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
                    .build(this)

            //    Log.d(TAG, "Launching place picker activity")
                placeLauncher.launch(intent)
                updateDebugText("Place picker launched successfully")

            } catch (e: Exception) {
                val message = "Error creating autocomplete intent: ${e.message}"
                Log.e(TAG, message, e)
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                updateDebugText(message)
            }
        } catch (e: Exception) {
            val message = "Fatal error launching place picker: ${e.message}"
            Log.e(TAG, message, e)
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            updateDebugText(message)
            e.printStackTrace()
        }
    }

    // Change this method to properly handle timezones
    private fun getTimezoneFromLocation(lat: Double, lng: Double) {
        // Instead of using device timezone
        // val tz = TimeZone.getDefault()
        // timezone = tz.id

        // Use a proper timezone lookup API
        try {
            // Use the Google Time Zone API
            val timestamp = System.currentTimeMillis() / 1000
            val url = URL("https://maps.googleapis.com/maps/api/timezone/json?location=$lat,$lng&timestamp=$timestamp&key=YOUR_API_KEY")

            // This is a simplified example - in production code, use proper async IO
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val connection = url.openConnection() as HttpURLConnection
                    connection.requestMethod = "GET"

                    val response = connection.inputStream.bufferedReader().use { it.readText() }
                    val jsonResponse = JSONObject(response)

                    if (jsonResponse.getString("status") == "OK") {
                        timezone = jsonResponse.getString("timeZoneId")
                   //     Log.d(TAG, "Retrieved timezone for location: $timezone")

                        // Adjust the birth date to use the correct timezone
                        updateBirthDateTimezone()
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Error getting timezone: ${e.message}", e)
                    // Fallback to device timezone as last resort
                    timezone = TimeZone.getDefault().id
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error initiating timezone request: ${e.message}", e)
            timezone = TimeZone.getDefault().id
        }
    }

    // Add this method to update the birth date when timezone changes
    private fun updateBirthDateTimezone() {
        // Create a new calendar with the birth location's timezone
        val locationCalendar = Calendar.getInstance(TimeZone.getTimeZone(timezone))

        // Get year, month, day, hour, minute from the current birthDate (which is in device timezone)
        val deviceCalendar = Calendar.getInstance()
        deviceCalendar.time = birthDate.time

        // Set the same date and time in the location calendar
        locationCalendar.set(Calendar.YEAR, deviceCalendar.get(Calendar.YEAR))
        locationCalendar.set(Calendar.MONTH, deviceCalendar.get(Calendar.MONTH))
        locationCalendar.set(Calendar.DAY_OF_MONTH, deviceCalendar.get(Calendar.DAY_OF_MONTH))
        locationCalendar.set(Calendar.HOUR_OF_DAY, deviceCalendar.get(Calendar.HOUR_OF_DAY))
        locationCalendar.set(Calendar.MINUTE, deviceCalendar.get(Calendar.MINUTE))

        // Update birthDate
        birthDate = locationCalendar
    }

    private fun showDatePicker() {
        val year = birthDate.get(Calendar.YEAR)
        val month = birthDate.get(Calendar.MONTH)
        val day = birthDate.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            R.style.SpinnerDatePickerDialog,
            { _, y, m, d ->
                birthDate.set(Calendar.YEAR, y)
                birthDate.set(Calendar.MONTH, m)
                birthDate.set(Calendar.DAY_OF_MONTH, d)
                val dateStr = "Date: ${y}-${m + 1}-${d}"
                dateButton.text = dateStr
                Log.d(TAG, "Date selected: $dateStr")
                updateDebugText("Date selected: $dateStr")
            },
            year,
            month,
            day
        ).apply {
            datePicker.apply {
                calendarViewShown = false
                spinnersShown = true
            }
        }

        datePickerDialog.show()
    }

    private fun showTimePicker() {
        val hour = birthDate.get(Calendar.HOUR_OF_DAY)
        val minute = birthDate.get(Calendar.MINUTE)

        TimePickerDialog(this, { _, h, m ->
            birthDate.set(Calendar.HOUR_OF_DAY, h)
            birthDate.set(Calendar.MINUTE, m)
            val timeStr = "Time: %02d:%02d".format(h, m)
            timeButton.text = timeStr
         //   Log.d(TAG, "Time selected: $timeStr")
            updateDebugText("Time selected: $timeStr")
        }, hour, minute, true).show()
    }

    override fun onDestroy() {
        super.onDestroy()
    //    Log.d(TAG, "onDestroy called")
    }
}