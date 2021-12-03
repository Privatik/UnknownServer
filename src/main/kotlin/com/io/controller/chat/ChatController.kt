package com.io.controller.chat

import com.io.data.model.chat.ChatIdRequest
import com.io.data.model.chat.ChatRequest
import com.io.data.model.chat.ChatsPagingRequest
import com.io.model.Chat
import com.io.util.ExceptionMessage

interface ChatController {

    suspend fun createChat(userId: String, chat: ChatRequest): Pair<Chat?, ExceptionMessage?>

    suspend fun getChats(userId: String, chatPaging: ChatsPagingRequest): Pair<List<Chat>?, ExceptionMessage?>
}