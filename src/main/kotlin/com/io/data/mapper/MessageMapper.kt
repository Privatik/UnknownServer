package com.io.data.mapper

import com.io.data.model.message.MessageRequest
import com.io.data.model.message.MessageResponse
import com.io.data.model.message.MessageTypeRequest
import com.io.data.model.message.MessageTypeResponse
import com.io.model.Message
import com.io.model.MessageType
import org.bson.types.ObjectId

fun MessageRequest.toModel(userName: String): Message =
    Message(
        id = if (this.id == null) ObjectId() else ObjectId(this.id),
        userId = this.userId!!,
        userName = userName,
        text = this.text,
        type = this.type.toModel(),
        timeSend = this.timeSend,
        timeUpdate = this.timeUpdate
    )

fun Message.toResponse(): MessageResponse =
    MessageResponse(
        id = this.id.toString(),
        type = this.type.toResponse(),
        text = this.text,
        timeSend = this.timeSend!!,
        timeUpdate = this.timeUpdate,
        userId = this.userId,
        userName = this.userName
    )

fun MessageType.toResponse(): MessageTypeResponse =
    when (this){
        MessageType.IMAGE -> MessageTypeResponse.IMAGE
        MessageType.TEXT -> MessageTypeResponse.TEXT
    }

fun MessageTypeRequest.toModel(): MessageType =
    when (this){
        MessageTypeRequest.IMAGE -> MessageType.IMAGE
        MessageTypeRequest.TEXT -> MessageType.TEXT
    }