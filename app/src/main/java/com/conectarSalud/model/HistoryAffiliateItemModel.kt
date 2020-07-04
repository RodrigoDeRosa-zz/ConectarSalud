package com.conectarSalud.model

data class HistoryAffiliateItemModel(
    val medicName: String,
    val medicLastName: String,
    val specialities: List<String>,
    val date: String,
    val consultationId: String,
    val affiliateName: String,
    val affiliateLastName: String
)