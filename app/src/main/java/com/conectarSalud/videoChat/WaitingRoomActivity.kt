package com.conectarSalud.videoChat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.conectarSalud.R
import com.conectarSalud.helper.SocketHandler

class WaitingRoomActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_waiting_room)

        // TODO check if an error occurs
        SocketHandler.connectSocket()
    }
}
