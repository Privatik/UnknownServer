package com.io.util

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.io.util.Constants.JWT_TOKEN_LIFE_CYCLE
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import java.util.*

fun getAccessToken(
    userId: String,
    jwtIssuer: String,
    jwtAudience: String,
    jwtSecret: String
): String =
    JWT.create()
        .withClaim("userId", userId)
        .withIssuer(jwtIssuer)
        .withExpiresAt(Date(System.currentTimeMillis() + JWT_TOKEN_LIFE_CYCLE))
        .withAudience(jwtAudience)
        .sign(Algorithm.HMAC256(jwtSecret))

val ApplicationCall.userId: String
    get() = principal<JWTPrincipal>()?.getClaim("userId", String::class) ?: ""

