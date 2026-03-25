package com.nexfin.frontend.viewmodel

import com.nexfin.frontend.network.models.response.TransactionResponse
import com.nexfin.frontend.repository.TransactionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class TransactionUiState(
    val isLoading: Boolean = false,
    val transactions: List<TransactionResponse> = emptyList(),
    val lastTransaction: TransactionResponse? = null,
    val errorMessage: String? = null
)

class TransactionViewModel(
    private val transactionRepository: TransactionRepository
) : ViewModelScope() {

    private val _uiState = MutableStateFlow(TransactionUiState())
    val uiState: StateFlow<TransactionUiState> = _uiState.asStateFlow()

    fun loadTransactions(userId: String) {
        scope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            runCatching { transactionRepository.getTransactions(userId) }
                .onSuccess { transactions ->
                    _uiState.value = TransactionUiState(
                        isLoading = false,
                        transactions = transactions
                    )
                }
                .onFailure { error ->
                    _uiState.value = TransactionUiState(
                        isLoading = false,
                        errorMessage = error.message ?: "Unable to load transactions"
                    )
                }
        }
    }

    fun transfer(fromUserId: String, toUserId: String, amount: Double, reference: String) {
        scope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            runCatching { transactionRepository.transfer(fromUserId, toUserId, amount, reference) }
                .onSuccess { transaction ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        lastTransaction = transaction
                    )
                }
                .onFailure { error ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = error.message ?: "Unable to transfer funds"
                    )
                }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
}
