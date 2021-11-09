package com.io.util

const val BASE_API = "/api"

object UserApiConstant{

    const val USER_CREATE =  "/user/create"
    const val USER_GET_BY_ID =  "/user"
}

object LoginApiConstant{

    const val LOGIN =  "/login"
    const val LOGOUT = "/logout"
}

object ChatApiConstant{

    const val CHAT_CREATE = "/chat/create"
    const val CHAT_GET_BY_ID = "/chat"
}

object MessageApiConstant{

    const val MESSAGE_SEND = "/message/send"
    const val MESSAGE_UPDATE = "/message/update"
    const val MESSAGE_DELETE = "/message/delete"
}