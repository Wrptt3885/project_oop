package com.nexfin.frontend.network.models.request

import kotlinx.serialization.Serializable

@Serializable
data class TopUpRequest(
    val amount: Double,
    val reference: String
)
