package com.io.controller.login

import com.io.data.model.login.LoginIdRequest
import com.io.data.model.login.LoginRequest
import com.io.model.User
import com.io.util.BaseResponse
import com.io.util.ExceptionMessage

interface LoginController {

    suspend fun login(login: LoginRequest): Pair<User?, ExceptionMessage?>

    suspend fun logout(logout: LoginIdRequest): Pair<Boolean , ExceptionMessage?>

    suspend fun createRefreshToken(userId: String, email: String): String
}