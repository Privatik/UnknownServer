package com.io.plugins

import com.io.route.*
import com.io.secutiry.token.TokenConfig
import com.io.util.BASE_API
import com.io.util.Constants
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

fun Application.configureRouting(
    accessTokenConfig: TokenConfig,
    refreshTokenConfig: TokenConfig
) {

    routing {

        route(BASE_API){
            loginRoutes(accessTokenConfig, refreshTokenConfig)

            userRoutes(accessTokenConfig, refreshTokenConfig)

            authenticate(Constants.REFRESH_TOKEN){
                refreshTokenRoutes(accessTokenConfig, refreshTokenConfig)
            }


            authenticate(Constants.ACCESS_TOKEN) {
                get("/valid"){
                    call.respond(HttpStatusCode.OK)
                }

                photoRoutes()
                chatRoutes()
                messageRoutes()
            }
        }
    }
}
