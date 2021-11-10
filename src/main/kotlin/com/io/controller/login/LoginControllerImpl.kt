package com.io.controller.login

import com.io.data.model.login.LoginRequest
import com.io.model.User
import com.io.repository.login.LoginRepository
import com.io.repository.user.UserRepository
import com.io.util.ExceptionMessage
import kotlin.math.log

class LoginControllerImpl(
    private val loginRepository: LoginRepository,
    private val userRepository: UserRepository
): LoginController {

    override suspend fun login(login: LoginRequest): Pair<User?, ExceptionMessage?> {
        if (login.inBlank()){
            return Pair(null, ExceptionMessage.EXCEPTION_LOGIN_FIELD_EMPTY)
        }
        val user = loginRepository.login(login.email, login.password) ?: kotlin.run {
            return Pair(null, ExceptionMessage.EXCEPTION_USER_DONT_EXIST)
        }

        return if (user.isActive){
            Pair(null, ExceptionMessage.EXCEPTION_USER_ACTIVE)
        } else {
            userRepository.changeActive(user.id, true)
            Pair(user, null)
        }
    }

    override suspend fun logout(id: String): Pair<Boolean, ExceptionMessage?> {
        val user = loginRepository.logout(id) ?: kotlin.run {
            return Pair(false, ExceptionMessage.EXCEPTION_USER_DONT_EXIST)
        }

        return if (!user.isActive){
            Pair(false, ExceptionMessage.EXCEPTION_USER_NOT_ACTIVE)
        } else {
            userRepository.changeActive(user.id, false)
            Pair(true, null)
        }
    }
}