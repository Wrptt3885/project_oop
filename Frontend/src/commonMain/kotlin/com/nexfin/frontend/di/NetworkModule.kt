package com.nexfin.frontend.di

import com.nexfin.frontend.network.ApiClient
import com.nexfin.frontend.network.ApiService
import com.nexfin.frontend.network.RemoteApiService
import com.nexfin.frontend.network.interceptors.AuthInterceptor
import com.nexfin.frontend.security.TokenManager

class NetworkModule(
    tokenManager: TokenManager
) {
    private val authInterceptor = AuthInterceptor(tokenManager)
    val apiService: ApiService = RemoteApiService(ApiClient.createHttpClient(authInterceptor))
}
