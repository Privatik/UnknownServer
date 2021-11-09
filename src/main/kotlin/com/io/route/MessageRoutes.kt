package com.io.route

import com.io.data.model.chat.ChatRequest
import com.io.data.model.message.MessageRequest
import com.io.util.BASE_API
import com.io.util.MessageApiConstant
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.messageRoutes() {
    route(BASE_API){

        post(MessageApiConstant.MESSAGE_SEND) {
            val request = call.receiveOrNull<MessageRequest>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }
        }

        post(MessageApiConstant.MESSAGE_UPDATE) {

        }

        post(MessageApiConstant.MESSAGE_DELETE) {

        }
    }
}