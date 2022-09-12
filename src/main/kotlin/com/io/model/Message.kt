package com.io.model

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import javax.management.monitor.StringMonitor

data class Message(
    @BsonId
    val id: String = ObjectId().toString(),
    val userId: String,
    val text: String,
    val type: MessageType,
    val timeSend: Long,
    val timeUpdate: Long? = null
)

enum class MessageType{
    TEXT,
    IMAGE
}
