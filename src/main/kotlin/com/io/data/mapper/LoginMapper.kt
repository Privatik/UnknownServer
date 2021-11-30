package com.io.data.mapper

import com.io.data.model.login.LoginResponse
import com.io.model.User

fun User.toLoginResponse(token: String) = LoginResponse(
    token = token,
    user = this.toResponse()
)