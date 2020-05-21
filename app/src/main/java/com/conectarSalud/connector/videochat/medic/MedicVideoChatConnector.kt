package com.conectarSalud.connector.videochat.medic

import com.android.volley.VolleyError
import com.conectarSalud.connector.backend.BackendConnector
import com.conectarSalud.services.Resources
import org.json.JSONObject

class MedicVideoChatConnector() {

    private val CALL_PATH = "/doctors/%s/consultations?status=active"

    fun getCallId(callback: (response :JSONObject?) -> Unit, callbackError: (error: VolleyError?) -> Unit) {

        // do login
        val url: String = String.format(CALL_PATH, Resources.extraUserData?.id)
        BackendConnector.get(url, callback, callbackError)
    }

}

