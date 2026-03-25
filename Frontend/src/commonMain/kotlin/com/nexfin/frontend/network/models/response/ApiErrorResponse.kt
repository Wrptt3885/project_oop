package com.nexfin.frontend.network.models.response

import kotlinx.serialization.Serializable

@Serializable
data class ApiErrorResponse(
    val message: String,
    val path: String? = null,
    val timestamp: String? = null
)
