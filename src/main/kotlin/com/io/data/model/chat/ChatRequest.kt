package com.io.data.model.chat

import kotlinx.serialization.Serializable

@Serializable
data class ChatRequest(
    val companionsId: List<String>
)
