package com.io.data.mapper

import com.io.data.model.refresh_token.RefreshTokenResponse
import com.io.model.RefreshToken

fun RefreshToken.toResponse(accessToken: String): RefreshTokenResponse =
    RefreshTokenResponse(
        accessToken = accessToken,
        refreshToken = this.refreshToken
    )