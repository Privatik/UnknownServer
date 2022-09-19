package com.io.websocket

import io.ktor.http.cio.websocket.*
import io.ktor.network.sockets.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class WebSocketSessions private constructor(){
    private val expiredAtMap = ConcurrentHashMap<DefaultWebSocketServerSession, Long>()

    suspend fun sendAll(frame: Frame){
        expiredAtMap.forEach { (session, expired) ->
            try {
                val time = System.currentTimeMillis() / 1000
                println("Socket try send $time  $expired}")
                if (time < expired){
                    session.send(frame)
                } else {
                    println("Socket close after auth")
                    session.close(CloseReason(CloseReason.Codes.CANNOT_ACCEPT, "You token is expired"))
                    remove(session)
                }
            } catch (e: ClosedReceiveChannelException){
                println("Socket close $e")
                remove(session)
            }
        }
    }

    fun add(session: DefaultWebSocketServerSession, expiredAt: Long){
        expiredAtMap[session] = expiredAt
    }

    fun remove(session: DefaultWebSocketServerSession){
        expiredAtMap.remove(session)
    }

    companion object {
        @Volatile
        private var webSocketSessions: WebSocketSessions? = null
        private val lock = Any()

        @JvmStatic
        fun getInstance(): WebSocketSessions{
            if (webSocketSessions == null){
                synchronized(lock){
                    if (webSocketSessions == null){
                        webSocketSessions = WebSocketSessions()
                    }
                }
            }
            return webSocketSessions!!
        }
    }

}