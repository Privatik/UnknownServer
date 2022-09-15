package com.io.controller.message

import com.io.data.mapper.toModel
import com.io.data.model.message.MessageRequest
import com.io.data.model.message.MessagesById
import com.io.data.model.message.MessagesPagingRequest
import com.io.model.Message
import com.io.repository.chat.ChatRepository
import com.io.repository.message.MessageRepository
import com.io.repository.user.UserRepository
import com.io.util.ExceptionMessage
import com.io.util.Response

class MessageControllerImpl(
    private val messageRepository: MessageRepository,
    private val chatRepository: ChatRepository,
    private val userRepository: UserRepository
): MessageController {

    override suspend fun sendMessage(userId: String, message: MessageRequest): Response<Message> {
        if (message.isBlank()){
            return Response(null, ExceptionMessage.EXCEPTION_MESSAGE_FIELD_EMPTY)
        }
        else if (userRepository.getUserById(userId) == null){
            return Response(null, ExceptionMessage.EXCEPTION_USER_DONT_EXIST)
        }
        val messageModel = messageRepository.sendMessage(message.toModel(userId))

        return Response(messageModel, null)
    }

    override suspend fun updateMessage(userId: String, message: MessageRequest): Response<Message> {
        if (message.isBlankWithId()){
            return Response(null, ExceptionMessage.EXCEPTION_MESSAGE_FIELD_EMPTY)
        }
        else if (userRepository.getUserById(userId) == null){
            return Response(null, ExceptionMessage.EXCEPTION_USER_DONT_EXIST)
        }

        val messageModel = messageRepository.updateMessage(
            message.id.toString(),
            message.text,
        )
            ?: kotlin.run {
                return Response(null, ExceptionMessage.EXCEPTION_MESSAGE_DONT_EXIST)
            }

        return Response(messageModel, null)
    }

    override suspend fun deleteMessage(userId: String, message: MessageRequest): Response<String> {
        if (message.isBlankWithId()){
            return Response(null, ExceptionMessage.EXCEPTION_MESSAGE_FIELD_EMPTY)
        }
        else if (userRepository.getUserById(userId) == null){
            return Response(null, ExceptionMessage.EXCEPTION_USER_DONT_EXIST)
        }

        val isDelete = messageRepository.deleteMessage(message.id!!)

        return Response(message.id, if (isDelete) null else ExceptionMessage.EXCEPTION_MESSAGE_DONT_EXIST)
    }

    override suspend fun getMessages(
        userId: String,
        messagePaging: MessagesPagingRequest
    ): Response<List<Message>> {
        return if (userRepository.getUserById(userId) == null) {
            Response(null, ExceptionMessage.EXCEPTION_USER_DONT_EXIST)
        } else {
            if (messagePaging.page < 0 || messagePaging.pageSize < 1){
                Response(null, ExceptionMessage.EXCEPTION_MESSAGE_DONT_CORRECT_DATA)
            } else {
                val list = messageRepository.getMessages(
                    messagePaging.page,
                    messagePaging.pageSize
                )
                Response(list, null)
            }
        }
    }

    override suspend fun getMessage(userId: String, messageById: MessagesById): Response<Message> {
        return if (userRepository.getUserById(userId) == null) {
            Response(null, ExceptionMessage.EXCEPTION_USER_DONT_EXIST)
        } else {
            val message = messageRepository.getMessage(messageById.id)
            if (message == null){
                Response(null, ExceptionMessage.EXCEPTION_MESSAGE_DONT_EXIST)
            } else
                Response(message, null)
        }
    }
}