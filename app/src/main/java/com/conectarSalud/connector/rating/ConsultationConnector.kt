package com.conectarSalud.connector.rating

import com.android.volley.VolleyError
import com.beust.klaxon.FieldRenamer
import com.beust.klaxon.Klaxon
import com.conectarSalud.connector.backend.BackendConnector
import com.conectarSalud.model.consultation.consultationDTO
import com.conectarSalud.model.consultation.prescriptionDTO
import com.conectarSalud.model.consultation.consultationsDTO
import com.conectarSalud.services.Resources
import org.json.JSONArray
import org.json.JSONObject

class ConsultationConnector() {

    private val badCredentials = 404
    private var mapper:Klaxon
    private lateinit var consultationCallback: (result: consultationDTO?) -> Unit
    private lateinit var prescriptionCallback: (result: prescriptionDTO?) -> Unit
    private lateinit var ratingCallback: (result: Boolean) -> Unit
    private lateinit var consultationsCallback: (result: ArrayList<consultationsDTO>?) -> Unit


    init {
        val renamer = object: FieldRenamer {
            override fun toJson(fieldName: String) = FieldRenamer.camelToUnderscores(fieldName)
            override fun fromJson(fieldName: String) = FieldRenamer.underscoreToCamel(fieldName)
        }
        mapper = Klaxon().fieldRenamer(renamer)
    }

    fun patchScore(consultationID: String, score:Float, comment: String,
                   callback: (result: Boolean?) -> Unit) {

        val parameters = JSONObject()
        parameters.put("score", score)
        if(comment.isNotEmpty()){
            parameters.put("score_opinion", comment)
        }

        this.ratingCallback = callback
        BackendConnector.patch("/affiliates/${Resources.dni}/consultations/$consultationID",
            parameters, ::correctPatchResponseHandler, ::errorPatchResponseHandler)
    }

    private fun correctPatchResponseHandler(response :JSONObject?) {
        ratingCallback(true)
    }

    private fun errorPatchResponseHandler(error: VolleyError?) {
        val errorMessage = error?.networkResponse?.statusCode
        ratingCallback(false)
    }

    fun getConsultationData(consultationID: String, callback: (result: consultationDTO?) -> Unit) {

        this.consultationCallback = callback
        BackendConnector.get("/affiliates/${Resources.dni}/consultations/$consultationID",
            ::correctConsultationGetResponseHandler, ::errorGetResponseHandler)
    }

    fun getPrescriptionData(consultationID: String, callback: (result: prescriptionDTO?) -> Unit) {
        this.prescriptionCallback = callback
        BackendConnector.get("/affiliates/${Resources.dni}/prescriptions/$consultationID",
            ::correctPrescriptionGetResponseHandler, ::errorGetResponseHandler)
    }

    private fun correctConsultationGetResponseHandler(response :JSONObject?) {
        val consultationData: consultationDTO? = mapper.parse<consultationDTO>(response.toString())
        consultationData?.let { consultationCallback(it) }
    }

    private fun correctPrescriptionGetResponseHandler(response :JSONObject?) {
        val prescriptionData: prescriptionDTO? = mapper.parse<prescriptionDTO>(response.toString())
        prescriptionData?.let { prescriptionCallback(it) }
    }

    private fun errorGetResponseHandler(error: VolleyError?) {
        consultationsCallback(null)
    }

    fun getConsultations( callback: (result: ArrayList<consultationsDTO>?) -> Unit) {

        this.consultationsCallback = callback
        BackendConnector.getArray("/affiliates/${Resources.dni}/consultations",
            ::correctGetArrayResponseHandler, ::errorGetResponseHandler)
    }

    private fun correctGetArrayResponseHandler(response :JSONArray?) {
        val histories: ArrayList<consultationsDTO> = ArrayList()
        for (i in 0 until response!!.length()) {
            val history = consultationsDTO()
            val jsonHistory: JSONObject = response.getJSONObject(i)
            var specialties = jsonHistory.getJSONArray("doctor_specialties")

            history.doctor_firstname = jsonHistory.getString("doctor_first_name")
            history.doctor_lastname = jsonHistory.getString("doctor_last_name")

            for (j in 0 until specialties!!.length()) {
                history.doctor_specialties?.add(specialties[j].toString())
            }

            history.date = jsonHistory.getString("date")
            histories.add(history)
        }
        histories?.let { consultationsCallback(it) }
    }

}