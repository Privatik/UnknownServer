package com.io.repository.message

import com.io.model.Chat
import com.io.model.Message
import com.io.model.User
import org.litote.kmongo.`in`
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.setValue

class MessageRepositoryImpl(
    db: CoroutineDatabase
): MessageRepository {

    private val messages = db.getCollection<Message>()

    override suspend fun sendMessage(message: Message): Message {
        messages.insertOne(message)
        return message
    }

    override suspend fun updateMessage(id: String, text: String, timeUpdate: Long): Message? {
        messages.updateOneById(id, update = setValue(Message::timeUpdate, timeUpdate))
        messages.updateOneById(id, update = setValue(Message::text,text))
        return messages.findOneById(id)
    }

    override suspend fun deleteMessage(id: String): Boolean {
        return messages.deleteOneById(id).wasAcknowledged()
    }

    override suspend fun getMessages(chatId: String, page: Int, pageSize: Int): List<Message> =
        messages
            .find(Message::chatId eq chatId)
            .descendingSort(Message::timeSend)
            .skip(pageSize * page)
            .limit(pageSize)
            .toList()

    override suspend fun getMessage(id: String): Message? {
        return messages.findOne(id)
    }
}