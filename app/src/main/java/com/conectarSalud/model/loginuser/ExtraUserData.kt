package com.conectarSalud.model.loginuser

import com.beust.klaxon.Json

data class ExtraUserData(
        val role: String,
        @Json(name="first_name")
        val firstName: String,
        @Json(name="last_name")
        val lastName: String,
        val id: String?,
        val licence: String?,
        val specialties: List<String>?
)
