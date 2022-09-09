package com.io.data.model.user

import io.ktor.http.content.*
import kotlinx.serialization.Serializable
import java.io.File

@Serializable
data class UserRequest(
    val email: String,
    val password: String,
    val nickName: String,
    val avatar: String
) {
    fun updateBody(part: PartData.FormItem): UserRequest{
        return when(part.name){
            "email" -> copy(email = part.value)
            "password" -> copy(password = part.value)
            "nickName" -> copy(nickName = part.value)
            else -> this
        }
    }

    fun isBlank() : Boolean = email.isBlank() || password.isBlank() || nickName.isBlank()

    companion object{

        @JvmStatic
        fun empty(): UserRequest = UserRequest(
            email = "",
            password = "",
            nickName = "",
            avatar = ""
        )
    }
}
