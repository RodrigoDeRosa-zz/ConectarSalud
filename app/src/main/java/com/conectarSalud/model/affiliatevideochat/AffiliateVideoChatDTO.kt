package com.conectarSalud.model.affiliatevideochat

import com.beust.klaxon.Json

data class AffiliateVideoChatDTO (
    @Json(name="consultation_id")
    val consultationId: String = "",
    @Json(name="call_id")
    val callId: String? = null
)