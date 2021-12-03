package com.io.controller.message

import com.io.data.mapper.toModel
import com.io.data.model.message.MessageRequest
import com.io.data.model.message.MessagesPagingRequest
import com.io.model.Message
import com.io.repository.chat.ChatRepository
import com.io.repository.message.MessageRepository
import com.io.repository.user.UserRepository
import com.io.util.ExceptionMessage

class MessageControllerImpl(
    private val messageRepository: MessageRepository,
    private val chatRepository: ChatRepository,
    private val userRepository: UserRepository
): MessageController {

    override suspend fun sendMessage(userId: String, message: MessageRequest): Pair<Message?, ExceptionMessage?> {
        if (message.isBlank()){
            return Pair(null, ExceptionMessage.EXCEPTION_MESSAGE_FIELD_EMPTY)
        }
        else if (chatRepository.getChat(message.chatId) == null){
            return Pair(null, ExceptionMessage.EXCEPTION_CHAT_DONT_EXIST)
        }
        else if (userRepository.getUserById(userId) == null){
            return Pair(null, ExceptionMessage.EXCEPTION_USER_DONT_EXIST)
        }
        val messageModel = messageRepository.sendMessage(message.toModel(userId))

        return Pair(messageModel, null)
    }

    override suspend fun updateMessage(userId: String, message: MessageRequest): Pair<Message?, ExceptionMessage?> {
        if (message.isBlankWithId()){
            return Pair(null, ExceptionMessage.EXCEPTION_MESSAGE_FIELD_EMPTY)
        }
        else if (chatRepository.getChat(message.chatId) == null){
            return Pair(null, ExceptionMessage.EXCEPTION_CHAT_DONT_EXIST)
        }
        else if (userRepository.getUserById(userId) == null){
            return Pair(null, ExceptionMessage.EXCEPTION_USER_DONT_EXIST)
        }
        else if (message.timeUpdate == null){
            return Pair(null, ExceptionMessage.EXCEPTION_MESSAGE_DONT_TIMEUPDATE)
        }

        val messageModel = messageRepository.updateMessage(
            message.id.toString(),
            message.text,
            message.timeUpdate
        )
            ?: kotlin.run {
                return Pair(null, ExceptionMessage.EXCEPTION_MESSAGE_DONT_EXIST)
            }

        return Pair(messageModel, null)
    }

    override suspend fun deleteMessage(userId: String, message: MessageRequest): Pair<Boolean, ExceptionMessage?> {
        if (message.isBlankWithId()){
            return Pair(false, ExceptionMessage.EXCEPTION_MESSAGE_FIELD_EMPTY)
        }
        else if (chatRepository.getChat(message.chatId) == null){
            return Pair(false, ExceptionMessage.EXCEPTION_CHAT_DONT_EXIST)
        }
        else if (userRepository.getUserById(userId) == null){
            return Pair(false, ExceptionMessage.EXCEPTION_USER_DONT_EXIST)
        }

        val isDelete = messageRepository.deleteMessage(message.id!!)

        return Pair(isDelete, if (isDelete) null else ExceptionMessage.EXCEPTION_MESSAGE_DONT_EXIST)
    }

    override suspend fun getMessages(
        userId: String,
        messagePaging: MessagesPagingRequest
    ): Pair<List<Message>?, ExceptionMessage?> {
        return if (chatRepository.getChat(messagePaging.chatId) == null){
            Pair(null, ExceptionMessage.EXCEPTION_CHAT_DONT_EXIST)
        } else if (userRepository.getUserById(userId) == null) {
            Pair(null, ExceptionMessage.EXCEPTION_USER_DONT_EXIST)
        } else {
            if (messagePaging.page < 0 || messagePaging.pageSize < 1){
                Pair(null, ExceptionMessage.EXCEPTION_MESSAGE_DONT_CORRECT_DATA)
            } else {
                val list = messageRepository.getMessages(
                    messagePaging.chatId,
                    messagePaging.page,
                    messagePaging.pageSize
                )
                Pair(list, null)
            }
        }
    }
}