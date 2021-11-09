package com.io.model

import org.bson.types.ObjectId
import javax.management.monitor.StringMonitor

data class Message(
    val id: String = ObjectId().toString(),
    val userId: String,
    val chatId: String,
    val text: String,
    val type: MessageType,
    val timeSend: String,
    val timeUpdate: String? = null
)

enum class MessageType{
    TEXT,
    IMAGE
}
