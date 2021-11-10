package com.io.controller.user

import com.io.data.model.user.UserRequest
import com.io.model.User
import com.io.util.BaseResponse
import com.io.util.ExceptionMessage

interface UserController {

    suspend fun createUser(user: UserRequest): Pair<User?, ExceptionMessage?>

    suspend fun getUserById(id: String): Pair<User?, ExceptionMessage?>
}