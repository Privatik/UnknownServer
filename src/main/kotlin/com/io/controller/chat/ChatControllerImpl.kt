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

    override suspend fun createChat(chat: ChatRequest): Pair<Chat?, ExceptionMessage?> {
        return if (chat.isBlank()){
            Pair(null, ExceptionMessage.EXCEPTION_CHAT_FIELD_EMPTY)
        }
        else if (userRepository.getUserById(chat.firstCompanionId) == null){
            Pair(null, ExceptionMessage.EXCEPTION_CHAT_FIRST_USER_DONT_EXIST)
        }
        else if (userRepository.getUserById(chat.secondCompanionId) == null){
            Pair(null, ExceptionMessage.EXCEPTION_CHAT_SECOND_USER_DONT_EXIST)
        }
        else if (chatRepository.getChat(chat.firstCompanionId, chat.secondCompanionId) != null) {
            Pair(null, ExceptionMessage.EXCEPTION_CHAT_HAS_ALREADY)
        } else {
            val chatCreate = chatRepository.createChat(chat.toModel())
            userRepository.addChat(chat.firstCompanionId, chatCreate.id)
            userRepository.addChat(chat.secondCompanionId, chatCreate.id)
            Pair(chatCreate, null)
        }
    }

    override suspend fun getChat(chat: ChatIdRequest): Pair<Chat?, ExceptionMessage?> {
        if (chat.id.isBlank()) {
            return  Pair(null, ExceptionMessage.EXCEPTION_CHAT_FIELD_EMPTY)
        }
        val chatFind = chatRepository.getChat(chat.id) ?: kotlin.run {
            return Pair(null, ExceptionMessage.EXCEPTION_CHAT_DONT_EXIST)
        }

        return Pair(chatFind, null)
    }

    override suspend fun getChats(chatPaging: ChatsPagingRequest): Pair<List<Chat>?, ExceptionMessage?> {
        return if (userRepository.getUserById(chatPaging.userId) == null){
            Pair(null, ExceptionMessage.EXCEPTION_USER_DONT_EXIST)
        } else {
            if (chatPaging.page < 0 || chatPaging.pageSize < 1){
                Pair(null, ExceptionMessage.EXCEPTION_CHAT_DONT_CORRECT_DATA)
            } else {
                val setChatsId = userRepository.getChatsId(chatPaging.userId) ?: return Pair(null, ExceptionMessage.EXCEPTION_USER_DONT_EXIST)
                val listChats = chatRepository.getChats(setChatsId, chatPaging.page, chatPaging.pageSize)
                Pair(listChats, null)
            }
        }
    }
}