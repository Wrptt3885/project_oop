package com.nexfin.frontend.viewmodel

import com.nexfin.frontend.network.models.response.WalletResponse
import com.nexfin.frontend.repository.WalletRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class WalletUiState(
    val isLoading: Boolean = false,
    val wallet: WalletResponse? = null,
    val errorMessage: String? = null
)

class WalletViewModel(
    private val walletRepository: WalletRepository
) : ViewModelScope() {

    private val _uiState = MutableStateFlow(WalletUiState())
    val uiState: StateFlow<WalletUiState> = _uiState.asStateFlow()

    fun loadWallet(userId: String) {
        scope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            runCatching { walletRepository.getWallet(userId) }
                .onSuccess { wallet ->
                    _uiState.value = WalletUiState(isLoading = false, wallet = wallet)
                }
                .onFailure { error ->
                    _uiState.value = WalletUiState(
                        isLoading = false,
                        errorMessage = error.message ?: "Unable to load wallet"
                    )
                }
        }
    }

    fun topUp(userId: String, amount: Double, reference: String) {
        scope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            runCatching { walletRepository.topUp(userId, amount, reference) }
                .onSuccess { wallet ->
                    _uiState.value = WalletUiState(isLoading = false, wallet = wallet)
                }
                .onFailure { error ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = error.message ?: "Unable to top up wallet"
                    )
                }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
}
