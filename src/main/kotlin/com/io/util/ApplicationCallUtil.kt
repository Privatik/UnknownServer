package com.io.util

import com.io.data.mapper.toResponse
import io.ktor.application.*
import io.ktor.response.*
import kotlinx.serialization.Serializable

suspend inline fun <reified T> ApplicationCall.response(response: T?, exception: ExceptionMessage?){
    if (response != null){
        respond(
            BaseResponse<T>(
                isSuccessful = true,
                message = response
            )
        )
    } else {
        respond(
            BaseResponse<ExceptionMessage>(
                isSuccessful = false,
                message = exception
            )
        )
    }

}

suspend inline fun ApplicationCall.responseBoolean(response: Boolean, exception: ExceptionMessage?){
    respond(
        BaseResponse<ExceptionMessage>(
            isSuccessful = response,
            message = if (!response) exception else null
        )
    )
}