package com.example.cognitivetests.auth

import kotlinx.serialization.Serializable

/**
 * Data class for refresh request.
 */
@Serializable
data class RefreshRequest(
    val token: String,
    val refreshToken: String
)
