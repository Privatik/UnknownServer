package com.io.controller.login

import com.io.model.User
import com.io.repository.login.LoginRepository
import com.io.repository.user.UserRepository
import com.io.util.ExceptionMessage

class LoginControllerImpl(
    private val loginRepository: LoginRepository,
    private val userRepository: UserRepository
): LoginController {

    override suspend fun login(email: String, password: String): Pair<User?, ExceptionMessage?> {
        val user = loginRepository.login(email, password) ?: kotlin.run {
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