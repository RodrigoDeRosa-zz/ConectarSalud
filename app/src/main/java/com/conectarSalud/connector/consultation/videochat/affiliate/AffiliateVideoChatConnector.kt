package com.conectarSalud.connector.consultation.videochat.affiliate

import com.android.volley.VolleyError
import com.beust.klaxon.Klaxon
import com.conectarSalud.connector.backend.BackendConnector
import com.conectarSalud.model.affiliatevideochat.AffiliateVideoChatDTO
import com.conectarSalud.model.consultation.activeConsultationDTO
import com.conectarSalud.services.Resources
import org.json.JSONObject

class AffiliateVideoChatConnector() {
    private val CONSULTATION_PATH = "/affiliates/%s/consultations"
    private var mapper: Klaxon = Klaxon()
    private lateinit var affiliateVideoChatCallback: (result: AffiliateVideoChatDTO?) -> Unit

    fun getConsultationId(params: activeConsultationDTO, callback: (response :AffiliateVideoChatDTO?) -> Unit, callbackError: (error: VolleyError?) -> Unit): Unit {
        this.affiliateVideoChatCallback = callback
        val url: String = String.format(CONSULTATION_PATH, Resources.dni)
        val parameters = JSONObject()
        parameters.put("symptoms", mapper.toJsonString(params.symptoms))
        parameters.put("reason", params.reason)
        BackendConnector.post(url, parameters, ::correctGetResponseHandler, callbackError)
    }

    private fun correctGetResponseHandler(response :JSONObject?) {
        val videoChatData: AffiliateVideoChatDTO? = mapper.parse<AffiliateVideoChatDTO>(response.toString())
        videoChatData?.let { affiliateVideoChatCallback(it) }
    }

    fun cancelConsultation(consultationID: String) {
        BackendConnector.delete(
            "/affiliates/${Resources.dni}/consultations/$consultationID",
            ::deleteResponseHandler,
            ::deleteErrorHandler
        )
    }

    private fun deleteResponseHandler(response: JSONObject?) {

    }

    private fun deleteErrorHandler(response: VolleyError?) {

    }

}

