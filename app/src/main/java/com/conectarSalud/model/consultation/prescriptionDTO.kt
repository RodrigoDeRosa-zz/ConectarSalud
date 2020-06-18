package com.conectarSalud.model.consultation

data class prescriptionDTO (
    val date: String,
    val patientFirstName: String,
    val patientLastName: String,
    val patientPlan: String,
    val patientId: String,
    val doctorFirstName: String,
    val doctorLastName: String,
    val doctorLicence: String,
    val doctorSpecialties: List<String>,
    val prescriptionText: String? = null
)