package com.io.controller.login

import com.io.data.model.login.LoginRequest
import com.io.model.User
import com.io.util.BaseResponse
import com.io.util.ExceptionMessage

interface LoginController {

    suspend fun login(login: LoginRequest): Pair<User?, ExceptionMessage?>

    suspend fun logout(id: String): Pair<Boolean , ExceptionMessage?>
}