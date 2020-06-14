package com.conectarSalud.connector.videochat.affiliate

import com.android.volley.VolleyError
import com.beust.klaxon.Klaxon
import com.conectarSalud.connector.backend.BackendConnector
import com.conectarSalud.model.affiliatevideochat.AffiliateVideoChatDTO
import com.conectarSalud.model.consultation.consultationDTO
import com.conectarSalud.services.Resources
import org.json.JSONObject

class AffiliateVideoChatConnector() {
    private val CONSULTATION_PATH = "/affiliates/%s/consultations"
    private var mapper: Klaxon = Klaxon()
    private lateinit var affiliateVideoChatCallback: (result: AffiliateVideoChatDTO?) -> Unit

    fun getConsultationId(callback: (response :AffiliateVideoChatDTO?) -> Unit, callbackError: (error: VolleyError?) -> Unit): Unit {
        this.affiliateVideoChatCallback = callback
        val url: String = String.format(CONSULTATION_PATH, Resources.dni)
        BackendConnector.post(url, null, ::correctGetResponseHandler, callbackError)
    }

    private fun correctGetResponseHandler(response :JSONObject?) {
        val videoChatData: AffiliateVideoChatDTO? = mapper.parse<AffiliateVideoChatDTO>(response.toString())
        videoChatData?.let { affiliateVideoChatCallback(it) }
    }

}

