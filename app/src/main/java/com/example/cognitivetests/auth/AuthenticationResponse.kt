package com.example.cognitivetests.auth

import kotlinx.serialization.Serializable

/**
 * Data class for authentication response.
 */
@Serializable
data class AuthenticationResponse(
        var token: String,
        var refreshToken: String,
        var expirationDate: String
)