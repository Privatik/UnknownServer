package com.io.data.model.login

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val email: String,
    val password: String
) {

    fun inBlank() : Boolean = email.isBlank() || password.isBlank()
}
