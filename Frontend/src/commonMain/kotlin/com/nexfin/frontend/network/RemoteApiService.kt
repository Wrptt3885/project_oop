package com.nexfin.frontend.network

import com.nexfin.frontend.network.models.request.LoginRequest
import com.nexfin.frontend.network.models.request.RegisterRequest
import com.nexfin.frontend.network.models.request.TopUpRequest
import com.nexfin.frontend.network.models.request.TransferRequest
import com.nexfin.frontend.network.models.response.LoginResponse
import com.nexfin.frontend.network.models.response.TransactionResponse
import com.nexfin.frontend.network.models.response.WalletResponse
import com.nexfin.frontend.utils.AppLogger
import com.nexfin.frontend.utils.Constants
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess

class RemoteApiService(
    private val client: HttpClient
) : ApiService {

    override suspend fun register(request: RegisterRequest): LoginResponse {
        val requestLabel = "POST ${Constants.BaseUrl}/auth/register"
        AppLogger.info("RemoteApiService", "$requestLabel email=${request.email}")
        return client.post("${Constants.BaseUrl}/auth/register") {
            ApiClient.run { applyJsonDefaults() }
            setBody(request)
        }.parseBody(requestLabel)
    }

    override suspend fun login(request: LoginRequest): LoginResponse {
        val requestLabel = "POST ${Constants.BaseUrl}/auth/login"
        AppLogger.info("RemoteApiService", "$requestLabel email=${request.email}")
        return client.post("${Constants.BaseUrl}/auth/login") {
            ApiClient.run { applyJsonDefaults() }
            setBody(request)
        }.parseBody(requestLabel)
    }

    override suspend fun getWallet(userId: String): WalletResponse {
        val requestLabel = "GET ${Constants.BaseUrl}/wallets/$userId"
        AppLogger.info("RemoteApiService", requestLabel)
        return client.get("${Constants.BaseUrl}/wallets/$userId") {
            ApiClient.run { applyJsonDefaults() }
        }.parseBody(requestLabel)
    }

    override suspend fun topUp(userId: String, request: TopUpRequest): WalletResponse {
        val requestLabel = "POST ${Constants.BaseUrl}/wallets/$userId/top-up"
        AppLogger.info(
            "RemoteApiService",
            "$requestLabel amount=${request.amount} reference=${request.reference}"
        )
        return client.post("${Constants.BaseUrl}/wallets/$userId/top-up") {
            ApiClient.run { applyJsonDefaults() }
            setBody(request)
        }.parseBody(requestLabel)
    }

    override suspend fun transfer(request: TransferRequest): TransactionResponse {
        val requestLabel = "POST ${Constants.BaseUrl}/transactions/transfer"
        AppLogger.info(
            "RemoteApiService",
            "$requestLabel from=${request.fromUserId} to=${request.toUserId} amount=${request.amount} reference=${request.reference}"
        )
        return client.post("${Constants.BaseUrl}/transactions/transfer") {
            ApiClient.run { applyJsonDefaults() }
            setBody(request)
        }.parseBody(requestLabel)
    }

    override suspend fun getTransactions(userId: String): List<TransactionResponse> {
        val requestLabel = "GET ${Constants.BaseUrl}/transactions/user/$userId"
        AppLogger.info("RemoteApiService", requestLabel)
        return client.get("${Constants.BaseUrl}/transactions/user/$userId") {
            ApiClient.run { applyJsonDefaults() }
        }.parseBody(requestLabel)
    }

    private suspend inline fun <reified T> HttpResponse.parseBody(requestLabel: String): T {
        AppLogger.info("RemoteApiService", "Response status=$status for $requestLabel")
        if (status.isSuccess()) {
            return body()
        }
        val responseText = bodyAsText()
        val parsedMessage = ApiClient.parseErrorMessage(responseText)
        AppLogger.error(
            "RemoteApiService",
            "Request failed status=$status for $requestLabel. Response=$responseText"
        )
        throw ApiException(parsedMessage)
    }
}
