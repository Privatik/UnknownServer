package com.io.data.mapper

import com.io.data.model.message.MessageResponse
import com.io.model.Message

fun Message.toResponse(): MessageResponse =
    MessageResponse(
        id = this.id,
        type = this.type,
        text = this.text,
        timeSend = this.timeSend,
        timeUpdate = this.timeUpdate,
        userId = this.userId,
        chatId = this.chatId
    )