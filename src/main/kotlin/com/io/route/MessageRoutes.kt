package com.io.route

import com.io.controller.message.MessageController
import com.io.data.mapper.toResponse
import com.io.data.model.message.MessageRequest
import com.io.data.model.message.MessageResponse
import com.io.data.model.message.MessagesPagingRequest
import com.io.secutiry.token.userId
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

        val response = messageController.sendMessage(call.userId, request)
        call.response<MessageResponse>(response.first?.toResponse(), response.second)
    }

    post(MessageApiConstant.MESSAGE_UPDATE) {
        val request = call.receiveOrNull<MessageRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        val response = messageController.updateMessage(call.userId, request)
        call.response<MessageResponse>(response.first?.toResponse(), response.second)
    }

    post(MessageApiConstant.MESSAGE_DELETE) {
        val request = call.receiveOrNull<MessageRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        val response = messageController.deleteMessage(call.userId, request)
        call.responseBoolean(response.first, response.second)
    }

    get(MessageApiConstant.MESSAGES_GEt_BY_CHAT_ID) {
        val request = MessagesPagingRequest(
            chatId = call.request.queryParameters["chatId"] ?: "",
            page = call.request.queryParameters["page"]?.toInt() ?: -1,
            pageSize = call.request.queryParameters["chatId"]?.toInt() ?: -1
        )

        if (request.isIncorrect()) {
            call.respond(HttpStatusCode.BadRequest)
            return@get
        }

        val response = messageController.getMessages(call.userId, request)
        call.response<List<MessageResponse>>(response.first?.map { it.toResponse() }, response.second)
    }
}