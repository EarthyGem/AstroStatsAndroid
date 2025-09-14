package app.lilaverse.astrostatsandroid.chat

import android.util.Log
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

// Clean AIService interface
interface AIService {
    val providerName: String
    fun generateResponse(prompt: String, callback: (String?) -> Unit)
}

// Claude Service Implementation
// Updated Claude Service with correct current model names

class ClaudeService(private val apiKey: String) : AIService {
    override val providerName = "Claude"
    private val baseUrl = "https://api.anthropic.com/v1/messages"

    companion object {
        private const val TAG = "ClaudeService"
    }

    override fun generateResponse(prompt: String, callback: (String?) -> Unit) {
        Log.d(TAG, "ClaudeService.generateResponse called with updated models")

        val client = OkHttpClient.Builder()
            .connectTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
            .readTimeout(60, java.util.concurrent.TimeUnit.SECONDS)
            .writeTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
            .build()

        val json = JSONObject().apply {
            // Use current Claude 4 model names - try these in order of preference:
            put("model", "claude-sonnet-4-20250514")          // Claude Sonnet 4 (current)
            // Alternative models if the above doesn't work:
            // put("model", "claude-opus-4-1-20250805")        // Claude Opus 4.1 (most powerful)
            // put("model", "claude-3-5-sonnet-20240620")     // Claude 3.5 Sonnet (fallback)
            // put("model", "claude-3-haiku-20240307")        // Claude 3 Haiku (fastest/cheapest)

            put("max_tokens", 2000)
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

        Log.d(TAG, "Sending request with model: claude-sonnet-4-20250514")

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e(TAG, "Network failure", e)
                callback("Network error: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                Log.d(TAG, "Response code: ${response.code}")

                try {
                    val responseBody = response.body?.string()
                    Log.d(TAG, "Full response: $responseBody")

                    if (response.isSuccessful && responseBody != null) {
                        val jsonResponse = JSONObject(responseBody)
                        val content = jsonResponse.getJSONArray("content")
                            .getJSONObject(0)
                            .getString("text")
                        Log.d(TAG, "Success! Response length: ${content.length}")
                        callback(content)
                    } else {
                        Log.e(TAG, "API error: ${response.code} - $responseBody")

                        // Enhanced error parsing
                        val errorDetails = try {
                            if (responseBody != null) {
                                val errorJson = JSONObject(responseBody)
                                val errorObj = errorJson.optJSONObject("error")
                                val message = errorObj?.optString("message") ?: "Unknown error"
                                val type = errorObj?.optString("type") ?: "unknown_error"
                                "Type: $type, Message: $message"
                            } else "No response body"
                        } catch (e: Exception) {
                            "Parse error: $responseBody"
                        }

                        val userMessage = when (response.code) {
                            400 -> "Bad request - check model name or request format"
                            401 -> "Invalid API key"
                            403 -> "API key lacks permissions"
                            404 -> "Model not found - trying wrong model name"
                            429 -> "Rate limited - try again later"
                            else -> "Claude API error (${response.code})"
                        }

                        callback("$userMessage\nDetails: $errorDetails")
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Response parsing error", e)
                    callback("Response parsing error: ${e.message}")
                }
            }
        })
    }
}
// OpenAI Service Implementation
class OpenAIService(private val apiKey: String) : AIService {
    override val providerName = "OpenAI"
    private val baseUrl = "https://api.openai.com/v1/chat/completions"

    companion object {
        private const val TAG = "OpenAIService"
    }

    override fun generateResponse(prompt: String, callback: (String?) -> Unit) {
        Log.d(TAG, "OpenAIService.generateResponse called")

        val client = OkHttpClient.Builder()
            .connectTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
            .readTimeout(60, java.util.concurrent.TimeUnit.SECONDS)
            .writeTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
            .build()

        val json = JSONObject().apply {
            put("model", "gpt-3.5-turbo")
            put("max_tokens", 2000)
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
            .header("Authorization", "Bearer $apiKey")
            .header("Content-Type", "application/json")
            .build()

        Log.d(TAG, "Sending request to OpenAI API...")

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e(TAG, "OpenAI API network failure", e)
                callback("OpenAI network error: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                Log.d(TAG, "OpenAI API response code: ${response.code}")

                try {
                    val responseBody = response.body?.string()

                    if (response.isSuccessful && responseBody != null) {
                        val jsonResponse = JSONObject(responseBody)
                        val content = jsonResponse.getJSONArray("choices")
                            .getJSONObject(0)
                            .getJSONObject("message")
                            .getString("content")
                        Log.d(TAG, "Successfully parsed OpenAI response")
                        callback(content)
                    } else {
                        Log.e(TAG, "OpenAI API error: ${response.code}")
                        callback("OpenAI API error (${response.code})")
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Error parsing OpenAI response", e)
                    callback("Error parsing OpenAI response: ${e.message}")
                }
            }
        })
    }
}

// Gemini Service Implementation
class GeminiService(private val apiKey: String) : AIService {
    override val providerName = "Gemini"
    private val baseUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent"

    companion object {
        private const val TAG = "GeminiService"
    }

    override fun generateResponse(prompt: String, callback: (String?) -> Unit) {
        Log.d(TAG, "GeminiService.generateResponse called")

        val client = OkHttpClient.Builder()
            .connectTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
            .readTimeout(60, java.util.concurrent.TimeUnit.SECONDS)
            .writeTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
            .build()

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

        Log.d(TAG, "Sending request to Gemini API...")

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e(TAG, "Gemini API network failure", e)
                callback("Gemini network error: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                Log.d(TAG, "Gemini API response code: ${response.code}")

                try {
                    val responseBody = response.body?.string()

                    if (response.isSuccessful && responseBody != null) {
                        val jsonResponse = JSONObject(responseBody)
                        val content = jsonResponse.getJSONArray("candidates")
                            .getJSONObject(0)
                            .getJSONObject("content")
                            .getJSONArray("parts")
                            .getJSONObject(0)
                            .getString("text")
                        Log.d(TAG, "Successfully parsed Gemini response")
                        callback(content)
                    } else {
                        Log.e(TAG, "Gemini API error: ${response.code}")
                        callback("Gemini API error (${response.code})")
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Error parsing Gemini response", e)
                    callback("Error parsing Gemini response: ${e.message}")
                }
            }
        })
    }
}

// Clean AIServiceManager
class AIServiceManager private constructor() {
    companion object {
        private const val TAG = "AIServiceManager"

        @Volatile
        private var INSTANCE: AIServiceManager? = null

        fun getInstance(): AIServiceManager {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: AIServiceManager().also { INSTANCE = it }
            }
        }
    }

    private var currentService: AIService = ClaudeService(APIKeys.ANTHROPIC)

    fun setService(serviceType: ServiceType) {
        Log.d(TAG, "Switching to service: $serviceType")
        currentService = when (serviceType) {
            ServiceType.CLAUDE -> ClaudeService(APIKeys.ANTHROPIC)
            ServiceType.OPENAI -> OpenAIService(APIKeys.OPENAI)
            ServiceType.GEMINI -> GeminiService(APIKeys.GEMINI)
        }
        Log.d(TAG, "Current service is now: ${currentService.providerName}")
    }

    fun generateResponse(prompt: String, callback: (String?) -> Unit) {
        Log.d(TAG, "AIServiceManager.generateResponse called")
        Log.d(TAG, "Using service: ${currentService.providerName}")
        Log.d(TAG, "Prompt length: ${prompt.length}")
        currentService.generateResponse(prompt, callback)
    }
}

enum class ServiceType {
    CLAUDE, OPENAI, GEMINI
}