package com.io.controller.login

import com.io.model.User
import com.io.util.BaseResponse

interface LoginController {

    suspend fun login(email: String, password: String): User?

    suspend fun logout(id: String): Boolean
}