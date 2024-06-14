package com.example.cognitivetests.DTO

import kotlinx.serialization.Serializable


@Serializable
data class PostStroopTestRequest(
    val datetime: String,
    val mistake_count: Int,
)

@Serializable
data class GetStroopTestRequest(
    val stroop_id: Int,
    val version: Int,
    val datetime: String,
    val mistake_count: Int,
)

@Serializable
data class PostDigitSubstitutionTestRequest(
    val datetime: String,
    val mistake_count: Int,
    val correct_answers: Int,
)

@Serializable
data class GetDigitSubstitutionTestRequest(
    val digit_substitution_id: Int,
    val version: Int,
    val datetime: String,
    val mistake_count: Int,
    val correct_answers: Int,
)

@Serializable
data class PostTrailMakingTestRequest(
    val datetime: String,
    val mistake_count: Int,
    val time: Int,
)

@Serializable
data class GetTrailMakingTestRequest(
    val trail_making_id: Int,
    val version: Int,
    val datetime: String,
    val mistake_count: Int,
    val time: Int,
)

@Serializable
data class EvaluationsGetRequest(
    val stroop: List<GetStroopTestRequest>,
    val digit_substitution: List<GetDigitSubstitutionTestRequest>,
    val trail_making: List<GetTrailMakingTestRequest>
)