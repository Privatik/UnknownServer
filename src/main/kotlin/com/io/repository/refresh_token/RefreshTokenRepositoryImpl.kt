package com.io.repository.refresh_token

import com.io.model.RefreshToken
import com.io.model.User
import com.io.util.Constants.REFRESH_TOKEN_LIFE_CYCLE
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.insertOne
import org.litote.kmongo.eq
import java.security.SecureRandom
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

    override suspend fun getRefreshToken(userId: String): RefreshToken {
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


    private suspend fun createNewRefreshToken(userId: String): RefreshToken {
        val token = ""

        val model =
            RefreshToken(
                userId = userId,
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