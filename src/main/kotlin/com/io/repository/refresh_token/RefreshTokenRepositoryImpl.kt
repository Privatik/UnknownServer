package com.io.repository.refresh_token

import com.io.model.RefreshToken
import com.io.model.User
import com.io.secutiry.hashing.HashingService
import com.io.secutiry.hashing.SaltedHash
import com.io.util.Constants.REFRESH_TOKEN_LIFE_CYCLE
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.insertOne
import org.litote.kmongo.eq
import java.security.SecureRandom
import java.util.*

class RefreshTokenRepositoryImpl(
    db: CoroutineDatabase,
    private val hashingService: HashingService
): RefreshTokenRepository {

    private val tokens = db.getCollection<RefreshToken>()

    override suspend fun checkValidRefreshToken(userId: String, refreshToken: String): Boolean {
        val token = tokens.findOne(RefreshToken::refreshToken eq refreshToken)
            ?: return false

        val isValidToken =
            hashingService.verify(userId, SaltedHash(token.refreshToken, token.salt)) && System.currentTimeMillis() < token.timestamp

        if (!isValidToken){
            deleteToken(token.refreshToken)
        }

        return isValidToken
    }

    override suspend fun getRefreshToken(userId: String): RefreshToken {
        return createNewRefreshToken(userId)
    }

    private suspend fun deleteToken(refreshToken: String) {
        tokens.deleteMany(RefreshToken::refreshToken eq refreshToken)
    }

    private suspend fun createNewRefreshToken(userId: String): RefreshToken {
        val token = hashingService.generateSaltedHash(userId)

        val model =
            RefreshToken(
                refreshToken = token.hash,
                salt = token.salt,
                timestamp = System.currentTimeMillis() + REFRESH_TOKEN_LIFE_CYCLE
            )

        tokens.insertOne(model)

        return model
    }
}