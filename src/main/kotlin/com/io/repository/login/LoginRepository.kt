package com.io.repository.login

import com.io.model.User

interface LoginRepository {

    suspend fun login(): User?

    suspend fun logout(): Boolean
}