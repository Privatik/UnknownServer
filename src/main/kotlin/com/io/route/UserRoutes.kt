package com.io.route

import com.io.controller.user.UserController
import com.io.data.mapper.toLoginResponse
import com.io.data.mapper.toResponse
import com.io.data.model.login.LoginResponse
import com.io.data.model.user.UserRequest
import com.io.data.model.user.UserResponse
import com.io.model.TestConnect
import com.io.util.UserApiConstant
import com.io.util.getAccessToken
import com.io.util.response
import com.io.util.userId
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

    post(UserApiConstant.USER_CREATE) {
        val request = call.receiveOrNull<UserRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        val response = userController.createUser(request)
        if (response.first != null){
            val accessToken = getAccessToken(
                userId = response.first!!.id,
                jwtIssuer = jwtIssuer,
                jwtAudience = jwtAudience,
                jwtSecret = jwtSecret
            )

            val refreshToken = userController.createRefreshToken(
                userId = response.first!!.id
            )

            call.response<LoginResponse>(response.first?.toLoginResponse(accessToken, refreshToken), response.second)
        } else call.response<LoginResponse>(null, response.second)
    }

    authenticate {
        post(UserApiConstant.USER_GET_BY_ID) {
            val response = userController.getUserById(call.userId)
            call.response<UserResponse>(response.first?.toResponse(), response.second)
        }
    }

    get("/") {
        call.respond(HttpStatusCode.OK, TestConnect(text = "Hello World"))
    }
}