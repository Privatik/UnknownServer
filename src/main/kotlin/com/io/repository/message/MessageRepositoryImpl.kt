package com.io.repository.message

import com.io.model.Chat
import com.io.model.Message
import com.io.model.User
import org.bson.types.ObjectId
import org.litote.kmongo.`in`
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.setValue

class MessageRepositoryImpl(
    db: CoroutineDatabase
): MessageRepository {

    private val messages = db.getCollection<Message>()

    override suspend fun sendMessage(message: Message): Message {
        val newMessage = message.copy(timeSend = System.currentTimeMillis())
        messages.insertOne(newMessage)
        return newMessage
    }

    override suspend fun updateMessage(id: String, text: String): Message? {
        messages.updateOneById(id, update = setValue(Message::timeUpdate, System.currentTimeMillis()))
        messages.updateOneById(id, update = setValue(Message::text,text))
        return messages.findOneById(id)
    }

    override suspend fun deleteMessage(id: String): Boolean {
        return messages.deleteOneById(id).wasAcknowledged()
    }

    override suspend fun getMessages(page: Int, pageSize: Int): List<Message> =
        messages
            .find()
            .descendingSort(Message::timeSend)
            .skip(pageSize * page)
            .limit(pageSize)
            .toList()

    override suspend fun getMessage(id: String): Message? {
        return messages.findOne(Message::id eq ObjectId(id))
    }
}