package app.lilaverse.astrostatsandroid.chat

import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class ClaudeService(private val apiKey: String) : AIService {
    override val providerName = "Claude"
    private val baseUrl = "https://api.anthropic.com/v1/messages"

    override fun generateResponse(prompt: String, callback: (String?) -> Unit) {
        val client = OkHttpClient()
        val json = JSONObject().apply {
            put("model", "claude-3-sonnet-20240229")
            put("max_tokens", 1000)
            put("messages", JSONArray().apply {
                put(JSONObject().apply {
                    put("role", "user")
                    put("content", prompt)
                })
            })
        }

        val request = Request.Builder()
            .url(baseUrl)
            .post(json.toString().toRequestBody("application/json".toMediaType()))
            .header("x-api-key", apiKey)
            .header("anthropic-version", "2023-06-01")
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
                    val content = jsonResponse.getJSONArray("content")
                        .getJSONObject(0)
                        .getString("text")
                    callback(content)
                } catch (e: Exception) {
                    callback(null)
                }
            }
        })
    }
}