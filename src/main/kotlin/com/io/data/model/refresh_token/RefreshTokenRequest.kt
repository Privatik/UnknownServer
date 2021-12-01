package com.io.data.model.refresh_token

import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenRequest(
    val userId: String,
    val refresh_token: String,
) {
    fun isBlank() : Boolean = userId.isBlank() || refresh_token.isBlank()
}