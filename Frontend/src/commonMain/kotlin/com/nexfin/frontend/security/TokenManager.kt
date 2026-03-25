package com.nexfin.frontend.security

class TokenManager {
    private var token: String? = null
    private var userId: String? = null

    fun saveSession(authToken: String, authUserId: String) {
        token = authToken
        userId = authUserId
    }

    fun clearSession() {
        token = null
        userId = null
    }

    fun getToken(): String? = token

    fun getUserId(): String? = userId

    fun isLoggedIn(): Boolean = !token.isNullOrBlank() && !userId.isNullOrBlank()
}
