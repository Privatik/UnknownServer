package com.io

import com.io.model.User
import com.io.plugins.*
import com.io.secutiry.token.TokenConfig
import com.io.util.Constants
import io.ktor.application.*
import kotlin.reflect.full.createType
import kotlin.reflect.full.declaredMembers
import kotlin.reflect.full.defaultType
import kotlin.reflect.full.starProjectedType

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
fun Application.module() {
    val accessTokenConfig = TokenConfig(
        issuer = environment.config.property("jwt.issuer").getString(),
        audience = environment.config.property("jwt.audience").getString(),
        expiresIn = Constants.JWT_TOKEN_LIFE_CYCLE,
        secret = "secret"
    )
    val refreshTokenConfig = TokenConfig(
        issuer = environment.config.property("jwt.issuer").getString(),
        audience = environment.config.property("jwt.audience").getString(),
        expiresIn = Constants.REFRESH_TOKEN_LIFE_CYCLE,
        secret = "secret"
    )

    configureKoin()
    configureSecurity(accessTokenConfig,refreshTokenConfig)
    configureRouting(accessTokenConfig,refreshTokenConfig)
    configureSockets()
    configureHTTP()
    configureSerialization()
    configureMonitoring()
    configureStatic()
}
