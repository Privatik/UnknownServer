package com.io.data.model.user

import com.io.model.User
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val id: String,
    val email: String,
    val height: Int? = null,
    val weight: Int? = null,
    val locate: String? = null,
    val sex: User.Sex,
    val birthDay: Long
)
