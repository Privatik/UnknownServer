package com.io.data.model.user

import kotlinx.serialization.Serializable

@Serializable
data class UserRequest(
    val email: String,
    val password: String
) {
    fun inBlank() : Boolean = email.isBlank() || password.isBlank()
}

@Serializable
data class UserIdRequest(
    val id: String
)
