package com.nexfin.frontend.repository

import com.nexfin.frontend.network.ApiService
import com.nexfin.frontend.network.models.request.TransferRequest
import com.nexfin.frontend.network.models.response.TransactionResponse

class TransactionRepository(
    private val apiService: ApiService
) {
    suspend fun transfer(fromUserId: String, toUserId: String, amount: Double, reference: String): TransactionResponse {
        return apiService.transfer(TransferRequest(fromUserId, toUserId, amount, reference.trim()))
    }

    suspend fun getTransactions(userId: String): List<TransactionResponse> {
        return apiService.getTransactions(userId)
    }
}
