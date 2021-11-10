package com.io.route

import com.io.controller.login.LoginController
import com.io.data.mapper.toResponse
import com.io.data.model.login.LoginRequest
import com.io.util.BASE_API
import com.io.util.BaseResponse
import com.io.util.LoginApiConstant
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Route.loginRoutes() {

    val loginController: LoginController by inject()

    route(BASE_API){
        post(LoginApiConstant.LOGIN) {
            val request = call.receiveOrNull<LoginRequest>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }

            val response = loginController.login(request)

            call.respond(
                BaseResponse(
                    isSuccessful = response.first != null,
                    message = response.second ?: response.first?.toResponse()
                )
            )
        }

        post(LoginApiConstant.LOGOUT) {
            val requestID = call.receiveOrNull<String>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }

            val response = loginController.logout(requestID)

            call.respond(
                BaseResponse(
                    isSuccessful = response.first,
                    message = response.second
                )
            )
        }
    }
}