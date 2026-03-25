package com.nexfin.frontend.viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class SessionState(
    val currentUserId: String? = null,
    val currentEmail: String? = null
)

class SharedViewModel {
    private val _sessionState = MutableStateFlow(SessionState())
    val sessionState: StateFlow<SessionState> = _sessionState.asStateFlow()

    fun setSession(userId: String, email: String) {
        _sessionState.value = SessionState(userId, email)
    }

    fun clearSession() {
        _sessionState.value = SessionState()
    }
}
