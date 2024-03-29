package com.io.data.model.user

import com.io.model.User
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val id: String,
    val email: String,
    val userName: String,
    val avatar: String
)
