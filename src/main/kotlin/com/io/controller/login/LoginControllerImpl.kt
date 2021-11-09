package com.io.controller.login

import com.io.model.User

class LoginControllerImpl: LoginController {

    override suspend fun login(email: String, password: String): User? {
        return null
    }

    override suspend fun logout(id: String): Boolean {
        return true
    }
}