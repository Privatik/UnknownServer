package com.io.route

import com.io.controller.login.LoginController
import com.io.data.mapper.toLoginResponse
import com.io.data.model.login.LoginRequest
import com.io.data.model.login.LoginResponse
import com.io.secutiry.token.TokenClaim
import com.io.secutiry.token.TokenConfig
import com.io.secutiry.token.TokenService
import com.io.secutiry.token.userId
import com.io.util.*
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Route.loginRoutes(
    accessTokenConfig: TokenConfig,
    refreshTokenConfig: TokenConfig
) {
    val loginController: LoginController by inject()
    val tokenService: TokenService by inject()

    post(LoginApiConstant.LOGIN) {
        val request = call.receiveOrNull<LoginRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        val response = loginController.login(request)

        if (response.isSuccess()){
            val accessToken = tokenService
                .generate(
                    accessTokenConfig,
                    TokenClaim("userId", response.data!!.id)
                )

            val refreshToken = tokenService
                .generate(
                    refreshTokenConfig,
                    TokenClaim("userId", response.data.id)
                )

            call.response<LoginResponse>(response.data.toLoginResponse(accessToken, refreshToken), response.exceptionMessage)
        } else call.response<LoginResponse>(null, response.exceptionMessage)

    }

    authenticate(Constants.ACCESS_TOKEN) {
        get(LoginApiConstant.LOGOUT) {
            val response = loginController.logout(call.userId)
            call.responseBoolean(response.data!!, response.exceptionMessage)
        }
    }
}