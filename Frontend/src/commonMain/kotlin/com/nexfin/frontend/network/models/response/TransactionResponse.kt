package com.nexfin.frontend.network.models.response

data class TransactionResponse(
    val transactionId: String,
    val sourceWalletId: String,
    val targetWalletId: String,
    val amount: String,
    val type: String,
    val status: String
)
