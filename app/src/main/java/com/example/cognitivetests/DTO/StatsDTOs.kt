package com.example.cognitivetests.DTO

import kotlinx.serialization.Serializable

@Serializable
data class statsResponse(
    val user_stats: UserStats
)

@Serializable
data class UserStats(
    val stroop: TestStat? = null,
    val trail_making: TestStat? = null,
    val digit_substitution: TestStat? = null
)
@Serializable
data class TestStat(
    val top_score: Int?,
    val mean_total_score: Double?,
    val mean_mistake_count: Double?,
    val last_test_date: String?,
    val test_count: Int?
)