package com.nexfin.frontend.network.models.response

import kotlinx.serialization.Serializable

@Serializable
data class WalletResponse(
    val walletId: String,
    val userId: String,
    val balance: Double,
    val currency: String
)
