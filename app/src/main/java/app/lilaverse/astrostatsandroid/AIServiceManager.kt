package app.lilaverse.astrostatsandroid.chat

class AIServiceManager private constructor() {
    companion object {
        @Volatile
        private var INSTANCE: AIServiceManager? = null

        fun getInstance(): AIServiceManager {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: AIServiceManager().also { INSTANCE = it }
            }
        }
    }

    private var currentService: AIService = ClaudeService(APIKeys.ANTHROPIC_KEY)

    fun setService(serviceType: ServiceType) {
        currentService = when (serviceType) {
            ServiceType.CLAUDE -> ClaudeService(APIKeys.ANTHROPIC_KEY)
            ServiceType.OPENAI -> OpenAIService(APIKeys.OPENAI_KEY)
            ServiceType.GEMINI -> GeminiService(APIKeys.GEMINI_KEY)
        }
    }

    fun generateResponse(prompt: String, callback: (String?) -> Unit) {
        currentService.generateResponse(prompt, callback)
    }
}

enum class ServiceType {
    CLAUDE, OPENAI, GEMINI
}