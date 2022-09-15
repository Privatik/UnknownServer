package com.io.data.model.user

import io.ktor.http.content.*
import kotlinx.serialization.Serializable

@Serializable
data class UserRequest(
    val email: String,
    val password: String,
    val userName: String,
    val avatar: String
) {
    fun updateBody(part: PartData.FormItem): UserRequest{
        return when(part.name){
            "email" -> copy(email = part.value)
            "password" -> copy(password = part.value)
            "userName" -> copy(userName = part.value)
            else -> this
        }
    }

    fun isBlank() : Boolean = email.isBlank() || password.isBlank() || userName.isBlank()

    companion object{

        @JvmStatic
        fun empty(): UserRequest = UserRequest(
            email = "",
            password = "",
            userName = "",
            avatar = ""
        )
    }
}
