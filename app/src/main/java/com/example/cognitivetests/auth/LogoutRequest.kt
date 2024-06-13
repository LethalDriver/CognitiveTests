package com.example.cognitivetests.auth
import kotlinx.serialization.Serializable

/**
 * Data class for logout request.
 */
@Serializable
data class LogoutRequest(
    val access_token: String,
    val refresh_token: String
)
