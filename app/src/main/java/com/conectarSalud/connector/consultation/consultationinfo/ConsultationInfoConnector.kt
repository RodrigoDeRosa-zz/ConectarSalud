package com.conectarSalud.connector.consultation.consultationinfo

import com.android.volley.VolleyError
import com.beust.klaxon.Klaxon
import com.conectarSalud.connector.backend.BackendConnector
import org.json.JSONObject

class ConsultationInfoConnector() {
    private val GET_SYMPTOMS_PATH = "/symptoms"
    private val mapper = Klaxon()
    private lateinit var symptomsCallback: (response :HashMap<String, List<String>>) -> Unit


    fun getSymptoms(callback: (data: HashMap<String, List<String>>) -> Unit) {
        symptomsCallback = callback
        BackendConnector.get(GET_SYMPTOMS_PATH, ::correctResponseHandler, ::errorResponseHandler)
    }

    private fun correctResponseHandler(response :JSONObject?) {
        if (response != null) {
            val finalResponse = HashMap<String, List<String>>()
            response.keys().forEach {key ->
                val strKey = key as String
                val values: List<String> = mapper.parseArray<String>(response.get(strKey).toString()) as List<String>
                finalResponse[strKey] = values
            }
            symptomsCallback(finalResponse)
        }
    }

    private fun errorResponseHandler(error: VolleyError?) {
    }
}

