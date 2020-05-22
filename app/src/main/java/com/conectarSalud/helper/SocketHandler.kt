package com.conectarSalud.helper

import android.util.Log
import com.conectarSalud.services.Resources
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import org.json.JSONObject

class SocketHandler() {
    companion object {
        private const val WAITING_CALL_EVENT: String = "waiting_call"
        private const val CALL_STARTED_EVENT: String = "call_started"
        private var appSocket: Socket = IO.socket("https://connecting-health.herokuapp.com/")

        fun connectSocket(): Unit {
            appSocket.connect()
        }

        fun setInitialSocketListeners(consultationId: String, callback: ()->Unit): Unit {
            appSocket.on(Socket.EVENT_CONNECT) {
                Log.i("SocketHandler","Socket is now connected")
                sendData(consultationId)
            }
            appSocket.on(CALL_STARTED_EVENT)  { data ->
                Resources.callID = ""
                callback()
            }
        }

        private fun sendData(consultationId: String): Unit {
            val data = JSONObject()
            data.put("consultation_id", consultationId)
            appSocket.emit(WAITING_CALL_EVENT, data.toString())
        }

    }


}
