package com.io.controller.login

import com.io.model.User
import com.io.repository.login.LoginRepository

class LoginControllerImpl(
    private val loginRepository: LoginRepository
): LoginController {

    override suspend fun login(email: String, password: String): User? {
        val user = loginRepository.login(email, password)
        return null
    }

    override suspend fun logout(id: String): Boolean {
        return true
    }
}