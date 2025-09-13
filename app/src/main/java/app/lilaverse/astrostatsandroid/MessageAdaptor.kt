package app.lilaverse.astrostatsandroid.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import app.lilaverse.astrostatsandroid.databinding.ItemMessageAssistantBinding
import app.lilaverse.astrostatsandroid.databinding.ItemMessageUserBinding

class MessageAdapter(private val messages: List<ChatMessage>) :
    RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = if (viewType == 1) {
            ItemMessageUserBinding.inflate(inflater, parent, false)
        } else {
            ItemMessageAssistantBinding.inflate(inflater, parent, false)
        }
        return MessageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(messages[position])
    }

    override fun getItemCount() = messages.size

    override fun getItemViewType(position: Int): Int {
        return if (messages[position].isUser) 1 else 0
    }

    class MessageViewHolder(private val binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(message: ChatMessage) {
            when (binding) {
                is ItemMessageUserBinding -> {
                    binding.textMessage.text = message.text
                }
                is ItemMessageAssistantBinding -> {
                    binding.textMessage.text = message.text
                }
            }
        }
    }
}