package com.io.model

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class Chat(
    @BsonId
    val id: String = ObjectId().toString(),
    val companionsId: List<String> = emptyList(),
    val lastMessage: Message? = null
)
