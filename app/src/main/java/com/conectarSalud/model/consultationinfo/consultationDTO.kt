package com.conectarSalud.model.consultationinfo

data class consultationDTO (
    val doctorFirstName: String = "",
    val doctorLastName: String = "",
    val prescription: String? = null,
    val indications: String? = null
)