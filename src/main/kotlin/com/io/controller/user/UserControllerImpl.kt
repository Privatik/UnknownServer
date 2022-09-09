package com.io.controller.user

import com.io.data.mapper.toModel
import com.io.data.model.user.UserRequest
import com.io.model.User
import com.io.repository.refresh_token.RefreshTokenRepository
import com.io.repository.user.UserRepository
import com.io.util.ExceptionMessage
import com.io.util.Response

class UserControllerImpl(
    private val userRepository: UserRepository,
    private val refreshTokenRepository: RefreshTokenRepository
): UserController {

    override suspend fun createUser(user: UserRequest): Response<User> {
        if(user.isBlank()) {
            return Response(null, ExceptionMessage.EXCEPTION_USER_FIELD_EMPTY)
        }

        val userCreate = userRepository.createUser(user.toModel())
        return if (userCreate != null){
            Response(userCreate, null)
        } else {
            Response(null, ExceptionMessage.EXCEPTION_USER_HAS_ALREADY)
        }
    }

    override suspend fun getUserById(userId: String): Response<User> {
        val userFind = userRepository.getUserById(userId) ?: kotlin.run {
            return Response(null, ExceptionMessage.EXCEPTION_USER_DONT_EXIST)
        }
        return Response(userFind, null)
    }

    override suspend fun createRefreshToken(userId: String): String =
        refreshTokenRepository.getRefreshToken(userId).refreshToken
}