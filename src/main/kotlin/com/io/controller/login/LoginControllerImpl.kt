package com.io.controller.login

import com.io.data.model.login.LoginRequest
import com.io.model.User
import com.io.repository.login.LoginRepository
import com.io.repository.refresh_token.RefreshTokenRepository
import com.io.repository.user.UserRepository
import com.io.util.ExceptionMessage
import com.io.util.Response

class LoginControllerImpl(
    private val loginRepository: LoginRepository,
    private val userRepository: UserRepository,
    private val refreshTokenRepository: RefreshTokenRepository
): LoginController {

    override suspend fun login(login: LoginRequest): Response<User> {
        if (login.inBlank()){
            return Response(null, ExceptionMessage.EXCEPTION_LOGIN_FIELD_EMPTY)
        }
        val user = loginRepository.login(login.email) ?: kotlin.run {
            return Response(null, ExceptionMessage.EXCEPTION_USER_DONT_EXIST)
        }

        return if (loginRepository.isCorrectPassword(user, login.password)){
            Response(user, null)
        } else {
            Response(null, ExceptionMessage.EXCEPTION_LOGIN_DONT_CORRECT_PASSWORD)
        }
    }

    override suspend fun logout(userId: String): Response<Boolean> {
        loginRepository.logout(userId) ?: kotlin.run {
            return Response(false, ExceptionMessage.EXCEPTION_USER_DONT_EXIST)
        }

        return Response(true, null)
    }

    override suspend fun createRefreshToken(userId: String): String =
        refreshTokenRepository.getRefreshToken(userId).refreshToken
}