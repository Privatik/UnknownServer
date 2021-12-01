package com.io.controller.login

import com.io.data.model.login.LoginIdRequest
import com.io.data.model.login.LoginRequest
import com.io.model.User
import com.io.repository.login.LoginRepository
import com.io.repository.refresh_token.RefreshTokenRepository
import com.io.repository.user.UserRepository
import com.io.util.ExceptionMessage
import kotlin.math.log

class LoginControllerImpl(
    private val loginRepository: LoginRepository,
    private val userRepository: UserRepository,
    private val refreshTokenRepository: RefreshTokenRepository
): LoginController {

    override suspend fun login(login: LoginRequest): Pair<User?, ExceptionMessage?> {
        if (login.inBlank()){
            return Pair(null, ExceptionMessage.EXCEPTION_LOGIN_FIELD_EMPTY)
        }
        val user = loginRepository.login(login.email) ?: kotlin.run {
            return Pair(null, ExceptionMessage.EXCEPTION_USER_DONT_EXIST)
        }

        return if (loginRepository.isCorrectPassword(user, login.password)){
            userRepository.changeActive(user.id, true)
            Pair(user, null)
        } else {
            Pair(null, ExceptionMessage.EXCEPTION_LOGIN_DONT_CORRECT_PASSWORD)
        }
    }

    override suspend fun logout(logout: LoginIdRequest): Pair<Boolean, ExceptionMessage?> {
        val user = loginRepository.logout(logout.id) ?: kotlin.run {
            return Pair(false, ExceptionMessage.EXCEPTION_USER_DONT_EXIST)
        }

        userRepository.changeActive(user.id, false)
        return Pair(true, null)
    }

    override suspend fun createRefreshToken(userId: String, email: String): String =
        refreshTokenRepository.getRefreshToken(userId, email).refreshToken
}