package app.lilaverse.astrostatsandroid.chat

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import app.lilaverse.astrostatsandroid.databinding.ActivityChatBinding
import app.lilaverse.astrostatsandroid.ChartCake

class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    private lateinit var messageAdapter: MessageAdapter
    private val messages = mutableListOf<ChatMessage>()
    private lateinit var chartCake: ChartCake
    private lateinit var userName: String

    companion object {
        private const val TAG = "ChatActivity"
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

            Log.d(TAG, "Successfully loaded: userName=$userName")
            Log.d(TAG, "ChartCake available: ${chartCake != null}")

        } catch (e: Exception) {
            Log.e(TAG, "Error loading intent data", e)
            // Continue with dummy data for testing
            userName = "Test User"
        }

        setupRecyclerView()
        setupInputField()
        testChartCakeData()


        // Add welcome message
        addSystemMessage("Hello $userName! I'm Lila, your astrology assistant. Ask me anything about this birth chart!")
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
            Log.d("ChatActivity", "ChartCake bodies count: ${chartCake.bodies?.size ?: 0}")
            Log.d("ChatActivity", "ChartCake sample planet: ${chartCake.bodies?.firstOrNull()?.body?.keyName ?: "None"}")

            // Test: Try to get planet info from ChartCake
            val planetInfo = chartCake.returnPlanets()
            Log.d("ChatActivity", "Planet info length: ${planetInfo?.length ?: 0}")
            Log.d("ChatActivity", "Planet info preview: ${planetInfo?.take(200) ?: "NULL"}")

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

            AgentManager.sendMessageToAgent(
                prompt = userInput,
                chartCake = chartCake,  // ← Make sure this is the correct ChartCake instance
                userName = userName,
                chartContextType = ChartContextType.NATAL,
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
    private fun testChartCakeData() {
        if (::chartCake.isInitialized) {
            Log.d("ChatActivity", "=== CHARTCAKE TEST ===")

            // Test basic ChartCake methods
            val planets = chartCake.returnPlanets()
            Log.d("ChatActivity", "Planets data: ${planets?.take(500) ?: "NULL"}")

            val activations = chartCake.formattedAllHouseActivationsBlockV2()
            Log.d("ChatActivity", "House activations: ${activations?.take(500) ?: "NULL"}")

            // Add a test message showing chart data
            addSystemMessage("Chart loaded! Found ${chartCake.bodies?.size ?: 0} celestial bodies.")

            // Show a sample of the chart data
            planets?.let {
                val preview = it.take(200) + if (it.length > 200) "..." else ""
                addSystemMessage("Sample chart data: $preview")
            }
        } else {
            addSystemMessage("ERROR: ChartCake not initialized!")
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "ChatActivity destroyed")
    }
}