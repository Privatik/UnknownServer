package com.io.repository.chat

import com.io.model.Chat

interface ChatRepository {

    suspend fun createChat(chat: Chat): Chat

    suspend fun getChat(id: String): Chat?

    suspend fun getChat(firstUserId: String, secondUserId: String): Chat?

    suspend fun getChats(listChatsId: Set<String>, page: Int, pageSize: Int): List<Chat>
}