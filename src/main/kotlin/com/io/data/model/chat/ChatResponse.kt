package com.io.data.model.chat

import com.io.data.model.message.MessageResponse
import com.io.model.Message
import kotlinx.serialization.Serializable
import org.bson.types.ObjectId

@Serializable
data class ChatResponse(
    val id: String,
    val firstCompanionId: String,
    val secondCompanionId: String,
    val lastMessage: MessageResponse?
)