package com.io.route

import com.io.controller.message.MessageController
import com.io.data.mapper.toResponse
import com.io.data.model.message.MessageRequest
import com.io.util.BASE_API
import com.io.util.BaseResponse
import com.io.util.MessageApiConstant
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Route.messageRoutes() {
    route(BASE_API){

        val messageController: MessageController by inject()

        post(MessageApiConstant.MESSAGE_SEND) {
            val request = call.receiveOrNull<MessageRequest>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }

            val response = messageController.sendMessage(request)

            call.respond(
                BaseResponse(
                    isSuccessful = response.first != null,
                    message = response.first?.toResponse() ?: response.second
                )
            )
        }

        post(MessageApiConstant.MESSAGE_UPDATE) {
            val request = call.receiveOrNull<MessageRequest>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }

            val response = messageController.updateMessage(request)

            call.respond(
                BaseResponse(
                    isSuccessful = response.first != null,
                    message = response.first?.toResponse() ?: response.second
                )
            )
        }

        post(MessageApiConstant.MESSAGE_DELETE) {
            val request = call.receiveOrNull<MessageRequest>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }

            val response = messageController.deleteMessage(request)

            call.respond(
                BaseResponse(
                    isSuccessful = response.first,
                    message = response.second
                )
            )
        }
    }
}