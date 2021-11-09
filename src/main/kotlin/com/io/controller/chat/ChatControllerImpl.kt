package com.io.controller.chat

import com.io.model.Chat
import com.io.repository.chat.ChatRepository
import com.io.util.ExceptionMessage

class ChatControllerImpl(
    private val chatRepository: ChatRepository
): ChatController {

    override suspend fun createChat(chat: Chat): Pair<Chat?, ExceptionMessage?> {
        return if (chatRepository.findChatWithCurrentUser(chat.companionsId)) {
            Pair(null, ExceptionMessage.EXCEPTION_CHAT_HAS_ALREADY)
        } else {
            if (chat.companionsId.size > 1) {
                val chatCreate = chatRepository.createChat(chat)
                Pair(chatCreate, null)
            } else {
                Pair(null, ExceptionMessage.EXCEPTION_CHAT_ONE_USER)
            }
        }
    }

    override suspend fun getChat(id: String): Pair<Chat?, ExceptionMessage?> {
        val chat = chatRepository.getChat(id) ?: kotlin.run {
            return Pair(null, ExceptionMessage.EXCEPTION_CHAT_DONT_EXIST)
        }

        return Pair(chat, null)
    }
}