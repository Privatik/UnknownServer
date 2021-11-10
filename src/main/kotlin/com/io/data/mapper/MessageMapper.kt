package com.io.data.mapper

import com.io.data.model.message.MessageRequest
import com.io.data.model.message.MessageResponse
import com.io.model.Message
import org.bson.types.ObjectId

fun MessageRequest.toModel(): Message =
    Message(
        id = this.id ?: ObjectId().toString(),
        userId = this.userId,
        chatId = this.chatId,
        text = this.text,
        type = this.type,
        timeSend = this.timeSend,
        timeUpdate = this.timeUpdate
    )

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