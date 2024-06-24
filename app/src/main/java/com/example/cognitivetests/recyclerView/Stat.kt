package com.example.cognitivetests.recyclerView

data class Stat(
    val testType: String,
    val topScore: Int?,
    val meanTotalScore: Double?,
    val meanMistakeCount: Double?,
    val lastTestDate: String?,
    val testCount: Int?
)