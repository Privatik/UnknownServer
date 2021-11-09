package com.io.data.model.chat

import kotlinx.serialization.Serializable

@Serializable
data class ChatRequest(
    val userId: String,
    val companionsId: List<String>
)
