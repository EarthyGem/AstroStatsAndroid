package app.lilaverse.astrostatsandroid

import android.util.Log
import app.lilaverse.astrostatsandroid.model.Chart
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.util.Locale
import java.util.concurrent.TimeUnit

class SouthNodeStorytellerService(
    private val chart: Chart,
    private val chartCake: ChartCake,
    val apiKey: String,
    val provider: AIProvider,
    modelOverride: String? = null,
    private val client: OkHttpClient = defaultClient
) {

    enum class AIProvider(val displayName: String) {
        OPENAI("OpenAI"),
        CLAUDE("Claude"),
        GEMINI("Gemini"),
        GROK("Grok"),
        PERPLEXITY("Perplexity")
    }

    sealed class SouthNodeStoryError(message: String) : Exception(message) {
        object InvalidUrl : SouthNodeStoryError("Invalid API URL")
        data class ApiError(val reason: String, val statusCode: Int? = null) : SouthNodeStoryError(
            statusCode?.let { "API Error ($it): $reason" } ?: "API Error: $reason"
        )
        data class NetworkError(val exception: Throwable) : SouthNodeStoryError("Network error: ${exception.localizedMessage}")
        object DecodingError : SouthNodeStoryError("Failed to decode API response")
        object Unauthorized : SouthNodeStoryError("Unauthorized: Please check your API key")
        object RateLimited : SouthNodeStoryError("Rate limited: Please try again later")
        object ModelLoading : SouthNodeStoryError("Model is still loading. Please try again in a minute.")
    }

    private val model: String = modelOverride ?: when (provider) {
        AIProvider.OPENAI -> "gpt-4o"
        AIProvider.CLAUDE -> "claude-sonnet-4-20250514"
        AIProvider.GEMINI -> "gemini-1.5-pro"
        AIProvider.GROK -> "grok-3-latest"
        AIProvider.PERPLEXITY -> "sonar"
    }

    fun generateSouthNodeStory(
        gender: String,
        timePeriod: String,
        style: String,
        length: String,
        callback: (Result<String>) -> Unit
    ) {
        val southNodeInfo = buildSouthNodeInfo()
        val firstName = chart.name.split(" ").firstOrNull().orEmpty().ifBlank { "They" }
        val selectedTimePeriod = if (timePeriod.equals("Random", ignoreCase = true)) {
            getRandomTimePeriod()
        } else timePeriod

        val isGrok = provider == AIProvider.GROK
        val stylePrompt = if (isGrok) getShortStylePrompt(style) else getStylePrompt(style)
        val (maxTokens, lengthInstruction) = getLengthParameters(length)
        val (payload, resolvedMaxTokens) = createProviderSpecificPrompt(
            southNodeInfo = southNodeInfo,
            firstName = firstName,
            gender = gender,
            selectedTimePeriod = selectedTimePeriod,
            stylePrompt = stylePrompt,
            lengthInstruction = lengthInstruction,
            maxTokens = maxTokens
        )

        when (provider) {
            AIProvider.OPENAI -> sendOpenAIRequest(payload, resolvedMaxTokens, callback)
            AIProvider.CLAUDE -> sendClaudeRequest(payload, resolvedMaxTokens, callback)
            AIProvider.GEMINI -> sendGeminiRequest(payload, resolvedMaxTokens, callback)
            AIProvider.GROK -> sendGrokRequest(payload, resolvedMaxTokens, callback)
            AIProvider.PERPLEXITY -> sendPerplexityRequest(payload, resolvedMaxTokens, callback)
        }
    }

    fun generateSouthNodeChatStory(
        rawDetails: String,
        callback: (Result<String>) -> Unit
    ) {
        val southNodeInfo = buildSouthNodeInfo()
        val firstName = chart.name.split(" ").firstOrNull().orEmpty().ifBlank { "They" }
        val style = "Epic & Mythic"
        val length = "Medium"

        val isGrok = provider == AIProvider.GROK
        val stylePrompt = if (isGrok) getShortStylePrompt(style) else getStylePrompt(style)
        val (maxTokens, lengthInstruction) = getLengthParameters(length)

        val (payload, resolvedMaxTokens) = createProviderSpecificChatPrompt(
            southNodeInfo = southNodeInfo,
            firstName = firstName,
            gender = "Use the user’s text to decide",
            selectedTimePeriod = "Use the user’s text to decide",
            stylePrompt = stylePrompt,
            lengthInstruction = lengthInstruction,
            maxTokens = maxTokens,
            rawDetails = rawDetails
        )

        when (provider) {
            AIProvider.OPENAI -> sendOpenAIRequest(payload, resolvedMaxTokens, callback)
            AIProvider.CLAUDE -> sendClaudeRequest(payload, resolvedMaxTokens, callback)
            AIProvider.GEMINI -> sendGeminiRequest(payload, resolvedMaxTokens, callback)
            AIProvider.GROK -> sendGrokRequest(payload, resolvedMaxTokens, callback)
            AIProvider.PERPLEXITY -> sendPerplexityRequest(payload, resolvedMaxTokens, callback)
        }
    }

    private fun buildSouthNodeInfo(): String {
        val placements = chartCake.formattedNatalPlacements(chart.name)
        val planetSummary = chartCake.returnPlanets(chart.name)
        val southNode = chartCake.natalBodies.firstOrNull { it.body.keyName == "South Node" }
        val southNodeSummary = southNode?.let {
            val sign = prettySign(it.sign)
            val degree = degToDms(it.longitude)
            val house = chartCake.houseCusps.houseForLongitude(it.longitude)
            "South Node at $degree $sign in house $house"
        } ?: "South Node placement unavailable"

        return buildString {
            appendLine("South Node summary → $southNodeSummary")
            appendLine()
            appendLine("Planetary & house emphasis →")
            appendLine(planetSummary)
            appendLine()
            appendLine("Natal placements snapshot →")
            appendLine(placements)
        }
    }

    private fun getLengthParameters(length: String): Pair<Int, String> {
        return when (length.lowercase(Locale.US)) {
            "short" -> {
                val tokens = when (provider) {
                    AIProvider.OPENAI -> 1500
                    AIProvider.CLAUDE -> 1800
                    else -> 2000
                }
                tokens to "Write a concise story around 700-1000 words. Focus on key moments and essential character development."
            }
            "medium" -> {
                val tokens = when (provider) {
                    AIProvider.OPENAI -> 3000
                    AIProvider.CLAUDE -> 3500
                    else -> 4000
                }
                tokens to "Write a moderately detailed story around 1500-2000 words. Include character development and setting details."
            }
            "long" -> {
                val tokens = when (provider) {
                    AIProvider.OPENAI -> 4500
                    AIProvider.CLAUDE -> 6000
                    else -> 6500
                }
                tokens to "Write a detailed story around 3000-4000 words. Include rich character development, vivid setting details, and a more complex narrative arc."
            }
            else -> getLengthParameters("medium")
        }
    }

    private fun getStylePrompt(style: String): String {
        return when (style) {
            "Hopeful & Uplifting" -> """
                A hopeful and emotionally uplifting historical drama about resilience, redemption, and the enduring light of the human spirit. Think The Pursuit of Happyness, Hidden Figures, or Life is Beautiful.
                The protagonist faces immense challenges but continues to believe in a better future. Through courage, kindness, and community, they find strength, healing, and meaning.
                Expect heartwarming moments, inspiring victories, and a powerful sense of emotional renewal.
            """.trimIndent()
            "Dark & Gritty Drama" -> """
                A brutal, intense historical drama set in a ruthless world of power struggles, betrayals, and survival. Think Game of Thrones, Peaky Blinders, or Gangs of New York.
                The protagonist is thrust into a world where loyalty is fleeting and violence is often the only language spoken. Every choice has dire consequences.
                Expect moral ambiguity, harsh realities, and visceral storytelling.
            """.trimIndent()
            "Romantic & Tragic" -> """
                A deeply romantic and tragic historical drama, steeped in passion, longing, and fate. Think Romeo & Juliet, Titanic, or Moulin Rouge.
                Love is intense, forbidden, or doomed from the start. The protagonist is caught in an emotional storm where the heart's desires collide with the harshness of reality.
                Expect sweeping emotions, tragic sacrifices, and the devastating beauty of love lost to time.
            """.trimIndent()
            "Adventure & Survival" -> """
                A fast-paced, high-stakes survival adventure set in a time of chaos and danger. Think The Revenant, Apocalypto, or 1917.
                The protagonist is on the run, fighting for survival, or embarking on a perilous quest through uncharted lands. They must outwit, outfight, and endure whatever the world throws at them.
                Expect relentless action, visceral struggle, and a relentless fight for survival.
            """.trimIndent()
            "Philosophical & Reflective" -> """
                A deeply introspective and philosophical historical drama, where the protagonist grapples with fate, morality, and the meaning of existence. Think The Last Temptation of Christ, The Seventh Seal, or A Hidden Life.
                The story unfolds as an inner journey as much as an external one, questioning free will, destiny, and the weight of past choices.
                Expect haunting dilemmas, poetic reflections, and moments of profound realization.
            """.trimIndent()
            "Children's Story" -> """
                A heartwarming children's fable where the characters are turned into animals that reflect their personalities.
                The story should be gentle, fun, and engaging for young minds, with a clear lesson about friendship, bravery, or discovery.
                Think classic fables like The Lion King, Charlotte's Web, or Winnie the Pooh.
                Replace dark elements with lighthearted adventure.
                Use talking animals, magical forests, and playful storytelling.
                No astrology terms, no violence, no adult themes.
            """.trimIndent()
            else -> "A historical story deeply grounded in realism and emotion."
        }
    }

    private fun getShortStylePrompt(style: String): String {
        return when (style) {
            "Hopeful & Uplifting" -> "Resilience, healing, and emotional triumph"
            "Dark & Gritty Drama" -> "Dark drama, betrayal, survival"
            "Romantic & Tragic" -> "Tragic love story"
            "Adventure & Survival" -> "Survival and action"
            "Philosophical & Reflective" -> "Thoughtful and introspective"
            "Children's Story" -> "Simple moral fable with animal characters"
            else -> "Dramatic historical fiction"
        }
    }

    private fun createProviderSpecificPrompt(
        southNodeInfo: String,
        firstName: String,
        gender: String,
        selectedTimePeriod: String,
        stylePrompt: String,
        lengthInstruction: String,
        maxTokens: Int
    ): Pair<Any, Int> {
        val userMessage = """
            The main character's name is $firstName. They are $gender. Their story must feel real— as if they lived and breathed in another time.

            Here is a structured past-life theme derived from an astrological chart:

            Past-Life Themes:
            $southNodeInfo
        """.trimIndent()

        val systemPrompt = """
            You are a masterful historical storyteller. You create dark, immersive, R-rated past-life stories that feel like a prestige drama series or a historical epic in this style:

            - Style: $stylePrompt

            Length guidance:
            $lengthInstruction

            The setting must be:
            - $selectedTimePeriod

            The story must be cinematic, character-driven, and grounded in a rich historical setting. Make the story historically accurate, especially regarding gender. It should be gripping—filled with tension, moral dilemmas, and raw human emotion.

            No astrology terms. The reader does not know astrology. Transform the astrological data into a fully immersive, character-driven past-life story.

            Important Guidelines:
            - Do not use astrological terms or talk about past-life regression.
            - Do not explain symbolism—show it through the story.
            - The writing should be dark, tense, and filled with emotional weight.
            - Avoid anything soft or mystical — this is historical fiction, not fantasy.
            - Lean into R-rated drama: sex, violence, betrayal, survival, and moral dilemmas when indicated by symbolism.
            - No subheaders.
            - End the story without explaining it—let the reader feel it. If appropriate, close with an ambiguous or haunting final line.
        """.trimIndent()

        return when (provider) {
            AIProvider.OPENAI -> Pair(buildOpenAIMessages(systemPrompt, userMessage), maxTokens)
            AIProvider.CLAUDE -> Pair(Pair(systemPrompt, userMessage), maxTokens)
            AIProvider.GEMINI -> Pair(buildGeminiContents(systemPrompt, userMessage), maxTokens)
            AIProvider.GROK -> createGrokOptimizedPrompt(
                southNodeInfo = southNodeInfo,
                firstName = firstName,
                gender = gender,
                selectedTimePeriod = selectedTimePeriod,
                stylePrompt = stylePrompt,
                maxTokens = 1500
            )
            AIProvider.PERPLEXITY -> Pair(buildPerplexityPrompt(systemPrompt, userMessage, lengthInstruction), maxTokens)
        }
    }

    private fun createProviderSpecificChatPrompt(
        southNodeInfo: String,
        firstName: String,
        gender: String,
        selectedTimePeriod: String,
        stylePrompt: String,
        lengthInstruction: String,
        maxTokens: Int,
        rawDetails: String
    ): Pair<Any, Int> {
        val userMessage = """
            The main character's name is $firstName.

            The user described them and their setting as: "$rawDetails"

            Here is a structured past-life theme derived from an astrological chart:

            Past-Life Themes:
            $southNodeInfo
        """.trimIndent()

        val systemPrompt = "You are a masterful historical storyteller."

        return when (provider) {
            AIProvider.OPENAI -> Pair(buildOpenAIMessages(systemPrompt, userMessage), maxTokens)
            AIProvider.CLAUDE -> Pair(Pair(systemPrompt, userMessage), maxTokens)
            AIProvider.GEMINI -> Pair(buildGeminiContents(systemPrompt, userMessage), maxTokens)
            AIProvider.GROK -> createGrokOptimizedPrompt(
                southNodeInfo = southNodeInfo,
                firstName = firstName,
                gender = gender,
                selectedTimePeriod = selectedTimePeriod,
                stylePrompt = stylePrompt,
                maxTokens = 1500
            )
            AIProvider.PERPLEXITY -> Pair(buildPerplexityChatPrompt(rawDetails, firstName, stylePrompt, southNodeInfo, lengthInstruction), maxTokens)
        }
    }

    private fun buildOpenAIMessages(systemPrompt: String, userMessage: String): JSONArray {
        return JSONArray().apply {
            put(JSONObject().apply {
                put("role", "system")
                put("content", systemPrompt)
            })
            put(JSONObject().apply {
                put("role", "user")
                put("content", userMessage)
            })
        }
    }

    private fun buildGeminiContents(systemPrompt: String, userMessage: String): JSONArray {
        val combined = "$systemPrompt\n\n$userMessage"
        return JSONArray().apply {
            put(JSONObject().apply {
                put("role", "user")
                put("parts", JSONArray().apply {
                    put(JSONObject().apply { put("text", combined) })
                })
            })
        }
    }

    private fun buildPerplexityPrompt(systemPrompt: String, userMessage: String, lengthInstruction: String): String {
        return """
            $systemPrompt

            Write a past-life story for the character described below.

            $userMessage

            $lengthInstruction

            The story must feel cinematic, emotionally immersive, and historically grounded. Do not use astrological language. Do not summarize or explain symbols—show them through story. End with an evocative closing.
        """.trimIndent()
    }

    private fun buildPerplexityChatPrompt(
        rawDetails: String,
        firstName: String,
        stylePrompt: String,
        southNodeInfo: String,
        lengthInstruction: String
    ): String {
        return """
            You are a masterful historical storyteller.

            The user described the character and setting as: "$rawDetails"

            Write a past-life story for a character named $firstName.

            Style: $stylePrompt

            Their karmic themes are based on an astrological chart:

            $southNodeInfo

            $lengthInstruction

            The story must feel cinematic, emotionally immersive, and historically grounded. Do not use astrological language. Do not summarize or explain symbols—show them through story. End with an evocative closing.
        """.trimIndent()
    }

    private fun createGrokOptimizedPrompt(
        southNodeInfo: String,
        firstName: String,
        gender: String,
        selectedTimePeriod: String,
        stylePrompt: String,
        maxTokens: Int
    ): Pair<Any, Int> {
        val trimmedInfo = if (southNodeInfo.length > 750) southNodeInfo.take(750) else southNodeInfo
        val systemPrompt = "You are a historical fiction writer. Write a past-life story using the inputs provided."
        val userPrompt = """
            Name: $firstName
            Gender: $gender
            Time Period: $selectedTimePeriod
            Style: $stylePrompt
            Length: ~1000 words

            Karmic Themes:
            $trimmedInfo
        """.trimIndent()

        val messages = JSONArray().apply {
            put(JSONObject().apply {
                put("role", "system")
                put("content", systemPrompt)
            })
            put(JSONObject().apply {
                put("role", "user")
                put("content", userPrompt)
            })
        }

        return Pair(messages, maxTokens)
    }

    private fun sendOpenAIRequest(payload: Any, maxTokens: Int, callback: (Result<String>) -> Unit) {
        val messages = payload as? JSONArray
            ?: return callback(Result.failure(SouthNodeStoryError.ApiError("Invalid prompt format for OpenAI")))

        val url = "https://api.openai.com/v1/chat/completions"
        val json = JSONObject().apply {
            put("model", model)
            put("messages", messages)
            put("max_tokens", maxTokens)
            put("temperature", 0.7)
        }

        val request = Request.Builder()
            .url(url)
            .post(json.toString().toRequestBody(JSON_MEDIA_TYPE))
            .header("Authorization", "Bearer $apiKey")
            .header("Content-Type", "application/json")
            .build()

        enqueue(request, callback) { body ->
            val jsonResponse = JSONObject(body)
            val choices = jsonResponse.optJSONArray("choices") ?: return@enqueue Result.failure(SouthNodeStoryError.DecodingError)
            if (choices.length() == 0) return@enqueue Result.failure(SouthNodeStoryError.DecodingError)
            val content = choices.getJSONObject(0).getJSONObject("message").getString("content")
            Result.success(content.trim())
        }
    }

    private fun sendClaudeRequest(payload: Any, maxTokens: Int, callback: (Result<String>) -> Unit) {
        val pair = payload as? Pair<*, *>
            ?: return callback(Result.failure(SouthNodeStoryError.ApiError("Invalid prompt format for Claude")))
        val systemPrompt = pair.first as? String ?: return callback(Result.failure(SouthNodeStoryError.DecodingError))
        val userMessage = pair.second as? String ?: return callback(Result.failure(SouthNodeStoryError.DecodingError))

        val url = "https://api.anthropic.com/v1/messages"
        val json = JSONObject().apply {
            put("model", model)
            put("max_tokens", maxTokens)
            put("temperature", 0.7)
            put("system", systemPrompt)
            put("messages", JSONArray().apply {
                put(JSONObject().apply {
                    put("role", "user")
                    put("content", JSONArray().apply {
                        put(JSONObject().apply {
                            put("type", "text")
                            put("text", userMessage)
                        })
                    })
                })
            })
        }

        val request = Request.Builder()
            .url(url)
            .post(json.toString().toRequestBody(JSON_MEDIA_TYPE))
            .header("x-api-key", apiKey)
            .header("anthropic-version", "2023-06-01")
            .header("Content-Type", "application/json")
            .build()

        enqueue(request, callback) { body ->
            val jsonResponse = JSONObject(body)
            val contentArray = jsonResponse.optJSONArray("content") ?: return@enqueue Result.failure(SouthNodeStoryError.DecodingError)
            val story = buildString {
                for (i in 0 until contentArray.length()) {
                    val item = contentArray.optJSONObject(i)
                    if (item != null && item.optString("type") == "text") {
                        append(item.optString("text"))
                    }
                }
            }
            if (story.isBlank()) Result.failure(SouthNodeStoryError.DecodingError) else Result.success(story.trim())
        }
    }

    private fun sendGeminiRequest(payload: Any, maxTokens: Int, callback: (Result<String>) -> Unit) {
        val contents = payload as? JSONArray
            ?: return callback(Result.failure(SouthNodeStoryError.ApiError("Invalid prompt format for Gemini")))

        val json = JSONObject().apply {
            put("contents", contents)
            put("generationConfig", JSONObject().apply {
                put("temperature", 0.7)
                put("maxOutputTokens", maxTokens)
            })
        }

        val url = "https://generativelanguage.googleapis.com/v1/models/$model:generateContent?key=$apiKey"
        val request = Request.Builder()
            .url(url)
            .post(json.toString().toRequestBody(JSON_MEDIA_TYPE))
            .header("Content-Type", "application/json")
            .build()

        enqueue(request, callback) { body ->
            val jsonResponse = JSONObject(body)
            val candidates = jsonResponse.optJSONArray("candidates") ?: return@enqueue Result.failure(SouthNodeStoryError.DecodingError)
            if (candidates.length() == 0) return@enqueue Result.failure(SouthNodeStoryError.DecodingError)
            val content = candidates.getJSONObject(0).optJSONObject("content")
                ?.optJSONArray("parts")?.optJSONObject(0)?.optString("text")
            if (content.isNullOrBlank()) Result.failure(SouthNodeStoryError.DecodingError) else Result.success(content.trim())
        }
    }

    private fun sendGrokRequest(payload: Any, maxTokens: Int, callback: (Result<String>) -> Unit) {
        val messages = payload as? JSONArray
            ?: return callback(Result.failure(SouthNodeStoryError.ApiError("Invalid prompt format for Grok")))

        val json = JSONObject().apply {
            put("model", model)
            put("messages", messages)
            put("max_tokens", maxTokens)
            put("temperature", 0.7)
            put("stream", false)
        }

        val url = "https://api.x.ai/v1/chat/completions"
        val request = Request.Builder()
            .url(url)
            .post(json.toString().toRequestBody(JSON_MEDIA_TYPE))
            .header("Authorization", "Bearer $apiKey")
            .header("Content-Type", "application/json")
            .build()

        enqueue(request, callback) { body ->
            val jsonResponse = JSONObject(body)
            val choices = jsonResponse.optJSONArray("choices") ?: return@enqueue Result.failure(SouthNodeStoryError.DecodingError)
            if (choices.length() == 0) return@enqueue Result.failure(SouthNodeStoryError.DecodingError)
            val content = choices.getJSONObject(0).optJSONObject("message")?.optString("content")
            if (content.isNullOrBlank()) Result.failure(SouthNodeStoryError.DecodingError) else Result.success(content.trim())
        }
    }

    private fun sendPerplexityRequest(payload: Any, maxTokens: Int, callback: (Result<String>) -> Unit) {
        val prompt = payload as? String
            ?: return callback(Result.failure(SouthNodeStoryError.ApiError("Invalid prompt format for Perplexity")))

        val json = JSONObject().apply {
            put("model", model)
            put("messages", JSONArray().apply {
                put(JSONObject().apply {
                    put("role", "system")
                    put("content", "You are a masterful historical storyteller.")
                })
                put(JSONObject().apply {
                    put("role", "user")
                    put("content", prompt)
                })
            })
            put("max_tokens", maxTokens)
            put("temperature", 0.7)
            put("stream", false)
        }

        val url = "https://api.perplexity.ai/chat/completions"
        val request = Request.Builder()
            .url(url)
            .post(json.toString().toRequestBody(JSON_MEDIA_TYPE))
            .header("Authorization", "Bearer $apiKey")
            .header("Content-Type", "application/json")
            .build()

        enqueue(request, callback) { body ->
            val jsonResponse = JSONObject(body)
            val choices = jsonResponse.optJSONArray("choices") ?: return@enqueue Result.failure(SouthNodeStoryError.DecodingError)
            if (choices.length() == 0) return@enqueue Result.failure(SouthNodeStoryError.DecodingError)
            val content = choices.getJSONObject(0).optJSONObject("message")?.optString("content")
            if (content.isNullOrBlank()) Result.failure(SouthNodeStoryError.DecodingError) else Result.success(content.trim())
        }
    }

    private fun enqueue(
        request: Request,
        callback: (Result<String>) -> Unit,
        parser: (String) -> Result<String>
    ) {
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback(Result.failure(SouthNodeStoryError.NetworkError(e)))
            }

            override fun onResponse(call: Call, response: Response) {
                response.use { resp ->
                    val body = resp.body?.string()
                    if (!resp.isSuccessful) {
                        val error = when (resp.code) {
                            401 -> SouthNodeStoryError.Unauthorized
                            429 -> SouthNodeStoryError.RateLimited
                            503 -> SouthNodeStoryError.ModelLoading
                            else -> SouthNodeStoryError.ApiError(body ?: "Unknown error", resp.code)
                        }
                        callback(Result.failure(error))
                        return
                    }
                    if (body == null) {
                        callback(Result.failure(SouthNodeStoryError.DecodingError))
                        return
                    }
                    try {
                        callback(parser(body))
                    } catch (t: Throwable) {
                        Log.e(TAG, "Failed to parse response", t)
                        callback(Result.failure(SouthNodeStoryError.DecodingError))
                    }
                }
            }
        })
    }

    companion object {
        private const val TAG = "SouthNodeStorySvc"
        private val JSON_MEDIA_TYPE = "application/json".toMediaType()
        private val defaultClient: OkHttpClient = OkHttpClient.Builder()
            .connectTimeout(45, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(45, TimeUnit.SECONDS)
            .build()

        fun getRandomTimePeriod(): String {
            val timePeriodOptions = listOf(
                "Ancient Egypt",
                "Mesopotamia",
                "Ancient Greece",
                "Ancient Rome",
                "Persian Empire",
                "Ancient China",
                "Mesoamerica",
                "Indus Valley Civilization",
                "African Kingdoms",
                "Medieval Europe",
                "Viking Age",
                "Byzantine Empire",
                "Mongol Empire",
                "Islamic Golden Age",
                "Feudal Japan",
                "Renaissance Italy",
                "Ottoman Empire",
                "Victorian England",
                "Spanish Conquest of the Americas",
                "Age of Exploration (1400s-1600s)",
                "French Revolution & Napoleonic Era",
                "Elizabethan England",
                "Industrial Revolution",
                "Antebellum South",
                "American Wild West",
                "Russian Empire",
                "British Raj (Colonial India)",
                "World War I",
                "Roaring Twenties & Prohibition",
                "World War II",
                "Pacific Islander Cultures",
                "Pre-Columbian North American Tribes",
                "Tang/Song Dynasty China",
                "Medieval Islamic Spain (Al-Andalus)",
                "Colonial America (1600s-1700s)",
                "Ancient Persia (Achaemenid/Sassanid Dynasties)"
            )
            return timePeriodOptions.random()
        }
    }
}