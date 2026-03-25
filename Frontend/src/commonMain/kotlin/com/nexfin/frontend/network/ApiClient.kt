package com.nexfin.frontend.network

import com.nexfin.frontend.network.interceptors.AuthInterceptor
import com.nexfin.frontend.network.models.response.ApiErrorResponse
import com.nexfin.frontend.utils.Constants
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.accept
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object ApiClient {
    val json: Json = Json {
        ignoreUnknownKeys = true
        prettyPrint = false
        isLenient = true
    }

    fun createHttpClient(authInterceptor: AuthInterceptor? = null): HttpClient {
        return HttpClient {
            install(ContentNegotiation) {
                json(json)
            }
            install(HttpTimeout) {
                requestTimeoutMillis = Constants.RequestTimeoutMillis
                connectTimeoutMillis = Constants.RequestTimeoutMillis
                socketTimeoutMillis = Constants.RequestTimeoutMillis
            }
            defaultRequestHeaders(authInterceptor)
        }
    }

    fun HttpRequestBuilder.applyJsonDefaults() {
        contentType(ContentType.Application.Json)
        accept(ContentType.Application.Json)
    }

    fun parseErrorMessage(responseBody: String): String {
        return runCatching {
            json.decodeFromString<ApiErrorResponse>(responseBody).message
        }.getOrDefault(responseBody.ifBlank { "Unknown API error" })
    }

    private fun HttpClientConfig<*>.defaultRequestHeaders(authInterceptor: AuthInterceptor?) {
        defaultRequest {
            authInterceptor?.token()?.takeIf { it.isNotBlank() }?.let { token ->
                header(HttpHeaders.Authorization, "Bearer $token")
            }
        }
    }
}
