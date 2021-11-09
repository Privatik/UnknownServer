package com.io.repository.chat

import com.io.model.Chat

interface ChatRepository {

    suspend fun findChatWithCurrentUser(userId: List<String>): Boolean

    suspend fun createChat(chat: Chat): Chat

    suspend fun getChat(id: String): Chat?
}