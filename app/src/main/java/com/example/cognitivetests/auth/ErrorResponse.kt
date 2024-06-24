package com.example.cognitivetests.auth

import kotlinx.serialization.Serializable

/**
 * Data class for error response.
 */
@Serializable
data class ErrorResponse(val detail: String)
