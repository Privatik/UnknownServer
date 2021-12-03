package com.io.data.model.user

import kotlinx.serialization.Serializable

@Serializable
data class UserRequest(
    val email: String,
    val password: String
) {
    fun isBlank() : Boolean = email.isBlank() || password.isBlank()
}
