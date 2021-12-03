package com.io.controller.message

import com.io.data.model.message.MessageRequest
import com.io.data.model.message.MessagesPagingRequest
import com.io.model.Message
import com.io.util.BaseResponse
import com.io.util.ExceptionMessage

interface MessageController {

    suspend fun sendMessage(userId: String, message: MessageRequest): Pair<Message?, ExceptionMessage?>
    suspend fun updateMessage(userId: String, message: MessageRequest): Pair<Message?, ExceptionMessage?>
    suspend fun deleteMessage(userId: String, message: MessageRequest): Pair<Boolean, ExceptionMessage?>
    suspend fun getMessages(userId: String, messagePaging: MessagesPagingRequest): Pair<List<Message>?, ExceptionMessage?>
}