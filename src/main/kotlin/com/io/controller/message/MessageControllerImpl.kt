package com.io.controller.message

import com.io.data.mapper.toModel
import com.io.data.model.message.MessageRequest
import com.io.model.Message
import com.io.repository.message.MessageRepository
import com.io.repository.user.UserRepository
import com.io.util.ExceptionMessage

class MessageControllerImpl(
    private val messageRepository: MessageRepository
): MessageController {

    override suspend fun sendMessage(message: MessageRequest): Pair<Message?, ExceptionMessage?> {
        if (message.isBlank()){
            return Pair(null, ExceptionMessage.EXCEPTION_MESSAGE_FIELD_EMPTY)
        }
        val messageModel = messageRepository.sendMessage(message.toModel())

        return Pair(messageModel, null)
    }

    override suspend fun updateMessage(message: MessageRequest): Pair<Message?, ExceptionMessage?> {
        if (message.isBlankWithId()){
            return Pair(null, ExceptionMessage.EXCEPTION_MESSAGE_FIELD_EMPTY)
        } else if (message.timeUpdate != null){
            return Pair(null, ExceptionMessage.EXCEPTION_MESSAGE_DONT_TIMEUPDATE)
        }

        val messageModel = messageRepository.updateMessage(message.id.toString(), message.timeUpdate.toString())
            ?: kotlin.run {
                return Pair(null, ExceptionMessage.EXCEPTION_MESSAGE_DONT_EXIST)
            }

        return Pair(messageModel, null)
    }

    override suspend fun deleteMessage(message: MessageRequest): Pair<Boolean, ExceptionMessage?> {
        if (message.isBlankWithId()){
            return Pair(false, ExceptionMessage.EXCEPTION_MESSAGE_FIELD_EMPTY)
        }

        val isDelete = messageRepository.deleteMessage(message.id!!)

        return Pair(isDelete, if (isDelete) null else ExceptionMessage.EXCEPTION_MESSAGE_DONT_EXIST)
    }
}