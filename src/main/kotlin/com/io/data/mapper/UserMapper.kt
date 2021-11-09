package com.io.data.mapper

import com.io.data.model.user.UserRequest
import com.io.data.model.user.UserResponse
import com.io.model.User

fun UserRequest.toModel(): User =
    User(
        email = this.email,
        password = this.password
    )

fun User.toResponse(): UserResponse =
    UserResponse(
        id = this.id,
        email = this.email
    )