package com.nexfin.frontend.viewmodel

import com.nexfin.frontend.repository.AuthRepository
import com.nexfin.frontend.utils.AppLogger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class AuthUiState(
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    val userId: String? = null,
    val email: String? = null,
    val errorMessage: String? = null
)

class AuthViewModel(
    private val authRepository: AuthRepository
) : ViewModelScope() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    fun register(fullName: String, email: String, password: String) {
        AppLogger.info("AuthViewModel", "Register triggered for email=${email.trim()}")
        submitAuthAction {
            authRepository.register(fullName, email, password)
        }
    }

    fun login(email: String, password: String) {
        AppLogger.info("AuthViewModel", "Login triggered for email=${email.trim()}")
        submitAuthAction {
            authRepository.login(email, password)
        }
    }

    fun logout() {
        authRepository.logout()
        _uiState.value = AuthUiState()
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }

    private fun submitAuthAction(action: suspend () -> com.nexfin.frontend.network.models.response.LoginResponse) {
        scope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            runCatching { action() }
                .onSuccess { response ->
                    AppLogger.info("AuthViewModel", "Authentication succeeded for email=${response.email}, userId=${response.userId}")
                    _uiState.value = AuthUiState(
                        isLoading = false,
                        isLoggedIn = true,
                        userId = response.userId,
                        email = response.email
                    )
                }
                .onFailure { error ->
                    AppLogger.error(
                        "AuthViewModel",
                        "Authentication failed: ${error.message ?: "Unknown authentication error"}",
                        error
                    )
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        isLoggedIn = false,
                        errorMessage = error.message ?: "Authentication failed"
                    )
                }
        }
    }
}
