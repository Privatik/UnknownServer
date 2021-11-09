package com.io.route

import io.ktor.routing.*

fun Route.messageRoutes() {
    route("/api"){
        post("/login") {

        }
        post("/logout") {

        }
    }
}