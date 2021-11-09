package com.io.route

import io.ktor.routing.*

fun Route.chatRoutes() {
    route("/api"){
        post("/chat/create") {

        }
        post("/logout") {

        }
    }
}