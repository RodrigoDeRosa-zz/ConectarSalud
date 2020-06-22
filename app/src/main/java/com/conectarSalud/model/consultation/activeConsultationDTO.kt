package com.conectarSalud.model.consultation

data class activeConsultationDTO (
    val symptoms: List<String>,
    val reason: String? = null
) {}