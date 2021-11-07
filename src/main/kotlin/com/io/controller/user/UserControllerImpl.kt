package com.io.controller.user

import com.io.model.User
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.insertOne

class UserControllerImpl(
    db: CoroutineDatabase
): UserController {

    private val users = db.getCollection<User>()

    override suspend fun createUser(user: User) {
       users.insertOne(user)
    }

    override suspend fun getAll(): List<User> = users.find().toList()
}