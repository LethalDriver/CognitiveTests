package com.example.cognitivetests.auth

import kotlinx.serialization.Serializable

@Serializable
data class RefreshResponse(
    val access_token: String,
    val token_type: String
)
