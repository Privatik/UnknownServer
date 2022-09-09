package com.io.controller.refresh_token

import com.io.data.model.refresh_token.RefreshTokenRequest
import com.io.model.RefreshToken
import com.io.util.ExceptionMessage
import com.io.util.Response

interface RefreshTokenController {

    suspend fun checkRefreshToken(request: RefreshTokenRequest): Response<Boolean>

    suspend fun createNewRefreshToken(userId: String): Response<RefreshToken>
}