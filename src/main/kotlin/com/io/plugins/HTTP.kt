package com.io.plugins

import io.ktor.features.*
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*

fun Application.configureHTTP() {
    install(DefaultHeaders) {
        header("X-Engine", "Ktor")
    }

}
