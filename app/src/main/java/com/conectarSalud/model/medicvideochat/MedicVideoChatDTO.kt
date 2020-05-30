package com.conectarSalud.model.medicvideochat

import com.beust.klaxon.Json

data class MedicVideoChatDTO (
    @Json(name="call_id")
    val callId: String? = null
)