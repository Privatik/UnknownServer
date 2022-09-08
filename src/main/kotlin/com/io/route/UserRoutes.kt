package com.io.route

import com.io.controller.user.UserController
import com.io.data.mapper.toLoginResponse
import com.io.data.mapper.toResponse
import com.io.data.model.login.LoginResponse
import com.io.data.model.user.UserRequest
import com.io.data.model.user.UserResponse
import com.io.model.TestConnect
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
    jwtIssuer: String,
    jwtAudience: String,
    jwtSecret: String
) {
    val userController: UserController by inject()

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

        var accessToken: String = ""
        var refreshToken: String = ""

        val response = userController.createUser(request)
        if (response.first != null){
            accessToken = getAccessToken(
                userId = response.first!!.id,
                jwtIssuer = jwtIssuer,
                jwtAudience = jwtAudience,
                jwtSecret = jwtSecret
            )

            refreshToken = userController.createRefreshToken(
                userId = response.first!!.id
            )
        }
        call.response<LoginResponse>(response.first?.toLoginResponse(accessToken, refreshToken), response.second)
    }

    authenticate {
        get(UserApiConstant.USER_GET_BY_ID) {
            val response = userController.getUserById(call.userId)
            call.response<UserResponse>(response.first?.toResponse(), response.second)
        }
    }
}