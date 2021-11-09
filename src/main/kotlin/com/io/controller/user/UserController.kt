package com.io.controller.user

import com.io.model.User
import com.io.util.BaseResponse

interface UserController {

    suspend fun createUser(user: User): User?

    suspend fun getUserById(id: String): User?

    suspend fun getAll(): List<User>
}