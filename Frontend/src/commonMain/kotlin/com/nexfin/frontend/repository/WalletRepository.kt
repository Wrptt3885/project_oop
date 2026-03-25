package com.nexfin.frontend.repository

import com.nexfin.frontend.network.ApiService
import com.nexfin.frontend.network.models.request.TopUpRequest
import com.nexfin.frontend.network.models.response.WalletResponse

class WalletRepository(
    private val apiService: ApiService
) {
    suspend fun getWallet(userId: String): WalletResponse = apiService.getWallet(userId)

    suspend fun topUp(userId: String, amount: Double, reference: String): WalletResponse {
        return apiService.topUp(userId, TopUpRequest(amount, reference.trim()))
    }
}
