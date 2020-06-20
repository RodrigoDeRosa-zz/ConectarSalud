package com.conectarSalud.home.medic

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.VolleyError
import com.conectarSalud.R
import com.conectarSalud.connector.consultation.videochat.medic.MedicVideoChatConnector
import com.conectarSalud.model.medicvideochat.MedicVideoChatDTO
import com.conectarSalud.services.Resources
import com.conectarSalud.consultation.VideoChatViewActivity
import kotlinx.android.synthetic.main.activity_home_medic.*

class HomeMedicActivity : AppCompatActivity() {
    private val medicVideoChatConnector: MedicVideoChatConnector = MedicVideoChatConnector()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_medic)

        btnStartCall.setOnClickListener {
            medicVideoChatConnector.getCallId(::redirectToVideoChat, ::showError)
        }
    }

    private fun redirectToVideoChat(response : MedicVideoChatDTO?): Unit {
        // Set call id
        Resources.callID = response?.callId.toString()

        // Redirect to call
        finish()
        val intent = Intent(this, VideoChatViewActivity::class.java)
        startActivity(intent)
    }

    private fun showError(error: VolleyError?): Unit {
        Toast.makeText(applicationContext, R.string.videochat_error, Toast.LENGTH_SHORT).show()
    }

}