package com.io.route

import com.io.controller.user.UserController
import com.io.data.mapper.toModel
import com.io.data.mapper.toResponse
import com.io.data.model.user.UserIdRequest
import com.io.data.model.user.UserRequest
import com.io.data.model.user.UserResponse
import com.io.util.BASE_API
import com.io.util.BaseResponse
import com.io.util.UserApiConstant
import com.io.util.response
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject


fun Route.userRoutes() {
    val userController: UserController by inject()

    post(UserApiConstant.USER_CREATE) {
        val request = call.receiveOrNull<UserRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        val response = userController.createUser(request)
        call.response<UserResponse>(response.first?.toResponse(), response.second)
    }

    post(UserApiConstant.USER_GET_BY_ID) {
        val requestID = call.receiveOrNull<UserIdRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        val response = userController.getUserById(requestID)
        call.response<UserResponse>(response.first?.toResponse(), response.second)
    }
}