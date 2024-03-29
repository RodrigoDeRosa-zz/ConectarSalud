package com.conectarSalud.consultation.watingconsultation

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.VolleyError
import com.conectarSalud.R
import com.conectarSalud.connector.consultation.videochat.affiliate.AffiliateVideoChatConnector
import com.conectarSalud.consultation.VideoChatViewActivity
import com.conectarSalud.helper.SocketHandler
import com.conectarSalud.home.affiliate.HomeAffiliateActivity
import com.conectarSalud.model.affiliatevideochat.AffiliateVideoChatDTO
import com.conectarSalud.services.Resources
import kotlinx.android.synthetic.main.activity_waiting_room.*
import java.net.Socket

class WaitingRoomActivity : AppCompatActivity() {
    private val affiliateVideoChatConnector: AffiliateVideoChatConnector =
        AffiliateVideoChatConnector()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_waiting_room)

        cancelVideoButton.setOnClickListener {
            affiliateVideoChatConnector.cancelConsultation(Resources.consultationID)
            SocketHandler.destoySocket()
            finish()

            val intent = Intent(this, HomeAffiliateActivity::class.java)
            startActivity(intent)
        }

        doVideochatInitialRequest()
    }

    private fun doVideochatInitialRequest(): Unit {
        affiliateVideoChatConnector.getConsultationId(Resources.symptoms, ::configureSocket, ::showError)
    }

    private fun configureSocket(response: AffiliateVideoChatDTO?): Unit {
        // Set consultationID
        Resources.consultationID = response?.consultationId.toString()

        if (response?.callId != null) {
            // If call_id exists, there is a previous call initiated.
            Resources.callID = response.callId.toString()
            initVideoChat()
        } else {
            // Set socket required listeners
            SocketHandler.setInitialSocketListeners(Resources.consultationID, ::initVideoChat, ::updateRemainingTime)
            // Connect socket to server and send waiting_call event
            SocketHandler.connectSocket()
        }
    }

    private fun initVideoChat(): Unit {
        finish()
        val intent = Intent(this, VideoChatViewActivity::class.java)
        startActivity(intent)
    }

    private fun updateRemainingTime(remainingTime: Int): Unit {
        val message: String = if (remainingTime > 0) String.format(getString(R.string.remaining_time), remainingTime.toString())
                                else getString(R.string.waiting_for_doctor)
        runOnUiThread({ remainingTimeMessage.text = message })
    }

    private fun showError(error: VolleyError?): Unit {
        Toast.makeText(applicationContext, R.string.videochat_error, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        SocketHandler.destoySocket()
    }
}
