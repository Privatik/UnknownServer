package com.io.controller.user

import com.io.data.mapper.toModel
import com.io.data.model.user.UserIdRequest
import com.io.data.model.user.UserRequest
import com.io.model.RefreshToken
import com.io.model.User
import com.io.repository.refresh_token.RefreshTokenRepository
import com.io.repository.user.UserRepository
import com.io.util.ExceptionMessage

class UserControllerImpl(
    private val userRepository: UserRepository,
    private val refreshTokenRepository: RefreshTokenRepository
): UserController {

    override suspend fun createUser(user: UserRequest): Pair<User?, ExceptionMessage?> {
        if(user.isBlank()) {
            return Pair(null, ExceptionMessage.EXCEPTION_USER_FIELD_EMPTY)
        }

        val userCreate = userRepository.createUser(user.toModel())
        return if (userCreate != null){
            Pair(userCreate, null)
        } else {
            Pair(null, ExceptionMessage.EXCEPTION_USER_HAS_ALREADY)
        }
    }

    override suspend fun getUserById(user: UserIdRequest): Pair<User?, ExceptionMessage?> {
        val userFind = userRepository.getUserById(user.id) ?: kotlin.run {
            return Pair(null, ExceptionMessage.EXCEPTION_USER_DONT_EXIST)
        }

        return Pair(userFind, null)
    }

    override suspend fun createRefreshToken(userId: String, email: String): String =
        refreshTokenRepository.getRefreshToken(userId, email).refreshToken
}