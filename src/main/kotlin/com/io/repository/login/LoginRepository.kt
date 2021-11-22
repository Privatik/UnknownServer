package com.io.repository.login

import com.io.model.User

interface LoginRepository {

    suspend fun login(email: String): User?

    suspend fun isCorrectPassword(user: User, password: String): Boolean

    suspend fun logout(id: String): User?
}