package com.io.data.model.login

import com.io.data.model.refresh_token.RefreshTokenResponse
import com.io.data.model.user.UserResponse
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val tokens: RefreshTokenResponse,
    val user: UserResponse
)