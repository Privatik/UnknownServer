package com.io.plugins

import com.io.route.chatRoutes
import com.io.route.loginRoutes
import com.io.route.messageRoutes
import com.io.route.userRoutes
import com.io.util.BASE_API
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.content.*
import io.ktor.http.content.*
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*

fun Application.configureRouting() {

    routing {
        route(BASE_API){
            userRoutes()
            loginRoutes()
            chatRoutes()
            messageRoutes()
        }
    }
}
