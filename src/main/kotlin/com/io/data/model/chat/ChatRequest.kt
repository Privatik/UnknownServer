package com.io.data.model.chat

import kotlinx.serialization.Serializable

@Serializable
data class ChatRequest(
    val firstCompanionId: String,
    val secondCompanionId: String
) {

    fun isBlank() = firstCompanionId.isBlank() || secondCompanionId.isBlank()
}

@Serializable
data class ChatIdRequest(
    val id: String
)

@Serializable
data class ChatsPagingRequest(
    val userId: String,
    val page: Int,
    val pageSize: Int
)
