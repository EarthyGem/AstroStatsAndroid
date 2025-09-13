package app.lilaverse.astrostatsandroid.chat

import android.os.Bundle
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        @Suppress("DEPRECATION")
        chartCake = intent.getParcelableExtra<ChartCake>("chartCake")
            ?: error("ChartCake missing")
        userName = intent.getStringExtra("userName") ?: "User"

        setupRecyclerView()
        setupInputField()
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
    }

    private fun sendMessage() {
        val userInput = binding.editTextMessage.text.toString().trim()
        if (userInput.isEmpty()) return

        messages.add(ChatMessage(userInput, true))
        messageAdapter.notifyItemInserted(messages.size - 1)
        binding.recyclerViewMessages.scrollToPosition(messages.size - 1)
        binding.editTextMessage.text.clear()

        AgentManager.sendMessageToAgent(
            prompt = userInput,
            chartCake = chartCake,
            userName = userName,
            chartContextType = ChartContextType.NATAL,
            chartTimeContext = ChartTimeContext.PRESENT
        ) { response ->
            runOnUiThread {
                messages.add(ChatMessage(response ?: "Sorry, I couldn't process that.", false))
                messageAdapter.notifyItemInserted(messages.size - 1)
                binding.recyclerViewMessages.scrollToPosition(messages.size - 1)
            }
        }
    }
}