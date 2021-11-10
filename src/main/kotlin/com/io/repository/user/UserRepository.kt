package com.io.repository.user

import com.io.model.User
import com.io.util.BaseResponse

interface UserRepository {
    suspend fun createUser(user: User): User?

    suspend fun getUserById(id: String): User?

    suspend fun changeActive(id: String, isActive: Boolean)
}