package com.io.controller.user

import com.io.model.User
import com.io.repository.user.UserRepository
import com.io.util.BaseResponse
import io.ktor.application.*
import io.ktor.response.*
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.insertOne

class UserControllerImpl(
    private val userRepository: UserRepository
): UserController {

    override suspend fun createUser(user: User): User? {
        if(user.email.isBlank() || user.password.isBlank()) {
            return null
        }
        return userRepository.createUser(user)
    }

    override suspend fun getUserById(id: String): User? = userRepository.getUserById(id)

    override suspend fun getAll(): List<User> = userRepository.getAll()
}