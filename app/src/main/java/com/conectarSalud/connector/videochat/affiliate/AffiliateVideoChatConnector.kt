package com.conectarSalud.connector.videochat.affiliate

import com.android.volley.VolleyError
import com.conectarSalud.connector.backend.BackendConnector
import com.conectarSalud.services.Resources
import org.json.JSONObject

class AffiliateVideoChatConnector() {

    private val CONSULTATION_PATH = "/affiliates/%s/consultations"

    fun getConsultationId(callback: (response :JSONObject?) -> Unit, callbackError: (error: VolleyError?) -> Unit): Unit {
        // do login
        val url: String = String.format(CONSULTATION_PATH, Resources.dni)
        BackendConnector.post(url, null, callback, callbackError)
    }
}

