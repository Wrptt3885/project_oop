package com.nexfin.frontend.network.models.response

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(val token: String, val userId: String, val email: String)
