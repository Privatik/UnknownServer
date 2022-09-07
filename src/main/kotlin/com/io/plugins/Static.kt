package com.io.plugins

import com.io.util.Constants
import io.ktor.application.*
import io.ktor.http.content.*
import io.ktor.routing.*

fun Application.configureStatic(){
    routing {
        static("/${Constants.PHOTO_FILE}") {

        }
    }
}