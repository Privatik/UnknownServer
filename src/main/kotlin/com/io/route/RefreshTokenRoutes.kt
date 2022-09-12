package com.io.route

import com.io.controller.refresh_token.RefreshTokenController
import com.io.data.mapper.toResponse
import com.io.data.model.refresh_token.RefreshTokenRequest
import com.io.data.model.refresh_token.RefreshTokenResponse
import com.io.secutiry.token.TokenClaim
import com.io.secutiry.token.TokenConfig
import com.io.secutiry.token.TokenService
import com.io.secutiry.token.userId
import com.io.util.RefreshApiConstant
import com.io.util.response
import com.io.util.responseBoolean
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Route.refreshTokenRoutes(
    accessTokenConfig: TokenConfig,
    refreshTokenConfig: TokenConfig
) {
    val tokenService: TokenService by inject()

    post(RefreshApiConstant.REFRESH_TOKEN){
        val request = call.receiveOrNull<RefreshTokenRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        if (request.userId != call.userId){
            call.respond(HttpStatusCode.Conflict)
            return@post
        }

        val accessToken = tokenService.generate(
            accessTokenConfig,
            TokenClaim("userId", request.userId)
        )
        val refreshToken = tokenService.generate(
            refreshTokenConfig,
            TokenClaim("userId", request.userId)
        )

        call.response<RefreshTokenResponse>(
            RefreshTokenResponse(accessToken, refreshToken),
            null
        )
    }
}