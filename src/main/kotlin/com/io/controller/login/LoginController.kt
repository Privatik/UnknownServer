package com.io.controller.login

import com.io.data.model.login.LoginRequest
import com.io.model.User
import com.io.util.ExceptionMessage
import com.io.util.Response

interface LoginController {

    suspend fun login(login: LoginRequest): Response<User>

    suspend fun logout(userId: String): Response<Boolean>

    suspend fun createRefreshToken(userId: String): String
}