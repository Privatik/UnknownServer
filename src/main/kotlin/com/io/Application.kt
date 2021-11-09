package com.io

import com.io.plugins.*
import io.ktor.application.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
fun Application.module() {
    configureRouting()
    configureSockets()
    configureSerialization()
    configureMonitoring()
    configureHTTP()
    configureSecurity()
    configureKoin()
}
