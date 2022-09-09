package com.io.data.model.message

import com.io.model.MessageType
import kotlinx.serialization.Serializable
import org.bson.types.ObjectId

@Serializable
data class MessagesPagingRequest(
    val chatId: String,
    val page: Int,
    val pageSize: Int
) {

    fun isIncorrect(): Boolean{
        return chatId.isBlank() || page == -1 || pageSize == -1
    }
}

@Serializable
data class MessagesById(
    val id: String
) {
    fun isIncorrect(): Boolean{
        return id.isBlank()
    }
}

@Serializable
data class MessageRequest (
    val id: String? = null,
    val chatId: String,
    val text: String,
    val type: MessageTypeRequest,
    val timeSend: Long,
    val timeUpdate: Long? = null
) {

    fun isBlank(): Boolean =
        chatId.isBlank() || text.isBlank() || timeSend == 0L

    fun isBlankWithId(): Boolean = id?.isBlank() == true || isBlank()
}

@Serializable
enum class MessageTypeRequest{
    TEXT,
    IMAGE
}