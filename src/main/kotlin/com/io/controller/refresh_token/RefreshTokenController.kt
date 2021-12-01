package com.io.controller.refresh_token

import com.io.data.model.refresh_token.RefreshTokenRequest
import com.io.model.RefreshToken
import com.io.util.ExceptionMessage

interface RefreshTokenController {

    suspend fun checkRefreshToken(request: RefreshTokenRequest): Pair<Boolean, ExceptionMessage?>

    suspend fun createNewRefreshToken(userId: String): Pair<RefreshToken?,ExceptionMessage?>
}