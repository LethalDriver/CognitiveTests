package com.example.cognitivetests.DTO

import kotlinx.serialization.Serializable

@Serializable
data class UserDTO(
    val email: String,
    val first_name: String,
    val last_name: String,
    val version: Int,
)