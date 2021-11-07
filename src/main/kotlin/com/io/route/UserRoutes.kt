package com.io.route

import com.io.controller.user.UserController
import com.io.data.response.BaseResponse
import com.io.model.User
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject


fun Route.userRoutes() {
    val userController: UserController by inject()
    route("/api/user/create") {
        post {
            val request = call.receiveOrNull<User>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }
            if(request.email.isBlank() || request.password.isBlank()) {
                call.respond(
                    BaseResponse(
                        isSuccessful = false,
                        message = "Filed empty"
                    )
                )
                return@post
            }
            userController.createUser(
                User(
                    email = request.email,
                    password = request.password
                )
            )
            call.respond(
                BaseResponse(isSuccessful = true)
            )
        }

        get{
            call.respond(userController.getAll())
        }
    }
}