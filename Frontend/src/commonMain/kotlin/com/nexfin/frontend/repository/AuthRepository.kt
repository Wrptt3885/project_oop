package com.nexfin.frontend.repository

import com.nexfin.frontend.network.ApiService
import com.nexfin.frontend.network.models.request.LoginRequest
import com.nexfin.frontend.network.models.request.RegisterRequest
import com.nexfin.frontend.network.models.response.LoginResponse
import com.nexfin.frontend.security.TokenManager
import com.nexfin.frontend.utils.AppLogger

class AuthRepository(
    private val apiService: ApiService,
    private val tokenManager: TokenManager
) {
    suspend fun register(fullName: String, email: String, password: String): LoginResponse {
        AppLogger.info("AuthRepository", "Register requested for email=${email.trim()}")
        val response = apiService.register(RegisterRequest(fullName.trim(), email.trim(), password))
        tokenManager.saveSession(response.token, response.userId)
        AppLogger.info("AuthRepository", "Register succeeded for email=${response.email}, userId=${response.userId}")
        return response
    }

    suspend fun login(email: String, password: String): LoginResponse {
        AppLogger.info("AuthRepository", "Login requested for email=${email.trim()}")
        val response = apiService.login(LoginRequest(email.trim(), password))
        tokenManager.saveSession(response.token, response.userId)
        AppLogger.info("AuthRepository", "Login succeeded for email=${response.email}, userId=${response.userId}")
        return response
    }

    fun logout() {
        AppLogger.info("AuthRepository", "Logout requested. Clearing local session.")
        tokenManager.clearSession()
    }
}
