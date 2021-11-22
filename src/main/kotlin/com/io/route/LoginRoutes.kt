package com.io.route

import com.io.controller.login.LoginController
import com.io.data.mapper.toResponse
import com.io.data.model.login.LoginIdRequest
import com.io.data.model.login.LoginRequest
import com.io.data.model.user.UserResponse
import com.io.util.*
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Route.loginRoutes() {

    val loginController: LoginController by inject()

    post(LoginApiConstant.LOGIN) {
        val request = call.receiveOrNull<LoginRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        val response = loginController.login(request)

        call.response<UserResponse>(response.first?.toResponse(), response.second)
    }

    post(LoginApiConstant.LOGOUT) {
        val request = call.receiveOrNull<LoginIdRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        val response = loginController.logout(request)

        call.responseBoolean(response.first, response.second)
    }
}