package com.io.data.model.message

import com.io.model.MessageType
import kotlinx.serialization.Serializable
import org.bson.types.ObjectId

@Serializable
data class MessagesPagingRequest(
    val page: Int,
    val pageSize: Int
) {

    fun isIncorrect(): Boolean{
        return page == -1 || pageSize == -1
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
    val text: String,
    val type: MessageTypeRequest,
    val timeSend: Long,
    val timeUpdate: Long? = null
) {

    fun isBlank(): Boolean = text.isBlank() || timeSend == 0L

    fun isBlankWithId(): Boolean = id?.isBlank() == true || isBlank()
}

@Serializable
enum class MessageTypeRequest{
    TEXT,
    IMAGE
}