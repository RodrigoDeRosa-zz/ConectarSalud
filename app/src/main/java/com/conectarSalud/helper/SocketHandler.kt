package com.conectarSalud.helper


import android.util.Log
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import org.json.JSONObject




class SocketHandler() {
    companion object {
        private var appSocket: Socket = IO.socket("https://connecting-health.herokuapp.com/")

        init {
            appSocket.on(Socket.EVENT_CONNECT) {
                Log.i("SocketHandler","Socket is now connected")
            }
            appSocket.on("message") {
                Log.i("SocketHandler","Message event is received")
            }
        }

        fun connectSocket(): Unit {
            appSocket.connect()
        }

        fun sendData(userId: String): Unit {
            val data = JSONObject().put("user_id", userId)
            appSocket.emit("message", data.toString())
        }
    }


}
