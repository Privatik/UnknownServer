package com.io.repository.login

import com.io.model.User
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq

class LoginRepositoryImpl(
    db: CoroutineDatabase
): LoginRepository {

    private val users = db.getCollection<User>()

    override suspend fun login(email: String, password: String): User?  =
        users.findOne(User::email eq email, User::password eq password)

    override suspend fun logout(id: String): Boolean {
       // users.updateOneById(id = id, update = )
        return true
    }
}