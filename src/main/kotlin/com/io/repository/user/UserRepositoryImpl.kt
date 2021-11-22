package com.io.repository.user

import com.io.model.User
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.setValue

class UserRepositoryImpl(
    db: CoroutineDatabase
): UserRepository {

    private val users = db.getCollection<User>()

    override suspend fun createUser(user: User): User?{
        val userFind = users.findOne(User::email eq user.email)

        return if (userFind != null){
            null
        } else {
            users.insertOne(user)
            user
        }
    }

    override suspend fun getUserById(id: String): User? = users.findOneById(id)

    override suspend fun changeActive(id: String, isActive: Boolean) {
        users.updateOneById(id = id, update = setValue(User::isActive, isActive))
    }
}