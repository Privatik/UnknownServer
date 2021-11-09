package com.io.controller.user

import com.io.model.User
import com.io.util.BaseResponse
import com.io.util.ExceptionMessage

interface UserController {

    suspend fun createUser(user: User): Pair<User?, ExceptionMessage?>

    suspend fun getUserById(id: String): Pair<User?, ExceptionMessage?>

    suspend fun getAll(): List<User>
}