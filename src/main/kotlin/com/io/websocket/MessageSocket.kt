package com.io.websocket

import io.ktor.http.cio.websocket.*
import io.ktor.routing.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import java.util.Collections

fun Routing.messageSocket(){
    val connections = Collections.synchronizedSet(HashSet<DefaultWebSocketServerSession>())
    webSocket("/message") {
        val thisSocket = this
        connections += thisSocket
        for (frame in incoming) {
            when (frame) {
                is Frame.Text -> {
                    try {
                        val text = frame.readText()
                        println("onMessage")
                        connections.forEach {
                            it.send(Frame.Text(text))
                        }
                    } finally {
                        connections -= thisSocket
                    }
                }
                is Frame.Binary -> {
                    try {
//                        val text = frame.read
//                        println("onMessage")
//                        connections.forEach {
//                            it.send(Frame.Text(text))
//                        }
                    } finally {
                        connections -= thisSocket
                    }
                }
                is Frame.Close,
                is Frame.Ping,
                is Frame.Pong -> {}
            }
        }
    }
}