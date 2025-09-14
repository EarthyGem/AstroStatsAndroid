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

    private fun sendMessage() {
        val userInput = binding.editTextMessage.text.toString().trim()
        if (userInput.isEmpty()) return

        Log.d(TAG, "=== SENDING MESSAGE ===")
        Log.d(TAG, "User input: $userInput")

        addUserMessage(userInput)
        binding.editTextMessage.text.clear()

        // Show typing indicator
        addAssistantMessage("Lila is thinking...")
        val typingMessageIndex = messages.size - 1

        try {
            Log.d(TAG, "Calling AgentManager.sendMessageToAgent...")

            AgentManager.sendMessageToAgent(
                prompt = userInput,
                chartCake = chartCake,
                userName = userName,
                chartContextType = ChartContextType.NATAL,
                chartTimeContext = ChartTimeContext.PRESENT
            ) { response ->
                Log.d(TAG, "AgentManager callback received")
                Log.d(TAG, "Response: ${response?.take(200) ?: "NULL"}")

                runOnUiThread {
                    try {
                        // Remove typing indicator
                        if (messages.size > typingMessageIndex && !messages[typingMessageIndex].isUser) {
                            messages.removeAt(typingMessageIndex)
                            messageAdapter.notifyItemRemoved(typingMessageIndex)
                        }

                        val finalResponse = response ?: "I'm having trouble processing your request. Please try again or check the logs for details."
                        addAssistantMessage(finalResponse)

                    } catch (e: Exception) {
                        Log.e(TAG, "Error handling AgentManager response", e)
                        addAssistantMessage("Error handling response: ${e.message}")
                    }
                }
            }

        } catch (e: Exception) {
            Log.e(TAG, "Error calling AgentManager", e)
            // Remove typing indicator
            if (messages.size > typingMessageIndex && !messages[typingMessageIndex].isUser) {
                messages.removeAt(typingMessageIndex)
                messageAdapter.notifyItemRemoved(typingMessageIndex)
            }
            addAssistantMessage("Error calling AgentManager: ${e.message}")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "ChatActivity destroyed")
    }
}