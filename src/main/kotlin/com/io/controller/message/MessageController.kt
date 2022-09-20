package com.io.controller.message

import com.io.data.model.message.MessageRequest
import com.io.data.model.message.MessagesById
import com.io.data.model.message.MessagesPagingRequest
import com.io.model.Message
import com.io.util.BaseResponse
import com.io.util.ExceptionMessage
import com.io.util.Response

interface MessageController {

    suspend fun sendMessage(message: MessageRequest): Response<Message>
    suspend fun updateMessage(message: MessageRequest): Response<Message>
    suspend fun deleteMessage(message: MessageRequest): Response<String>
    suspend fun getMessages(userId: String, messagePaging: MessagesPagingRequest): Response<List<Message>>
    suspend fun getMessage(userId: String, messageById: MessagesById): Response<Message>
}