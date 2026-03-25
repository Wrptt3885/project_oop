package com.nexfin.frontend.network.models.request

import kotlinx.serialization.Serializable

@Serializable
data class TransferRequest(
    val fromUserId: String,
    val toUserId: String,
    val amount: Double,
    val reference: String
)
