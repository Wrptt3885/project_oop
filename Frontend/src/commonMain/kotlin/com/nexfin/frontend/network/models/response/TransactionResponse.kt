package com.nexfin.frontend.network.models.response

import kotlinx.serialization.Serializable

@Serializable
data class TransactionResponse(
    val transactionId: String,
    val sourceWalletId: String,
    val targetWalletId: String,
    val amount: Double,
    val type: String,
    val status: String,
    val reference: String,
    val createdAt: String
)
