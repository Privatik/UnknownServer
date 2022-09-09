package com.io.route

import com.io.controller.user.UserController
import com.io.data.mapper.toLoginResponse
import com.io.data.mapper.toResponse
import com.io.data.model.login.LoginResponse
import com.io.data.model.user.UserRequest
import com.io.data.model.user.UserResponse
import com.io.model.TestConnect
import com.io.secutiry.token.TokenClaim
import com.io.secutiry.token.TokenConfig
import com.io.secutiry.token.TokenService
import com.io.secutiry.token.userId
import com.io.util.*
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject
import java.io.File
import java.util.UUID

fun Route.userRoutes(
    accessTokenConfig: TokenConfig,
    refreshTokenConfig: TokenConfig
) {
    val userController: UserController by inject()
    val tokenService: TokenService by inject()

    post(UserApiConstant.USER_CREATE) {
        val userMultiPartData = call.receiveMultipart()

        var request = UserRequest.empty()

        userMultiPartData.forEachPart { part ->
            when (part){
                is PartData.FormItem -> {
                    request = request.updateBody(part)
                }
                is PartData.FileItem -> {
                    val avatarUrl = part.save(Constants.PHOTO_FILE)
                    request = request.copy(avatar = avatarUrl)
                    part.dispose()
                }
                is PartData.BinaryItem -> {}
            }
        }


        val response = userController.createUser(request)
        val answer = if (response.data != null){
            val accessToken = tokenService.generate(
                config = accessTokenConfig,
                TokenClaim("userId", response.data.id)
            )

            val refreshToken = tokenService.generate(
                config = refreshTokenConfig,
                TokenClaim("userId", response.data.id)
            )
            response.data.toLoginResponse(accessToken, refreshToken)
        } else null

        call.response<LoginResponse>(answer, response.exceptionMessage)
    }

}