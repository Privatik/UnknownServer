package com.io.controller.user

import com.io.model.User

interface UserController {

    suspend fun createUser(user: User)

    suspend fun getAll():List<User>
}