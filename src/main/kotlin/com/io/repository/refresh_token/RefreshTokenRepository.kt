package com.io.repository.refresh_token

import com.io.model.RefreshToken

interface RefreshTokenRepository {

    suspend fun checkValidRefreshToken(userId: String, refreshToken: String): Boolean

    suspend fun getRefreshToken(userId: String): RefreshToken
}