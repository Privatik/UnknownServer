package com.io.repository.login

import com.io.model.User
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.setValue

class LoginRepositoryImpl(
    db: CoroutineDatabase
): LoginRepository {

    private val users = db.getCollection<User>()

    override suspend fun login(email: String): User? {
        val user = users.findOne(User::email eq email)
            ?: kotlin.run {
                return null
            }

        return user
    }

    override suspend fun isCorrectPassword(user: User, password: String):Boolean {
        return users.findOne(User::email eq user.email, User::password eq password) != null
    }

    override suspend fun logout(id: String): User? {
        val user = users.findOne(User::id eq id)
            ?: kotlin.run {
                return null
            }

        return user
    }
}