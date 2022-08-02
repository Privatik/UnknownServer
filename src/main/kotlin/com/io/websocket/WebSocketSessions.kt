package com.io.websocket

import io.ktor.http.cio.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import java.util.*

class WebSocketSessions {
    private val connections = Collections.synchronizedSet(HashSet<DefaultWebSocketServerSession>())

    suspend fun sendAll(frame: Frame){
        val removeSet = HashSet<DefaultWebSocketServerSession>()
        connections.forEach {
            try {
                it.send(frame)
            } catch (e: ClosedReceiveChannelException){
                removeSet += it
            }
        }
        connections -= removeSet
    }

    fun add(session: DefaultWebSocketServerSession){
        connections += session
    }

    fun remove(session: DefaultWebSocketServerSession){
        connections -= session
    }

}