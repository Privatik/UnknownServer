package com.io.util

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*

suspend inline fun <reified T> ApplicationCall.response(response: T?, exception: ExceptionMessage?){
    val isResponse = response != null
    respond(
        HttpStatusCode.OK,
        BaseResponse(
            isSuccessful = isResponse,
            message = if (isResponse) response else exception
        )
    )
}

suspend inline fun ApplicationCall.responseBoolean(response: Boolean, exception: ExceptionMessage?){
    respond(
        HttpStatusCode.OK,
        BaseResponse(
            isSuccessful = response,
            message = if (!response) exception else null
        )
    )
}