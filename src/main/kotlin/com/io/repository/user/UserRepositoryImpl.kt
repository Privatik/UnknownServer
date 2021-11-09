package com.io.repository.user

import com.io.model.User
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq

class UserRepositoryImpl(
    db: CoroutineDatabase
): UserRepository {

    private val users = db.getCollection<User>()

    override suspend fun createUser(user: User): User{
        users.insertOne(user)
        return user
    }

    override suspend fun getUserById(id: String): User? = users.findOne(User::id eq id)

    override suspend fun getAll(): List<User> = users.find().toList()
}