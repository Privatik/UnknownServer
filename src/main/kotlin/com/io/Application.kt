package com.io

import com.io.model.User
import com.io.plugins.*
import io.ktor.application.*
import kotlin.reflect.full.createType
import kotlin.reflect.full.declaredMembers
import kotlin.reflect.full.defaultType
import kotlin.reflect.full.starProjectedType

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
fun Application.module() {
    configureKoin()
    configureSecurity()
    configureRouting()
    configureSockets()
    configureHTTP()
    configureSerialization()
    configureMonitoring()
    configureStatic()
}
