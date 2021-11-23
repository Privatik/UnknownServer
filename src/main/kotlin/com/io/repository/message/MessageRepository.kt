package com.io.repository.message

import com.io.model.Message

interface MessageRepository {

    suspend fun sendMessage(message: Message): Message

    suspend fun updateMessage(id: String, text: String, timeUpdate: String): Message?

    suspend fun deleteMessage(id: String): Boolean
}