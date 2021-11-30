package com.io.service

import com.io.model.User
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import kotlin.math.log

class UserService(
    db: CoroutineDatabase
) {
    private val users = db.getCollection<User>()

    suspend fun checkUserByEmail(email: String, userId: String): Boolean {
        val user = users.findOneById(userId) ?: return false
        return user.email == email
    }
}