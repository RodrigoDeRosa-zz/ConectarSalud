package com.conectarSalud.model.consultation

import java.util.*

data class consultationDTO (
    val date: String,
    val patientFirstName: String,
    val patientLastName: String,
    val doctorFirstName: String,
    val doctorLastName: String,
    val doctorSpecialties: List<String>,
    val symptoms: List<String>,
    val hasPrescription: Boolean,
    val indications: String? = null
) {}