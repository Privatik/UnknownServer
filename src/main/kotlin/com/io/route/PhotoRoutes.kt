package com.io.route

import com.io.util.PhotoApiConstant
import com.io.util.getPhoto
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.photoRoutes(){

    get("${PhotoApiConstant.GET_PHOTO_BY_NAME}/{name}"){
        val filename = call.parameters["name"]!!

        val file = getPhoto(filename)
        if(file.exists()) {
            call.respondFile(file)
        }
        else call.respond(HttpStatusCode.NotFound)
    }
}