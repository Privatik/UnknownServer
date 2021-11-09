package com.io.plugins

import com.io.di.controllerModule
import com.io.di.dbModule
import com.io.di.repositoryModule
import io.ktor.application.*
import org.koin.ktor.ext.Koin

fun Application.configureKoin() {
    install(Koin) {
        modules(
            dbModule,
            repositoryModule,
            controllerModule
        )
    }
}