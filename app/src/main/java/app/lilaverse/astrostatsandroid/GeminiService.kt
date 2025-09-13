package app.lilaverse.astrostatsandroid.chat

import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class GeminiService(private val apiKey: String) : AIService {
    override val providerName = "Gemini"
    private val baseUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent"

    override fun generateResponse(prompt: String, callback: (String?) -> Unit) {
        val client = OkHttpClient()
        val json = JSONObject().apply {
            put("contents", JSONArray().apply {
                put(JSONObject().apply {
                    put("parts", JSONArray().apply {
                        put(JSONObject().apply {
                            put("text", prompt)
                        })
                    })
                })
            })
        }

        val request = Request.Builder()
            .url("$baseUrl?key=$apiKey")
            .post(json.toString().toRequestBody("application/json".toMediaType()))
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
                    val content = jsonResponse.getJSONArray("candidates")
                        .getJSONObject(0)
                        .getJSONObject("content")
                        .getJSONArray("parts")
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