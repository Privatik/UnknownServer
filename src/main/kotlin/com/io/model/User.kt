package com.io.model

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class User(
    @BsonId
    val id: String = ObjectId().toString(),
    val email: String,
    val password: String,
    val chatsIds: MutableSet<String> = mutableSetOf(),
    val isActive: Boolean = false,
    val height: Int? = null,
    val weight: Int? = null,
    val locate: String? = null,
    val sex: Sex,
    val birthDay: Long
){
    @Serializable
    enum class Sex{
        UNKNOWN,
        MAN,
        WOMAN
    }

    companion object{

        fun parseToSex(sexN: Int):Sex =
            when (sexN){
                0 -> Sex.MAN
                1 -> Sex.WOMAN
                else -> Sex.UNKNOWN
            }

    }
}
