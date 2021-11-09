package com.io.route

import com.io.controller.user.UserController
import com.io.data.mapper.toModel
import com.io.data.mapper.toResponse
import com.io.data.model.user.UserRequest
import com.io.util.BaseResponse
import com.io.model.User
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject


fun Route.userRoutes() {
    val userController: UserController by inject()

    route("/api") {
        post("/user/create") {
            val request = call.receiveOrNull<UserRequest>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }
            call.respond(
                BaseResponse<User>(
                    isSuccessful = true,
                    message = userController.createUser(request.toModel())
                )
            )
        }

        get("users"){
            call.respond(userController.getAll().map { it.toResponse() })
        }
    }
}