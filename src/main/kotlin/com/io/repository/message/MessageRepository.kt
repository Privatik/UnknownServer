package com.io.repository.message

import com.io.model.Message

interface MessageRepository {

    suspend fun sendMessage(message: Message): Message

    suspend fun updateMessage(id: String, text: String, timeUpdate: Long): Message?

    suspend fun deleteMessage(id: String): Boolean

    suspend fun getMessages(chatId: String, page: Int, pageSize: Int): List<Message>
}