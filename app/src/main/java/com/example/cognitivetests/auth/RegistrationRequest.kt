package com.example.cognitivetests.auth

import kotlinx.serialization.Serializable

/**
 * Data class for registration request.
 */
@Serializable
data class RegistrationRequest(
        var email: String,
        var password: String,
        var firstName: String,
        var lastName: String,
)