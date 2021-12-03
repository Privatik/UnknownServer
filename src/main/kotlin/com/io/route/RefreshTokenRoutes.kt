package com.io.route

import com.io.controller.refresh_token.RefreshTokenController
import com.io.data.mapper.toResponse
import com.io.data.model.login.LoginResponse
import com.io.data.model.refresh_token.RefreshTokenRequest
import com.io.data.model.refresh_token.RefreshTokenResponse
import com.io.data.model.user.UserRequest
import com.io.util.RefreshApiConstant
import com.io.util.getAccessToken
import com.io.util.response
import com.io.util.responseBoolean
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Route.refreshTokenRoutes(
    jwtIssuer: String,
    jwtAudience: String,
    jwtSecret: String
) {
    val controller: RefreshTokenController by inject()

    post(RefreshApiConstant.REFRESH_TOKEN){
        val request = call.receiveOrNull<RefreshTokenRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        val checkValidPair = controller.checkRefreshToken(request)

        if (checkValidPair.first){
            val response = controller.createNewRefreshToken(request.userId)

            if (response.first != null){
                val accessToken = getAccessToken(
                    userId = response.first!!.userId,
                    jwtSecret = jwtSecret,
                    jwtAudience = jwtAudience,
                    jwtIssuer = jwtIssuer
                )
                call.response<RefreshTokenResponse>(response.first?.toResponse(accessToken), null)
            } else {
                call.response<RefreshTokenResponse>(null, response.second)
            }
        } else {
            call.responseBoolean(checkValidPair.first, checkValidPair.second)
        }
    }
}