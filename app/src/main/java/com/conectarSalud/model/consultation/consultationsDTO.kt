package com.conectarSalud.model.consultation

data class consultationsDTO (
    var doctor_firstname: String? = "",
    var doctor_lastname: String? = "",
    var patient_firstname: String? = "",
    var patient_lastname: String? = "",
    var patient_dni: String? = "",
    var doctor_specialties: ArrayList<String>? = ArrayList(),
    var date: String? = "",
    var consultation_id: String? = ""
)