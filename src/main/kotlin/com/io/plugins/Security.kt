package com.io.plugins

import io.ktor.auth.*
import io.ktor.util.*
import io.ktor.auth.jwt.*
import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.io.secutiry.token.TokenConfig
import com.io.util.Constants
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.request.*
import org.koin.core.qualifier.named
import org.koin.ktor.ext.inject

fun Application.configureSecurity(
    accessConfig: TokenConfig,
    refreshConfig: TokenConfig
) {

    authentication {
        jwt(Constants.ACCESS_TOKEN) {
            realm = this@configureSecurity.environment.config.property("jwt.realm").getString()
            verifier(
                JWT
                    .require(Algorithm.HMAC256(accessConfig.secret))
                    .withAudience(accessConfig.audience)
                    .withIssuer(accessConfig.issuer)
                    .build()
            )
            validate { credential ->
                if (credential.payload.audience.contains(accessConfig.audience)) JWTPrincipal(credential.payload) else null
            }
        }
        jwt(Constants.REFRESH_TOKEN) {
            realm = this@configureSecurity.environment.config.property("jwt.realm").getString()
            verifier(
                JWT
                    .require(Algorithm.HMAC256(refreshConfig.secret))
                    .withAudience(refreshConfig.audience)
                    .withIssuer(refreshConfig.issuer)
                    .build()
            )
            validate { credential ->
                if (credential.payload.audience.contains(refreshConfig.audience)) JWTPrincipal(credential.payload) else null
            }
            challenge { _, _ ->
                call.respond(HttpStatusCode.Forbidden, "Refresh token is invalid)")
            }
        }
    }
}
