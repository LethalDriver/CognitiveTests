package com.example.cognitivetests.auth

import kotlinx.serialization.Serializable

/**
 * Data class for refresh request.
 */
@Serializable
data class RefreshRequest(
    val access_token: String,
    val refresh_token: String
)
