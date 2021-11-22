package com.io.controller.chat

import com.io.data.model.chat.ChatIdRequest
import com.io.data.model.chat.ChatRequest
import com.io.model.Chat
import com.io.util.ExceptionMessage

interface ChatController {

    suspend fun createChat(chat: ChatRequest): Pair<Chat?, ExceptionMessage?>

    suspend fun getChat(chat: ChatIdRequest): Pair<Chat?, ExceptionMessage?>
}