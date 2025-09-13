package app.lilaverse.astrostatsandroid.chat

interface AIService {
    fun generateResponse(prompt: String, callback: (String?) -> Unit)
    val providerName: String
}