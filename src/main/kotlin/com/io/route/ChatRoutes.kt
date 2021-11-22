package com.io.route

import com.io.controller.chat.ChatController
import com.io.data.mapper.toModel
import com.io.data.mapper.toResponse
import com.io.data.model.chat.ChatIdRequest
import com.io.data.model.chat.ChatRequest
import com.io.data.model.chat.ChatResponse
import com.io.data.model.user.UserRequest
import com.io.util.BASE_API
import com.io.util.BaseResponse
import com.io.util.ChatApiConstant
import com.io.util.response
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

        val response = chatController.createChat(request)

        call.response<ChatResponse>(response.first?.toResponse(), response.second)
    }

    post(ChatApiConstant.CHAT_GET_BY_ID) {
        val request = call.receiveOrNull<ChatIdRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        val response = chatController.getChat(request)

        call.response<ChatResponse>(response.first?.toResponse(), response.second)
    }
}