/*
 * AIService.kt
 *
 * Created on 2026-04-10
 *
 * Copyright (C) 2026 Volkswagen AG, All rights reserved.
 */

package com.simon.cat.services

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.intellij.openapi.diagnostic.Logger
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody

data class ChatMessage(
    val role: String,
    val content: String
                      )

data class ChatRequest(
    val model: String,
    val messages: List<ChatMessage>,
    val max_tokens: Int = 500,
    val temperature: Float = 0.7f
                      )

data class ChatResponse(
    val id: String?,
    val `object`: String?,
    val created: Long?,
    val choices: List<Choice>?
                       ) {
    data class Choice(
        val index: Int?,
        val message: ChatMessage?,
        val finish_reason: String?
                     )
}

class AIService {
    private val logger = Logger.getInstance(AIService::class.java)
    private val gson = Gson()
    private val client = OkHttpClient()

    fun sendMessage(apiKey: String, endpoint: String, model: String, message: String): String? {
        return sendMessageWithHistory(
            apiKey, endpoint, model, listOf(
                ChatMessage("system", "You are a helpful coding assistant. Keep responses concise and friendly."),
                ChatMessage("user", message)
                                           )
                                     )
        return try {
            val requestBody = createRequestBody(model, message)

            val request = Request.Builder()
                .url(endpoint)
                .post(requestBody)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer $apiKey")
                .build()

            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    logger.warn("AI API call failed: ${response.code}")
                    return getFallbackResponse(message)
                }

                val responseBody = response.body?.string() ?: ""
                parseResponse(responseBody)
            }
        } catch (e: Exception) {
            logger.error("Error calling AI service", e)
            getFallbackResponse(message)
        }
    }

    private fun createRequestBody(model: String, message: String): RequestBody {
        val messages = listOf(
            ChatMessage("system", "You are a helpful coding assistant. Keep responses concise and friendly."),
            ChatMessage("user", message)
                             )

        val requestBody = ChatRequest(
            model = model,
            messages = messages
                                     )

        val jsonBody = gson.toJson(requestBody)
        return RequestBody.create("application/json; charset=utf-8".toMediaType(), jsonBody)
    }

    private fun parseResponse(responseBody: String): String? {
        return try {
            val jsonObject = gson.fromJson(responseBody, JsonObject::class.java)
            val choices = jsonObject.getAsJsonArray("choices")

            if (choices != null && choices.size() > 0) {
                val firstChoice = choices[0].asJsonObject
                val message = firstChoice.getAsJsonObject("message")
                message?.get("content")?.asString
            } else {
                // Try alternative response format
                extractContentFromJson(jsonObject)
            }
        } catch (e: Exception) {
            logger.warn("Failed to parse AI response", e)
            null
        }
    }

    private fun extractContentFromJson(jsonObject: JsonObject): String? {
        // Try common alternative response formats
        val alternatives = listOf(
            "generated_text",
            "text",
            "output",
            "result",
            "response"
                                 )

        for (key in alternatives) {
            val content = jsonObject.get(key)
            if (content != null && !content.isJsonNull) {
                return content.asString
            }
        }

        return null
    }

    fun sendMessageWithHistory(apiKey: String, endpoint: String, model: String, messages: List<ChatMessage>): String? {
        return try {
            val requestBody = createRequestBody(model, messages)

            val request = Request.Builder()
                .url(endpoint)
                .post(requestBody)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer $apiKey")
                .build()

            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    logger.warn("AI API call failed: ${response.code}")
                    return generateSmartFallbackResponse(messages.lastOrNull()?.content ?: "")
                }

                val responseBody = response.body?.string() ?: ""
                parseResponse(responseBody)
            }
        } catch (e: Exception) {
            logger.error("Error calling AI service", e)
            generateSmartFallbackResponse(messages.lastOrNull()?.content ?: "")
        }
    }

    private fun createRequestBody(model: String, messages: List<ChatMessage>): RequestBody {
        val requestBody = ChatRequest(
            model = model,
            messages = messages
                                     )

        val jsonBody = gson.toJson(requestBody)
        return RequestBody.create("application/json; charset=utf-8".toMediaType(), jsonBody)
    }

    private fun getFallbackResponse(userMessage: String): String {
        // Return a simple fallback response when AI service is unavailable
        return generateSmartFallbackResponse(userMessage)
    }

    fun generateSmartFallbackResponse(userMessage: String): String {
        val lowerMessage = userMessage.lowercase()

        return when {
            lowerMessage.contains("hello") || lowerMessage.contains("hi")               -> {
                "Hello! I'm your AI coding companion. Even without my full AI capabilities, I can still help you with programming questions and motivation! 😊"
            }

            lowerMessage.contains("help")                                               -> {
                "I'm here to help! While my AI features might be offline, I can still provide basic programming assistance and motivational quotes."
            }

            lowerMessage.contains("java")                                               -> {
                "Java is a versatile language! Key principles include: 1) Write clean, readable code 2) Follow object-oriented design 3) Handle exceptions properly 4) Use appropriate design patterns. Keep practicing! 💻"
            }

            lowerMessage.contains("kotlin")                                             -> {
                "Kotlin advantages: 1) Null safety built-in 2) Extension functions 3) Lambda support 4) Interoperability with Java. Start with basics and gradually explore advanced features!"
            }

            lowerMessage.contains("debug") || lowerMessage.contains("bug")              -> {
                "Debugging tips: 1) Read error messages carefully 2) Check variable values 3) Use print statements or debugger 4) Test small pieces at a time 5) Don't panic - every programmer debugs!"
            }

            lowerMessage.contains("motivation") || lowerMessage.contains("inspiration") -> {
                "Remember: Every expert was once a beginner. Your journey is unique and valuable. Celebrate small wins, learn from failures, and keep moving forward. You're capable of amazing things! 🌟"
            }

            lowerMessage.contains("tired") || lowerMessage.contains("exhausted")        -> {
                "Coding burnout happens! Take a break: 1) Step away from the screen 2) Stretch or walk around 3) Hydrate 4) Come back with fresh eyes. Your well-being matters more than any deadline! ☕"
            }

            lowerMessage.contains("confused") || lowerMessage.contains("stuck")         -> {
                "Feeling stuck is normal! Here's what helped me: 1) Break problem into smaller parts 2) Search for similar examples 3) Explain concept out loud 4) Take a break and return 5) Ask for help when needed!"
            }

            else                                                                        -> {
                "That's an interesting question! While I'm not using my full AI brain right now, programming is all about problem-solving. Think step by step, research thoroughly, and trust your abilities. What specific aspect would you like to explore?"
            }
        }
    }
}
