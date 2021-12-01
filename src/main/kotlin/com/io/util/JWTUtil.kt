package com.io.util

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.io.util.Constants.JWT_TOKEN_LIFE_CYCLE
import java.util.*

inline fun getAccessToken(
    email: String,
    jwtIssuer: String,
    jwtAudience: String,
    jwtSecret: String
): String =
    JWT.create()
        .withClaim("email", email)
        .withIssuer(jwtIssuer)
        .withExpiresAt(Date(System.currentTimeMillis() + JWT_TOKEN_LIFE_CYCLE))
        .withAudience(jwtAudience)
        .sign(Algorithm.HMAC256(jwtSecret))

