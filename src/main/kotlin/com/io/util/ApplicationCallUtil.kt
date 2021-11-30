package com.io.util

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.io.data.model.user.UserResponse
import com.io.model.User
import com.io.plugins.email
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.util.pipeline.*
import java.util.*

suspend inline fun <reified T> ApplicationCall.response(response: T?, exception: ExceptionMessage?){
    if (response != null){
        respond(
            HttpStatusCode.OK,
            BaseResponse<T>(
                isSuccessful = true,
                message = response
            )
        )
    } else {
        respond(
            HttpStatusCode.OK,
            BaseResponse<ExceptionMessage>(
                isSuccessful = false,
                message = exception
            )
        )
    }

}

suspend inline fun ApplicationCall.responseBoolean(response: Boolean, exception: ExceptionMessage?){
    respond(
        HttpStatusCode.OK,
        BaseResponse<ExceptionMessage>(
            isSuccessful = response,
            message = if (!response) exception else null
        )
    )
}

suspend fun PipelineContext<Unit, ApplicationCall>.ifEmailBelongsToUser(
    userId: String,
    validateEmail: suspend (email: String, userId: String) -> Boolean,
    onSuccess: suspend () -> Unit
) {
    val isEmailByUser = validateEmail(
        call.principal<JWTPrincipal>()?.email ?: "",
        userId
    )
    if(isEmailByUser) {
        onSuccess()
    } else {
       // call.respond(HttpStatusCode.Unauthorized)
    }
}