package com.conectarSalud.connector.videochat

import com.android.volley.VolleyError
import com.beust.klaxon.Klaxon
import com.conectarSalud.R
import com.conectarSalud.connector.backend.BackendConnector
import com.conectarSalud.connector.login.LoginConnector
import com.conectarSalud.model.loginuser.LoggedInUser
import com.conectarSalud.model.loginuser.LoginResult
import com.conectarSalud.services.Resources
import org.json.JSONObject

class VideoChatConnector() {

    private val CONSULTATION_PATH = "/affiliates/%s/consultations"
    private lateinit var callback: () -> Unit

    fun getConsultationId(callback: () -> Unit) {
        // save callback
        this.callback = callback

        // do login
        val url: String = String.format(CONSULTATION_PATH, Resources.userID)
        BackendConnector.post(url, null, ::correctResponseHandler, ::errorResponseHandler)
    }

    private fun correctResponseHandler(response :JSONObject?) {
        Resources.consultationID = response?.get("consultation_id") as String
        callback()
    }

    private fun errorResponseHandler(error: VolleyError?) {
        callback()
    }
}

