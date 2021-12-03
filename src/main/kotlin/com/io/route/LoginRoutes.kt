package com.io.route

import com.io.controller.login.LoginController
import com.io.data.mapper.toLoginResponse
import com.io.data.model.login.LoginRequest
import com.io.data.model.login.LoginResponse
import com.io.util.*
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Route.loginRoutes(
    jwtIssuer: String,
    jwtAudience: String,
    jwtSecret: String
) {
    val loginController: LoginController by inject()

    post(LoginApiConstant.LOGIN) {
        val request = call.receiveOrNull<LoginRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        val response = loginController.login(request)

        if (response.first != null){
            val accessToken = getAccessToken(
                userId = response.first!!.id,
                jwtIssuer = jwtIssuer,
                jwtAudience = jwtAudience,
                jwtSecret = jwtSecret
            )

            val refreshToken = loginController.createRefreshToken(
                userId = response.first!!.id
            )

            call.response<LoginResponse>(response.first?.toLoginResponse(accessToken, refreshToken), response.second)
        } else call.response<LoginResponse>(null, response.second)

    }

    authenticate {
        post(LoginApiConstant.LOGOUT) {
            val response = loginController.logout(call.userId)
            call.responseBoolean(response.first, response.second)
        }
    }
}