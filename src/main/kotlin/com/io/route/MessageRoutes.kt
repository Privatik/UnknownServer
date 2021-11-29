package com.io.route

import com.io.controller.message.MessageController
import com.io.data.mapper.toResponse
import com.io.data.model.chat.ChatResponse
import com.io.data.model.message.MessageRequest
import com.io.data.model.message.MessageResponse
import com.io.data.model.message.MessagesPagingRequest
import com.io.model.Message
import com.io.util.*
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Route.messageRoutes() {
    val messageController: MessageController by inject()

    post(MessageApiConstant.MESSAGE_SEND) {
        val request = call.receiveOrNull<MessageRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        val response = messageController.sendMessage(request)

        call.response<MessageResponse>(response.first?.toResponse(), response.second)
    }

    post(MessageApiConstant.MESSAGE_UPDATE) {
        val request = call.receiveOrNull<MessageRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        val response = messageController.updateMessage(request)

        call.response<MessageResponse>(response.first?.toResponse(), response.second)
    }

    post(MessageApiConstant.MESSAGE_DELETE) {
        val request = call.receiveOrNull<MessageRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        val response = messageController.deleteMessage(request)

        call.responseBoolean(response.first, response.second)
    }

    post(MessageApiConstant.MESSAGES_GEt_BY_CHAT_ID) {
        val request = call.receiveOrNull<MessagesPagingRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        val response = messageController.getMessages(request)

        call.response<List<MessageResponse>>(response.first?.map { it.toResponse() }, response.second)
    }
}