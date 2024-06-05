package com.example.cognitivetests.recyclerView

data class Score(
    val testType: String,
    val dateTime: String,
    val score: Int?,
    val mistakeCount: Int?,
    val time: Int?
)
