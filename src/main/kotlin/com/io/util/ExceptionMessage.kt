package com.io.util


enum class ExceptionMessage {
    //User
    EXCEPTION_USER_FIELD_EMPTY,
    EXCEPTION_USER_HAS_ALREADY,
    EXCEPTION_USER_DONT_EXIST,
    //Login
    EXCEPTION_LOGIN_FIELD_EMPTY,
    EXCEPTION_LOGIN_DONT_CORRECT_PASSWORD,
    EXCEPTION_USER_NOT_ACTIVE,
    EXCEPTION_USER_ACTIVE,
    //Chat
    EXCEPTION_CHAT_FIELD_EMPTY,
    EXCEPTION_CHAT_DONT_CORRECT_DATA,
    EXCEPTION_CHAT_ONE_USER,
    EXCEPTION_CHAT_FIRST_USER_DONT_EXIST,
    EXCEPTION_CHAT_SECOND_USER_DONT_EXIST,
    EXCEPTION_CHAT_DONT_EXIST,
    EXCEPTION_CHAT_HAS_ALREADY,
    //Message
    EXCEPTION_MESSAGE_DONT_CORRECT_DATA,
    EXCEPTION_MESSAGE_FIELD_EMPTY,
    EXCEPTION_MESSAGE_DONT_EXIST,
    EXCEPTION_MESSAGE_DONT_TIMEUPDATE,
    //Refresh Token
    EXCEPTION_REFRESH_TOKEN_FIELD_EMPTY,
    EXCEPTION_REFRESH_TOKEN_DEADLINE_HAS_EXPIRED,
}