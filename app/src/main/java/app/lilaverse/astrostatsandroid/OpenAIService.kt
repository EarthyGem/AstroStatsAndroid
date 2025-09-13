package app.lilaverse.astrostatsandroid.chat

import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class OpenAIService(private val apiKey: String) : AIService {
    override val providerName = "OpenAI"
    private val baseUrl = "https://api.openai.com/v1/chat/completions"

    override fun generateResponse(prompt: String, callback: (String?) -> Unit) {
        val client = OkHttpClient()
        val json = JSONObject().apply {
            put("model", "gpt-4o-mini")
            put("messages", JSONArray().apply {
                put(JSONObject().apply {
                    put("role", "system")
                    put("content", "You are Lila, an astrology assistant.")
                })
                put(JSONObject().apply {
                    put("role", "user")
                    put("content", prompt)
                })
            })
            put("max_tokens", 1000)
            put("temperature", 0.3)
        }

        val request = Request.Builder()
            .url(baseUrl)
            .post(json.toString().toRequestBody("application/json".toMediaType()))
            .header("Authorization", "Bearer $apiKey")
            .header("Content-Type", "application/json")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback(null)
            }

            override fun onResponse(call: Call, response: Response) {
                try {
                    val responseBody = response.body?.string()
                    val jsonResponse = JSONObject(responseBody ?: "")
                    val content = jsonResponse.getJSONArray("choices")
                        .getJSONObject(0)
                        .getJSONObject("message")
                        .getString("content")
                    callback(content)
                } catch (e: Exception) {
                    callback(null)
                }
            }
        })
    }
}