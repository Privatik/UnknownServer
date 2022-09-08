package com.io.plugins

import com.io.route.*
import com.io.util.BASE_API
import com.io.websocket.messageSocket
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.content.*
import io.ktor.http.content.*
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.content.TextContent
import io.ktor.response.*
import io.ktor.request.*
import kotlin.random.Random

fun Application.configureRouting() {

    routing {
        val jwtIssuer = environment.config.property("jwt.domain").getString()
        val jwtAudience = environment.config.property("jwt.audience").getString()
        val jwtSecret = environment.config.property("jwt.secret").getString()

        route(BASE_API){
            loginRoutes(
                jwtAudience = jwtAudience,
                jwtSecret = jwtSecret,
                jwtIssuer = jwtIssuer
            )

            userRoutes(
                jwtAudience = jwtAudience,
                jwtSecret = jwtSecret,
                jwtIssuer = jwtIssuer
            )

            refreshTokenRoutes(
                jwtAudience = jwtAudience,
                jwtSecret = jwtSecret,
                jwtIssuer = jwtIssuer
            )

            get("/"){
                call.respond(HttpStatusCode.OK,"Hello world")
            }

            photoRoutes()
            authenticate {
//                chatRoutes()
                messageRoutes()
            }
        }
    }
}
