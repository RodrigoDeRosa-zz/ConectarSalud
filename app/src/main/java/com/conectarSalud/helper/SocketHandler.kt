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
        private const val REMAINING_TIME: String = "remaining_time"
        private var appSocket: Socket = IO.socket("https://connecting-health.herokuapp.com/")

        fun connectSocket(): Unit {
            appSocket.connect()
        }

        fun destoySocket(): Unit {
            appSocket.close()
        }

        fun setInitialSocketListeners(consultationId: String, callStartedCallback: ()->Unit, remainingTimeCallback: (Int)->Unit): Unit {
            appSocket.on(Socket.EVENT_CONNECT) {
                Log.i("SocketHandler","Socket is now connected")
                sendData(consultationId)
            }
            appSocket.on(CALL_STARTED_EVENT)  { data ->
                val dataJSON = JSONObject(data[0] as String)
                Resources.callID = dataJSON.get("call_id") as String
                callStartedCallback()
            }
            appSocket.on(REMAINING_TIME) {data ->
                val dataJSON = JSONObject(data[0] as String)
                val remainingTime: Int = dataJSON.get(REMAINING_TIME) as Int
                remainingTimeCallback(remainingTime)
            }
        }

        private fun sendData(consultationId: String): Unit {
            val data = JSONObject()
            data.put("consultation_id", consultationId)
            appSocket.emit(WAITING_CALL_EVENT, data.toString())
        }
    }
}
