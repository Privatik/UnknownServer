package com.io.repository.chat

import com.io.model.Chat
import com.io.model.User
import org.bson.types.ObjectId
import org.litote.kmongo.contains
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.insertOne
import org.litote.kmongo.eq

class ChatRepositoryImpl(
    db: CoroutineDatabase
):ChatRepository {

    private val chats = db.getCollection<Chat>()

    override suspend fun createChat(chat: Chat): Chat {
        chats.insertOne(chat)
        return chat
    }

    override suspend fun getChat(id: String): Chat? = chats.findOne(Chat::id eq id)

    override suspend fun getChat(firstUserId: String, secondUserId: String): Chat? =
        chats.findOne(Chat::firstCompanionId eq firstUserId, Chat::secondCompanionId eq secondUserId)
}