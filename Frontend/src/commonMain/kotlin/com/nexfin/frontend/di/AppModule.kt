package com.nexfin.frontend.di

import com.nexfin.frontend.repository.AuthRepository
import com.nexfin.frontend.repository.TransactionRepository
import com.nexfin.frontend.repository.WalletRepository
import com.nexfin.frontend.security.TokenManager
import com.nexfin.frontend.viewmodel.AuthViewModel
import com.nexfin.frontend.viewmodel.SharedViewModel
import com.nexfin.frontend.viewmodel.TransactionViewModel
import com.nexfin.frontend.viewmodel.WalletViewModel

class AppModule {
    private val tokenManager = TokenManager()
    private val networkModule = NetworkModule(tokenManager)

    private val authRepository = AuthRepository(networkModule.apiService, tokenManager)
    private val walletRepository = WalletRepository(networkModule.apiService)
    private val transactionRepository = TransactionRepository(networkModule.apiService)

    val sharedViewModel = SharedViewModel()
    val authViewModel = AuthViewModel(authRepository)
    val walletViewModel = WalletViewModel(walletRepository)
    val transactionViewModel = TransactionViewModel(transactionRepository)
}
