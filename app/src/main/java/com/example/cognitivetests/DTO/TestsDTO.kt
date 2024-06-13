package com.example.cognitivetests.DTO

import kotlinx.serialization.Serializable


@Serializable
data class PostStroopTestRequest(
    val datetime: String,
    val mistake_count: Int,
    val total_score: Int,
)

@Serializable
data class PostDigitSubstitutionTestRequest(
    val datetime: String,
    val mistake_count: Int,
    val total_score: Int,
    val time: Int,
)

@Serializable
data class PostTrailMakingTestRequest(
    val datetime: String,
    val mistake_count: Int,
    val total_score: Int,
    val time: Int,
)