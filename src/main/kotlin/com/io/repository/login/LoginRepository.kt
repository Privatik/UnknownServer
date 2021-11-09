package com.io.repository.login

import com.io.model.User

interface LoginRepository {

    suspend fun login(email: String, password: String): User?

    suspend fun logout(id: String): User?
}