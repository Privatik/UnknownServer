package com.io.data.model.user

import kotlinx.serialization.Serializable

@Serializable
data class UserRequest(
    val email: String,
    val password: String,
    val birthDay: Long,
    val sex: Int,
) {
    fun isBlank() : Boolean = email.isBlank() || password.isBlank() || sex !in 0..1 || birthDay == 0L
}
