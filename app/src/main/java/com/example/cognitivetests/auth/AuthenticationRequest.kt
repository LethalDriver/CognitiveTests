package com.example.cognitivetests.auth

import kotlinx.serialization.Serializable

/**
 * Data class for authentication request.
 */
@Serializable
data class AuthenticationRequest(
    var email: String,
    var password: String
)