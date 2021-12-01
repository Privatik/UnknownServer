package com.io.route

import com.io.controller.user.UserController
import com.io.data.mapper.toLoginResponse
import com.io.data.mapper.toResponse
import com.io.data.model.login.LoginResponse
import com.io.data.model.user.UserIdRequest
import com.io.data.model.user.UserRequest
import com.io.data.model.user.UserResponse
import com.io.service.UserService
import com.io.util.*
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject


fun Route.userRoutes(
    jwtIssuer: String,
    jwtAudience: String,
    jwtSecret: String
) {
    val userController: UserController by inject()
    val userService: UserService by inject()

    post(UserApiConstant.USER_CREATE) {
        val request = call.receiveOrNull<UserRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        val response = userController.createUser(request)
        if (response.first != null){
            val accessToken = getAccessToken(
                email = response.first!!.email,
                jwtIssuer = jwtIssuer,
                jwtAudience = jwtAudience,
                jwtSecret = jwtSecret
            )

            val refreshToken = userController.createRefreshToken(
                userId = response.first!!.id,
                email = response.first!!.email
            )

            call.response<LoginResponse>(response.first?.toLoginResponse(accessToken, refreshToken), response.second)
        } else call.response<LoginResponse>(null, response.second)
    }

    authenticate {
        post(UserApiConstant.USER_GET_BY_ID) {
            val requestID = call.receiveOrNull<UserIdRequest>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }

            ifEmailBelongsToUser(
                userId = requestID.id,
                validateEmail = userService::checkUserByEmail
            ) {
                val response = userController.getUserById(requestID)
                call.response<UserResponse>(response.first?.toResponse(), response.second)
            }
        }
    }
}