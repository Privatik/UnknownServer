package com.io.controller.chat

import com.io.data.mapper.toModel
import com.io.data.model.chat.ChatIdRequest
import com.io.data.model.chat.ChatRequest
import com.io.data.model.chat.ChatsPagingRequest
import com.io.model.Chat
import com.io.repository.chat.ChatRepository
import com.io.repository.user.UserRepository
import com.io.util.ExceptionMessage

class ChatControllerImpl(
    private val chatRepository: ChatRepository,
    private val userRepository: UserRepository
): ChatController {

    override suspend fun createChat(userId: String, chat: ChatRequest): Pair<Chat?, ExceptionMessage?> {
        return if (chat.companionId.isBlank()){
            Pair(null, ExceptionMessage.EXCEPTION_CHAT_FIELD_EMPTY)
        }
        else if (userRepository.getUserById(userId) == null){
            Pair(null, ExceptionMessage.EXCEPTION_CHAT_FIRST_USER_DONT_EXIST)
        }
        else if (userRepository.getUserById(chat.companionId) == null){
            Pair(null, ExceptionMessage.EXCEPTION_CHAT_SECOND_USER_DONT_EXIST)
        }
        else if (chatRepository.getChat(userId, chat.companionId) != null) {
            Pair(null, ExceptionMessage.EXCEPTION_CHAT_HAS_ALREADY)
        } else {
            val chatCreate = chatRepository.createChat(chat.toModel(userId))
            userRepository.addChat(userId, chatCreate.id)
            userRepository.addChat(chat.companionId, chatCreate.id)
            Pair(chatCreate, null)
        }
    }

    override suspend fun getChats(
        userId: String,
        chatPaging: ChatsPagingRequest
    ): Pair<List<Chat>?, ExceptionMessage?> {
        return if (userRepository.getUserById(userId) == null){
            Pair(null, ExceptionMessage.EXCEPTION_USER_DONT_EXIST)
        } else {
            if (chatPaging.page < 0 || chatPaging.pageSize < 1){
                Pair(null, ExceptionMessage.EXCEPTION_CHAT_DONT_CORRECT_DATA)
            } else {
                val setChatsId = userRepository.getChatsId(userId) ?: return Pair(null, ExceptionMessage.EXCEPTION_USER_DONT_EXIST)
                val listChats = chatRepository.getChats(setChatsId, chatPaging.page, chatPaging.pageSize)
                Pair(listChats, null)
            }
        }
    }
}