package com.nexfin.frontend.network.models.request

data class TransferRequest(val fromUserId: String, val toUserId: String, val amount: String)
