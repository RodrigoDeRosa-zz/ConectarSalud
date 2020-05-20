package com.conectarSalud.videoChat

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.conectarSalud.R
import com.conectarSalud.connector.videochat.VideoChatConnector
import com.conectarSalud.helper.SocketHandler
import com.conectarSalud.services.Resources

class WaitingRoomActivity : AppCompatActivity() {
    private val videoChatConnector: VideoChatConnector = VideoChatConnector()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_waiting_room)

        doVideochatInitialRequest()
    }

    private fun doVideochatInitialRequest(): Unit {
        videoChatConnector.getConsultationId(::configureSocket)
    }

    private fun configureSocket() {
        // Set socket required listeners
        SocketHandler.setInitialSocketListeners(Resources.consultationID, ::initVideoChat)

        // Connect socket to server and send waiting_call event
        SocketHandler.connectSocket()
    }

    private fun initVideoChat(callId: String) {
        finish()
        val intent = Intent(this, VideoChatViewActivity::class.java)
        startActivity(intent)
    }
}
