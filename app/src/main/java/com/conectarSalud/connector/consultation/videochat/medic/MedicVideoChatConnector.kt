package com.conectarSalud.connector.consultation.videochat.medic

import com.android.volley.VolleyError
import com.beust.klaxon.Klaxon
import com.conectarSalud.connector.backend.BackendConnector
import com.conectarSalud.model.medicvideochat.MedicVideoChatDTO
import com.conectarSalud.services.Resources
import org.json.JSONObject

class MedicVideoChatConnector() {
    private val CALL_PATH = "/doctors/%s/consultations?status=active"
    private var mapper: Klaxon = Klaxon()
    private lateinit var medicVideoChatCallback: (result: MedicVideoChatDTO?) -> Unit


    fun getCallId(callback: (response: MedicVideoChatDTO?) -> Unit, callbackError: (error: VolleyError?) -> Unit) {
        this.medicVideoChatCallback = callback
        // do login
        val url: String = String.format(CALL_PATH, Resources.extraUserData?.id)
        BackendConnector.get(url, ::correctGetResponseHandler, callbackError)
    }

    private fun correctGetResponseHandler(response :JSONObject?) {
        val videoChatData: MedicVideoChatDTO? = mapper.parse<MedicVideoChatDTO>(response.toString())
        videoChatData?.let { medicVideoChatCallback(it) }
    }

}

