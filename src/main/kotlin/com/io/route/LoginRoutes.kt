package com.io.route

import io.ktor.routing.*

fun Route.loginRoutes() {
    route("/api"){
        post("/login") {

        }
        post("/logout") {

        }
    }
}