package com.io.controller.message

import com.io.data.model.message.MessageRequest
import com.io.data.model.message.MessagesPagingRequest
import com.io.model.Message
import com.io.util.BaseResponse
import com.io.util.ExceptionMessage

interface MessageController {

    suspend fun sendMessage(message: MessageRequest): Pair<Message?, ExceptionMessage?>
    suspend fun updateMessage(message: MessageRequest): Pair<Message?, ExceptionMessage?>
    suspend fun deleteMessage(message: MessageRequest): Pair<Boolean, ExceptionMessage?>
    suspend fun getMessages(messagePaging: MessagesPagingRequest): Pair<List<Message>?, ExceptionMessage?>
}