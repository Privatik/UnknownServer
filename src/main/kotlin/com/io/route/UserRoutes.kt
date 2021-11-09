package com.io.route

import com.io.controller.user.UserController
import com.io.data.mapper.toModel
import com.io.data.mapper.toResponse
import com.io.data.model.user.UserRequest
import com.io.util.BASE_API
import com.io.util.BaseResponse
import com.io.util.UserApiConstant
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject


fun Route.userRoutes() {
    val userController: UserController by inject()

    route(BASE_API) {
        post(UserApiConstant.USER_CREATE) {
            val request = call.receiveOrNull<UserRequest>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }

            val response = userController.createUser(request.toModel())
            call.respond(
                BaseResponse(
                    isSuccessful = response.first != null,
                    message = response.first?.toResponse() ?: response.second
                )
            )
        }

        post(UserApiConstant.USER_GET_BY_ID) {
            val request = call.receiveOrNull<String>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }

            val response = userController.getUserById(request)
            call.respond(
                BaseResponse(
                    isSuccessful = response.first != null,
                    message = response.first?.toResponse() ?: response.second
                )
            )
        }

        get("users"){
            call.respond(userController.getAll().map { it.toResponse() })
        }
    }
}