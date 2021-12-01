package com.io.data.mapper

import com.io.data.model.login.LoginResponse
import com.io.model.RefreshToken
import com.io.model.User

fun User.toLoginResponse(accessToken: String, refreshToken: String) = LoginResponse(
    accessToken = accessToken,
    refreshToken = refreshToken,
    user = this.toResponse()
)