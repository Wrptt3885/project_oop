package com.nexfin.frontend.network

import com.nexfin.frontend.network.models.request.LoginRequest
import com.nexfin.frontend.network.models.request.RegisterRequest
import com.nexfin.frontend.network.models.request.TopUpRequest
import com.nexfin.frontend.network.models.request.TransferRequest
import com.nexfin.frontend.network.models.response.LoginResponse
import com.nexfin.frontend.network.models.response.TransactionResponse
import com.nexfin.frontend.network.models.response.WalletResponse

interface ApiService {
    suspend fun register(request: RegisterRequest): LoginResponse
    suspend fun login(request: LoginRequest): LoginResponse
    suspend fun getWallet(userId: String): WalletResponse
    suspend fun topUp(userId: String, request: TopUpRequest): WalletResponse
    suspend fun transfer(request: TransferRequest): TransactionResponse
    suspend fun getTransactions(userId: String): List<TransactionResponse>
}
