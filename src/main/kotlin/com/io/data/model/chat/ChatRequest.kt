package com.io.data.model.chat

import kotlinx.serialization.Serializable

@Serializable
data class ChatRequest(
    val companionId: String
)

@Serializable
data class ChatIdRequest(
    val id: String
)

@Serializable
data class ChatsPagingRequest(
    val page: Int,
    val pageSize: Int
)
