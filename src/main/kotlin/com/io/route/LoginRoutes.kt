package com.io.route

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.io.controller.login.LoginController
import com.io.data.mapper.toLoginResponse
import com.io.data.model.login.LoginIdRequest
import com.io.data.model.login.LoginRequest
import com.io.data.model.login.LoginResponse
import com.io.service.UserService
import com.io.util.*
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject
import java.util.*

fun Route.loginRoutes(
    jwtIssuer: String,
    jwtAudience: String,
    jwtSecret: String
) {
    val loginController: LoginController by inject()
    val userService: UserService by inject()

    post(LoginApiConstant.LOGIN) {
        val request = call.receiveOrNull<LoginRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        val response = loginController.login(request)

        if (response.first != null){
            val accessToken = getAccessToken(
                email = response.first!!.email,
                jwtIssuer = jwtIssuer,
                jwtAudience = jwtAudience,
                jwtSecret = jwtSecret
            )

            val refreshToken = loginController.createRefreshToken(
                userId = response.first!!.id,
                email = response.first!!.email
            )

            call.response<LoginResponse>(response.first?.toLoginResponse(accessToken, refreshToken), response.second)
        } else call.response<LoginResponse>(null, response.second)

    }

    authenticate {
        post(LoginApiConstant.LOGOUT) {
            val request = call.receiveOrNull<LoginIdRequest>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }

            ifEmailBelongsToUser(
                userId = request.id,
                validateEmail = userService::checkUserByEmail
            ) {
                val response = loginController.logout(request)
                call.responseBoolean(response.first, response.second)
            }
        }
    }
}