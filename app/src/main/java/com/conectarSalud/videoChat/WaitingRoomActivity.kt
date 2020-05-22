package com.conectarSalud.videoChat

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.VolleyError
import com.conectarSalud.R
import com.conectarSalud.connector.videochat.affiliate.AffiliateVideoChatConnector
import com.conectarSalud.helper.SocketHandler
import com.conectarSalud.home.affiliate.HomeAffiliateActivity
import com.conectarSalud.services.Resources
import kotlinx.android.synthetic.main.activity_waiting_room.*
import org.json.JSONObject

class WaitingRoomActivity : AppCompatActivity() {
    private val affiliateVideoChatConnector: AffiliateVideoChatConnector =
        AffiliateVideoChatConnector()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_waiting_room)

        cancelVideoButton.setOnClickListener {
            // TODO do back request
            finish()
            val intent = Intent(this, HomeAffiliateActivity::class.java)
            startActivity(intent)
        }

        doVideochatInitialRequest()
    }

    private fun doVideochatInitialRequest(): Unit {
        affiliateVideoChatConnector.getConsultationId(::configureSocket, ::showError)
    }

    private fun configureSocket(response: JSONObject?): Unit {
        // Set consultationID
        Resources.consultationID = response?.get("consultation_id") as String

        // Set socket required listeners
        SocketHandler.setInitialSocketListeners(Resources.consultationID, ::initVideoChat, ::updateRemainingTime)

        // Connect socket to server and send waiting_call event
        SocketHandler.connectSocket()
    }

    private fun initVideoChat(): Unit {
        finish()
        val intent = Intent(this, VideoChatViewActivity::class.java)
        startActivity(intent)
    }

    private fun updateRemainingTime(remainingTime: Int): Unit {
        val message: String = if (remainingTime > 0) getString(R.string.waiting_for_doctor)
                                else String.format(getString(R.string.remaining_time), remainingTime.toString())
        remainingTimeMessage.text = message
    }

    private fun showError(error: VolleyError?): Unit {
        Toast.makeText(applicationContext, R.string.videochat_error, Toast.LENGTH_SHORT).show()
    }

}
