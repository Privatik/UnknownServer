package com.io.data.model.message

import com.io.model.MessageType
import kotlinx.serialization.Serializable
import org.bson.types.ObjectId

@Serializable
data class MessageRequest (
    val id: String? = null,
    val userId: String,
    val chatId: String,
    val text: String,
    val type: MessageTypeRequest,
    val timeSend: String,
    val timeUpdate: String?
) {

    fun isBlank(): Boolean =
        userId.isBlank() || chatId.isBlank() || text.isBlank() || timeSend.isBlank()

    fun isBlankWithId(): Boolean = id?.isBlank() == true || isBlank()
}

@Serializable
enum class MessageTypeRequest{
    TEXT,
    IMAGE
}