package com.io.model

import org.bson.types.ObjectId

data class Chat(
    val id: String = ObjectId().toString(),
    val messages: List<Message> = emptyList()
)
