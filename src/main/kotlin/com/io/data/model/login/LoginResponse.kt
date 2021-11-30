package com.io.data.model.login

import com.io.data.model.user.UserResponse
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val token: String,
    val user: UserResponse
)