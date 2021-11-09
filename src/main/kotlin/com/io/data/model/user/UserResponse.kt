package com.io.data.model.user

import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val id: String,
    val email: String,
)
