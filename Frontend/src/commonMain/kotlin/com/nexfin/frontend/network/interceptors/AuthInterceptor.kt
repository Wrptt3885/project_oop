package com.nexfin.frontend.network.interceptors

import com.nexfin.frontend.security.TokenManager

class AuthInterceptor(private val tokenManager: TokenManager) {
    fun token(): String? = tokenManager.getToken()
}
