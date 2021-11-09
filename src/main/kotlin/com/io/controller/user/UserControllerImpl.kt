package com.io.controller.user

import com.io.model.User
import com.io.repository.user.UserRepository
import com.io.util.BaseResponse
import com.io.util.ExceptionMessage
import io.ktor.application.*
import io.ktor.response.*
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.insertOne

class UserControllerImpl(
    private val userRepository: UserRepository
): UserController {

    override suspend fun createUser(user: User): Pair<User?, ExceptionMessage?> {
        if(user.email.isBlank() || user.password.isBlank()) {
            return Pair(null, ExceptionMessage.EXCEPTION_USER_FIELD_EMPTY)
        }

        val userFind = userRepository.createUser(user)
        return if (userFind != null){
            Pair(userFind, null)
        } else {
            Pair(null, ExceptionMessage.EXCEPTION_USER_HAS_ALREADY)
        }
    }

    override suspend fun getUserById(id: String): Pair<User?, ExceptionMessage?> {
        val user = userRepository.getUserById(id) ?: kotlin.run {
            return Pair(null, ExceptionMessage.EXCEPTION_USER_DONT_EXIST)
        }

        return Pair(user, null)
    }

    override suspend fun getAll(): List<User> = userRepository.getAll()
}