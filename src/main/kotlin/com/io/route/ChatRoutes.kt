package com.io.route

import com.io.controller.chat.ChatController
import com.io.data.mapper.toModel
import com.io.data.mapper.toResponse
import com.io.data.model.chat.ChatRequest
import com.io.data.model.user.UserRequest
import com.io.util.BASE_API
import com.io.util.BaseResponse
import com.io.util.ChatApiConstant
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Route.chatRoutes() {

    val chatController: ChatController by inject()

    route(BASE_API){
        post(ChatApiConstant.CHAT_CREATE) {
            val request = call.receiveOrNull<ChatRequest>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }

            val response = chatController.createChat(request.toModel())

            call.respond(
                BaseResponse(
                    isSuccessful = response.first != null,
                    message = response.first?.toResponse() ?: response.second
                )
            )
        }

        post(ChatApiConstant.CHAT_GET_BY_ID) {
            val request = call.receiveOrNull<String>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }

            val response = chatController.getChat(request)

            call.respond(
                BaseResponse(
                    isSuccessful = response.first != null,
                    message = response.first?.toResponse() ?: response.second
                )
            )
        }
    }
}