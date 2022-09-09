package com.io.route

import com.io.controller.chat.ChatController
import com.io.data.mapper.toResponse
import com.io.data.model.chat.ChatRequest
import com.io.data.model.chat.ChatResponse
import com.io.data.model.chat.ChatsPagingRequest
import com.io.secutiry.token.userId
import com.io.util.*
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Route.chatRoutes() {

    val chatController: ChatController by inject()

    post(ChatApiConstant.CHAT_CREATE) {
        val request = call.receiveOrNull<ChatRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        val response = chatController.createChat(call.userId, request)

        call.response<ChatResponse>(response.first?.toResponse(), response.second)
    }

    post(ChatApiConstant.CHATS_GET_BY_USER_ID) {
        val request = call.receiveOrNull<ChatsPagingRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        val response = chatController.getChats(call.userId, request)

        call.response<List<ChatResponse>>(response.first?.map { it.toResponse() }, response.second)
    }
}