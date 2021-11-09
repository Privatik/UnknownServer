package com.io.data.model.message

import com.io.model.MessageType
import kotlinx.serialization.Serializable
import org.bson.types.ObjectId

@Serializable
data class MessageResponse(
    val id: String,
    val userId: String,
    val chatId: String,
    val text: String,
    val type: MessageType,
    val timeSend: String,
    val timeUpdate: String?
)
