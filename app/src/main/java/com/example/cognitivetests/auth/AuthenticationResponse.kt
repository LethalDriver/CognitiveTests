package com.example.cognitivetests.auth

import kotlinx.serialization.Serializable

/**
 * Data class for authentication response.
 */
@Serializable
data class AuthenticationResponse(
        var access_token: String,
        var refresh_token: String,
        var refresh_token_exs: String
)