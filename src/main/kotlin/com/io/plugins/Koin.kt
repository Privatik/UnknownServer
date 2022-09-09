package com.io.plugins

import com.io.di.controllerModule
import com.io.di.dbModule
import com.io.di.repositoryModule
import com.io.di.serviceModule
import com.io.secutiry.token.TokenConfig
import com.io.util.Constants
import io.ktor.application.*
import org.koin.dsl.module
import org.koin.ktor.ext.Koin
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

fun Application.configureKoin() {
    install(Koin) {
        modules(
            dbModule,
            serviceModule,
            repositoryModule,
            controllerModule,
        )
    }
}