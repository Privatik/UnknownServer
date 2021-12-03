package com.io.data.mapper

import com.io.data.model.chat.ChatRequest
import com.io.data.model.chat.ChatResponse
import com.io.data.model.user.UserRequest
import com.io.model.Chat
import com.io.model.User

fun ChatRequest.toModel(userId:String): Chat =
    Chat(
        firstCompanionId = userId,
        secondCompanionId = this.companionId
    )

fun Chat.toResponse(): ChatResponse =
    ChatResponse(
        id = this.id,
        firstCompanionId = this.firstCompanionId,
        secondCompanionId = this.secondCompanionId,
        lastMessage = this.lastMessage?.toResponse()
    )