package com.io.controller.user

import com.io.data.model.user.UserRequest
import com.io.model.User
import com.io.util.ExceptionMessage
import com.io.util.Response

interface UserController {

    suspend fun createUser(user: UserRequest): Response<User>

    suspend fun getUserById(userId: String): Response<User>

    suspend fun createRefreshToken(userId: String): String
}