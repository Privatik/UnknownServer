package com.io.repository.chat

import com.io.model.Chat
import com.io.model.User
import org.litote.kmongo.contains
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.insertOne
import org.litote.kmongo.eq

class ChatRepositoryImpl(
    db: CoroutineDatabase
):ChatRepository {

    private val chats = db.getCollection<Chat>()

    override suspend fun findChatWithCurrentUser(userId: List<String>): Boolean {
        return false
    }

    override suspend fun createChat(chat: Chat): Chat {
        val chatN = chats.insertOne(chat)
        return chat
    }

    override suspend fun getChat(id: String): Chat? = chats.findOne(Chat::id eq id)
}