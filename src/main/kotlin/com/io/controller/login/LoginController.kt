package com.io.controller.login

import com.io.model.User
import com.io.util.BaseResponse
import com.io.util.ExceptionMessage

interface LoginController {

    suspend fun login(email: String, password: String): Pair<User?, ExceptionMessage?>

    suspend fun logout(id: String): Pair<Boolean , ExceptionMessage?>
}