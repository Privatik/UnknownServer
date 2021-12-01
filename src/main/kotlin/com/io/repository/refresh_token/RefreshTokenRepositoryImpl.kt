package com.io.repository.refresh_token

import com.io.model.RefreshToken
import com.io.model.User
import com.io.util.Constants.REFRESH_TOKEN_LIFE_CYCLE
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.insertOne
import org.litote.kmongo.eq
import java.util.*

class RefreshTokenRepositoryImpl(
    db: CoroutineDatabase
): RefreshTokenRepository {

    private val tokens = db.getCollection<RefreshToken>()

    override suspend fun checkValidRefreshToken(userId: String, refreshToken: String): Boolean {
        val token = tokens.findOne(RefreshToken::userId eq userId, RefreshToken::refreshToken eq refreshToken)
            ?: return false

        return System.currentTimeMillis() < token.timestamp
    }

    override suspend fun getRefreshToken(userId: String): RefreshToken? {
        val token = tokens.findOne(RefreshToken::userId eq userId) ?: kotlin.run {
            return createNewRefreshToken(userId)
        }

        return if (checkValidRefreshToken(userId, token.refreshToken)){
            token
        } else {
            deleteTokens(userId)
            createNewRefreshToken(userId)
        }
    }

    override suspend fun getRefreshToken(userId: String, email: String): RefreshToken {
        val token = tokens.findOne(RefreshToken::userId eq userId, RefreshToken::email eq email) ?: kotlin.run {
            return createNewRefreshToken(userId, email)
        }

        return if (checkValidRefreshToken(userId, token.refreshToken)){
            token
        } else {
            deleteTokens(userId)
            createNewRefreshToken(userId, email)
        }
    }

    private suspend fun createNewRefreshToken(userId: String, email: String): RefreshToken {
        val token = UUID.randomUUID().toString()

        val model =
            RefreshToken(
                userId = userId,
                email = email,
                refreshToken = token,
                timestamp = System.currentTimeMillis() + REFRESH_TOKEN_LIFE_CYCLE
            )
        tokens.insertOne(
           model
        )

        return model
    }

    private suspend fun createNewRefreshToken(userId: String): RefreshToken? {
        val token = UUID.randomUUID().toString()
        val oldToken = tokens.findOne(RefreshToken::userId eq userId) ?: return null

        val model =
            RefreshToken(
                userId = userId,
                email = oldToken.email,
                refreshToken = token,
                timestamp = System.currentTimeMillis() + REFRESH_TOKEN_LIFE_CYCLE
            )

        tokens.insertOne(model)

        return model
    }

    private suspend fun deleteTokens(userId: String){
        tokens.deleteMany(RefreshToken::userId eq userId)
    }
}