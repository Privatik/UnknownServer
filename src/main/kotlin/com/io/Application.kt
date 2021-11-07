package com.io

import com.io.di.mainModule
import io.ktor.application.*
import com.io.plugins.*
import org.koin.ktor.ext.Koin

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
    install(Koin) {
        modules(mainModule)
    }
}
