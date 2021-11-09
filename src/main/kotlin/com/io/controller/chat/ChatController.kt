package com.io.controller.chat

import com.io.model.Chat
import com.io.util.ExceptionMessage

interface ChatController {

    suspend fun createChat(chat: Chat): Pair<Chat?, ExceptionMessage?>

    suspend fun getChat(id: String): Pair<Chat?, ExceptionMessage?>
}