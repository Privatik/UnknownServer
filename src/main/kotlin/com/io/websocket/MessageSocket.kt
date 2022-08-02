package com.io.websocket

import io.ktor.http.cio.websocket.*
import io.ktor.routing.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import java.util.Collections

fun Route.messageSocket(){
    val sessions = WebSocketSessions()
    webSocket("/message") {
        sessions.add(this)
        for (frame in incoming) {
            when (frame) {
                is Frame.Text -> {
                    try {
                        val text = frame.readText()
                        sessions.sendAll(Frame.Text(text))
                    } finally {}
                }
                is Frame.Binary -> {
                    try {
//                        val text = frame.read
//                        println("onMessage")
//                        connections.forEach {
//                            it.send(Frame.Text(text))
//                        }
                    } finally {}
                }
                is Frame.Close,
                is Frame.Ping,
                is Frame.Pong -> {}
            }
        }
    }
}