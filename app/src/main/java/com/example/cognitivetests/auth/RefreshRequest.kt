package com.example.cognitivetests.auth

import kotlinx.serialization.Serializable

/**
 * Data class for refresh request.
 */
@Serializable
data class RefreshRequest(
    val refresh_token: String
)
