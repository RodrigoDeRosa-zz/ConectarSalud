package com.conectarSalud.videoChat

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.VolleyError
import com.conectarSalud.R
import com.conectarSalud.connector.videochat.affiliate.AffiliateVideoChatConnector
import com.conectarSalud.helper.SocketHandler
import com.conectarSalud.services.Resources
import org.json.JSONObject

class WaitingRoomActivity : AppCompatActivity() {
    private val affiliateVideoChatConnector: AffiliateVideoChatConnector =
        AffiliateVideoChatConnector()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_waiting_room)

        doVideochatInitialRequest()
    }

    private fun doVideochatInitialRequest(): Unit {
        affiliateVideoChatConnector.getConsultationId(::configureSocket, ::showError)
    }

    private fun configureSocket(response: JSONObject?): Unit {
        // Set consultationID
        Resources.consultationID = response?.get("consultation_id") as String

        // Set socket required listeners
        SocketHandler.setInitialSocketListeners(Resources.consultationID, ::initVideoChat)

        // Connect socket to server and send waiting_call event
        SocketHandler.connectSocket()
    }

    private fun initVideoChat() {
        finish()
        val intent = Intent(this, VideoChatViewActivity::class.java)
        startActivity(intent)
    }

    private fun showError(error: VolleyError?): Unit {
        Toast.makeText(applicationContext, R.string.videochat_error, Toast.LENGTH_SHORT).show()
    }
}
