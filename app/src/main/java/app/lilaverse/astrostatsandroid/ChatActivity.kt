package app.lilaverse.astrostatsandroid.chat

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import app.lilaverse.astrostatsandroid.databinding.ActivityChatBinding
import app.lilaverse.astrostatsandroid.BuildConfig
import app.lilaverse.astrostatsandroid.ChartCake

import app.lilaverse.astrostatsandroid.chat.ChartContextType
import app.lilaverse.astrostatsandroid.chat.ChartTimeContext
import java.util.Locale

class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    private lateinit var messageAdapter: MessageAdapter
    private val messages = mutableListOf<ChatMessage>()
    private lateinit var chartCake: ChartCake
    private lateinit var userName: String
    private var isRelocatedChat: Boolean = false
    private var relocatedChartCake: ChartCake? = null
    private var selectedLocationName: String? = null
    private var originalLocationName: String? = null
    private var selectedLatitude: Double? = null
    private var selectedLongitude: Double? = null
    private var originalLatitude: Double? = null
    private var originalLongitude: Double? = null

    companion object {
        private const val TAG = "ChatActivity"
        const val EXTRA_IS_RELOCATED = "extra_is_relocated"
        const val EXTRA_RELOCATED_CHART = "extra_relocated_chart"
        const val EXTRA_LOCATION_NAME = "extra_location_name"
        const val EXTRA_LOCATION_LATITUDE = "extra_location_latitude"
        const val EXTRA_LOCATION_LONGITUDE = "extra_location_longitude"
        const val EXTRA_ORIGINAL_LOCATION_NAME = "extra_original_location_name"
        const val EXTRA_ORIGINAL_LATITUDE = "extra_original_latitude"
        const val EXTRA_ORIGINAL_LONGITUDE = "extra_original_longitude"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d(TAG, "=== ChatActivity Starting ===")

        try {
            @Suppress("DEPRECATION")
            chartCake = intent.getParcelableExtra<ChartCake>("chartCake")
                ?: error("ChartCake missing")
            userName = intent.getStringExtra("userName") ?: "User"

            isRelocatedChat = intent.getBooleanExtra(EXTRA_IS_RELOCATED, false)
            relocatedChartCake = intent.getParcelableExtra(EXTRA_RELOCATED_CHART)
            selectedLocationName = intent.getStringExtra(EXTRA_LOCATION_NAME)
            originalLocationName = intent.getStringExtra(EXTRA_ORIGINAL_LOCATION_NAME)
            if (intent.hasExtra(EXTRA_LOCATION_LATITUDE) && intent.hasExtra(EXTRA_LOCATION_LONGITUDE)) {
                val lat = intent.getDoubleExtra(EXTRA_LOCATION_LATITUDE, Double.NaN)
                val lon = intent.getDoubleExtra(EXTRA_LOCATION_LONGITUDE, Double.NaN)
                if (!lat.isNaN() && !lon.isNaN()) {
                    selectedLatitude = lat
                    selectedLongitude = lon
                }
            }
            if (intent.hasExtra(EXTRA_ORIGINAL_LATITUDE) && intent.hasExtra(EXTRA_ORIGINAL_LONGITUDE)) {
                val lat = intent.getDoubleExtra(EXTRA_ORIGINAL_LATITUDE, Double.NaN)
                val lon = intent.getDoubleExtra(EXTRA_ORIGINAL_LONGITUDE, Double.NaN)
                if (!lat.isNaN() && !lon.isNaN()) {
                    originalLatitude = lat
                    originalLongitude = lon
                }
            }

            Log.d(TAG, "Successfully loaded: userName=$userName")
            Log.d(TAG, "ChartCake available: ${chartCake != null}")

        } catch (e: Exception) {
            Log.e(TAG, "Error loading intent data", e)
            // Continue with dummy data for testing
            userName = "Test User"
        }

        setupRecyclerView()
        setupInputField()

        addSystemMessage(buildWelcomeMessage())
        emitChartDiagnosticsIfDebug()
    }




    private fun tryBackupService() {
        Log.d(TAG, "Trying backup AI service...")
        addSystemMessage("Trying backup service...")

        val aiManager = AIServiceManager.getInstance()
        aiManager.setService(ServiceType.OPENAI)

        aiManager.generateResponse("Hello from backup service") { response ->
            runOnUiThread {
                if (response != null) {
                    addSystemMessage("✅ Backup service working!")
                } else {
                    addSystemMessage("❌ Backup service also failed")
                    // Try Gemini
                    tryGeminiService()
                }
            }
        }
    }

    private fun tryGeminiService() {
        Log.d(TAG, "Trying Gemini service...")
        addSystemMessage("Trying Gemini service...")

        val aiManager = AIServiceManager.getInstance()
        aiManager.setService(ServiceType.GEMINI)

        aiManager.generateResponse("Hello from Gemini") { response ->
            runOnUiThread {
                if (response != null) {
                    addSystemMessage("✅ Gemini service working!")
                } else {
                    addSystemMessage("❌ All AI services failed. Check API keys and internet connection.")
                }
            }
        }
    }

    private fun setupRecyclerView() {
        messageAdapter = MessageAdapter(messages)
        binding.recyclerViewMessages.apply {
            adapter = messageAdapter
            layoutManager = LinearLayoutManager(this@ChatActivity)
        }
    }

    private fun setupInputField() {
        binding.buttonSend.setOnClickListener {
            sendMessage()
        }

        binding.editTextMessage.setOnEditorActionListener { _, _, _ ->
            sendMessage()
            true
        }
    }

    private fun addSystemMessage(message: String) {
        Log.d(TAG, "System: $message")
        messages.add(ChatMessage(message, false))
        messageAdapter.notifyItemInserted(messages.size - 1)
        scrollToBottom()
    }

    private fun addUserMessage(message: String) {
        Log.d(TAG, "User: $message")
        messages.add(ChatMessage(message, true))
        messageAdapter.notifyItemInserted(messages.size - 1)
        scrollToBottom()
    }

    private fun addAssistantMessage(message: String) {
        Log.d(TAG, "Assistant: ${message.take(100)}...")
        messages.add(ChatMessage(message, false))
        messageAdapter.notifyItemInserted(messages.size - 1)
        scrollToBottom()
    }

    private fun scrollToBottom() {
        if (messages.isNotEmpty()) {
            binding.recyclerViewMessages.scrollToPosition(messages.size - 1)
        }
    }

// Add debugging to your ChatActivity sendMessage() method

    private fun sendMessage() {
        val userInput = binding.editTextMessage.text.toString().trim()
        if (userInput.isEmpty()) return

        Log.d("ChatActivity", "=== DEBUGGING CHARTCAKE PASSING ===")
        Log.d("ChatActivity", "ChartCake available: ${::chartCake.isInitialized}")

        if (::chartCake.isInitialized) {
            Log.d("ChatActivity", "ChartCake bodies count: ${chartCake.bodies.size}")
            Log.d("ChatActivity", "ChartCake sample planet: ${chartCake.bodies.firstOrNull()?.body?.keyName ?: "None"}")
            // Test: Try to get planet info from ChartCake
            val planetInfo = chartCake.returnPlanets(userName)
            Log.d("ChatActivity", "Planet info length: ${planetInfo.length}")
            Log.d("ChatActivity", "Planet info preview: ${planetInfo.take(200)}")
            // Test: Try to get house activations
            val houseActivations = chartCake.formattedAllHouseActivationsBlockV2()
            Log.d("ChatActivity", "House activations length: ${houseActivations?.length ?: 0}")
            Log.d("ChatActivity", "House activations preview: ${houseActivations?.take(200) ?: "NULL"}")
        } else {
            Log.e("ChatActivity", "ChartCake is NOT initialized!")
        }

        addUserMessage(userInput)
        binding.editTextMessage.text.clear()

        // Show typing indicator
        addAssistantMessage("Lila is thinking...")
        val typingMessageIndex = messages.size - 1

        try {
            Log.d("ChatActivity", "Calling AgentManager with:")
            Log.d("ChatActivity", "- userName: $userName")
            Log.d("ChatActivity", "- chartCake: ${if (::chartCake.isInitialized) "AVAILABLE" else "NULL"}")
            val preparedPrompt = if (isRelocatedChat) buildAstrocartographyPrompt(userInput) else userInput
            AgentManager.sendMessageToAgent(
                prompt = preparedPrompt,
                chartCake = chartCake,  // ← Make sure this is the correct ChartCake instance
                userName = userName,
                otherChart = if (isRelocatedChat) relocatedChartCake else null,
                chartContextType = if (isRelocatedChat) ChartContextType.RELOCATED else ChartContextType.NATAL,
                chartTimeContext = ChartTimeContext.PRESENT
            ) { response ->
                Log.d("ChatActivity", "AgentManager response received: ${response?.take(100) ?: "NULL"}")

                runOnUiThread {
                    try {
                        // Remove typing indicator
                        if (messages.size > typingMessageIndex && !messages[typingMessageIndex].isUser) {
                            messages.removeAt(typingMessageIndex)
                            messageAdapter.notifyItemRemoved(typingMessageIndex)
                        }

                        val finalResponse = response ?: "I'm having trouble processing your request. Please try again."
                        addAssistantMessage(finalResponse)

                    } catch (e: Exception) {
                        Log.e("ChatActivity", "Error handling response", e)
                        addAssistantMessage("Error handling response: ${e.message}")
                    }
                }
            }

        } catch (e: Exception) {
            Log.e("ChatActivity", "Error calling AgentManager", e)
            // Remove typing indicator
            if (messages.size > typingMessageIndex && !messages[typingMessageIndex].isUser) {
                messages.removeAt(typingMessageIndex)
                messageAdapter.notifyItemRemoved(typingMessageIndex)
            }
            addAssistantMessage("Error sending message: ${e.message}")
        }
    }
    private fun buildWelcomeMessage(): String {
        if (isRelocatedChat) {
            val relocatedLabel = coordinateLabel(selectedLatitude, selectedLongitude, selectedLocationName) ?: "this location"
            val originLabel = coordinateLabel(originalLatitude, originalLongitude, originalLocationName) ?: "your birth location"
            return """
            🌍 Hi! I'm Lila, your astrology guide for **$relocatedLabel
          Let's explore how relocating from **$originLabel** weaves with your natal chart and what archetypal field is awakened here.
             Tell me what draws you to this place—moving, traveling, or just curious—so I can tailor the astrocartography insight.
            """.trimIndent()
        }
            // Add a test message showing chart data
        return "Hello $userName! I'm Lila, your astrology assistant. Ask me anything about this birth chart!"
    }
            // Show a sample of the chart data

    private fun coordinateLabel(lat: Double?, lon: Double?, fallback: String?): String? {
        val named = fallback?.takeIf { it.isNotBlank() }
        return named ?: formatCoordinate(lat, lon)
    }

    private fun formatCoordinate(lat: Double?, lon: Double?): String? {
        if (lat == null || lon == null) return null
        return String.format(Locale.getDefault(), "%.2f°, %.2f°", lat, lon)
    }

    private fun buildAstrocartographyPrompt(userInput: String): String {
        val originLabel = coordinateLabel(originalLatitude, originalLongitude, originalLocationName) ?: "Original location"
        val relocatedLabel = coordinateLabel(selectedLatitude, selectedLongitude, selectedLocationName) ?: "Selected location"
        val originCoords = formatCoordinate(originalLatitude, originalLongitude)?.let { " ($it)" } ?: ""
        val relocatedCoords = formatCoordinate(selectedLatitude, selectedLongitude)?.let { " ($it)" } ?: ""

        val natalSummary = runCatching { chartCake.returnPlanets(userName) }.getOrElse { "Natal chart data unavailable." }
        val relocatedSummary = relocatedChartCake?.let { runCatching { it.returnPlanets(userName) }.getOrNull() }
            ?: "Relocated chart data unavailable."

        return buildString {
            appendLine("🔭 ASTROCARTOGRAPHY CHAT (FORREST METHOD)")
            appendLine()
            appendLine("ORIGINAL LOCATION: $originLabel$originCoords")
            appendLine("RELOCATED LOCATION: $relocatedLabel$relocatedCoords")
            appendLine()
            appendLine("USER QUESTION:")
            appendLine(userInput)
            appendLine()
            appendLine("🌍 CONTEXT:")
            appendLine("This conversation explores your natal chart through the lens of relocation—treat the relocated map as a **permanent transit** anchored to place.")
            appendLine()
            appendLine("📊 CHART DATA:")
            appendLine("• NATAL SNAPSHOT:")
            appendLine(natalSummary)
            appendLine()
            appendLine("• RELOCATED SNAPSHOT:")
            appendLine(relocatedSummary)
            appendLine()
            appendLine("FRAMEWORK:")
            appendLine("- Lead with the natal story, then describe how the relocated chart modifies its expression.")
            appendLine("- Offer both the high side and the low side potentials for every planetary line discussed.")
            appendLine("- Invite the user to share or clarify their intention for this place if it is not already clear.")
            appendLine("- Emphasize choice, consciousness, and agency—\"your choices determine the outcome.\"")
            appendLine("- Acknowledge that astrocartography maps highlight angular emphasis and that a full relocated chart adds nuance.")
        }.trim()
    }

    private fun emitChartDiagnosticsIfDebug() {
        if (!BuildConfig.DEBUG) {
            return
        }

        if (!::chartCake.isInitialized) {
            Log.w(TAG, "ChartCake not initialized; skipping debug diagnostics")
            return
        }

        Log.d(TAG, "=== CHARTCAKE DEBUG ===")

        val planets = chartCake.returnPlanets(userName)
        Log.d(TAG, "Planets data: ${planets.take(500)}")

        val activations = chartCake.formattedAllHouseActivationsBlockV2()
        Log.d(TAG, "House activations: ${activations?.take(500) ?: "NULL"}")

        Log.d(TAG, "Chart loaded with ${chartCake.bodies.size} celestial bodies")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "ChatActivity destroyed")
    }
}