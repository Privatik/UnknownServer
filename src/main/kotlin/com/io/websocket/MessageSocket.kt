package com.io.websocket

import com.io.secutiry.token.timeExpireToken
import com.io.util.SingleChatApiConstant
import io.ktor.http.cio.websocket.*
import io.ktor.routing.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import java.util.Collections

fun Route.messageSocket(){
    val sessions = WebSocketSessions.getInstance()

    webSocket(SingleChatApiConstant.MESSAGE) {
        sessions.add(this, call.timeExpireToken)
    }
}