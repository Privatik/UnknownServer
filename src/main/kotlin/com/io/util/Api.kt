package com.io.util

import org.koin.core.logger.MESSAGE

const val BASE_API = "/api"

object UserApiConstant{

    const val USER_CREATE =  "/user/create"
    const val USER_GET_BY_ID =  "/user"
}

object SingleChatApiConstant{

    const val MESSAGE =  "/chat/message"
}

object LoginApiConstant{

    const val LOGIN =  "/login"
    const val LOGOUT = "/logout"
}

object ChatApiConstant{

    const val CHAT_CREATE = "/chat/create"
    const val CHAT_GET_BY_ID = "/chat"
    const val CHATS_GET_BY_USER_ID = "/chats"
}

object MessageApiConstant{

    const val MESSAGES_GEt_BY_CHAT_ID = "/messages"
    const val MESSAGES_GEt_BY_ID = "/message"
    const val MESSAGE_SEND = "/message/send"
    const val MESSAGE_UPDATE = "/message/update"
    const val MESSAGE_DELETE = "/message/delete"
}

object PhotoApiConstant{

    const val GET_PHOTO_BY_NAME = "/photo"
}

object RefreshApiConstant{

    const val REFRESH_TOKEN = "/refresh_token"
}