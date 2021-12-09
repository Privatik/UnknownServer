package com.io.data.mapper

import com.io.data.model.user.UserRequest
import com.io.data.model.user.UserResponse
import com.io.model.User

fun UserRequest.toModel(): User =
    User(
        email = this.email,
        password = this.password,
        sex = User.parseToSex(this.sex),
        birthDay = this.birthDay
    )

fun User.toResponse(): UserResponse =
    UserResponse(
        id = this.id,
        email = this.email,
        height = this.height,
        weight = this.weight,
        locate = this.locate,
        sex = this.sex,
        birthDay = this.birthDay
    )