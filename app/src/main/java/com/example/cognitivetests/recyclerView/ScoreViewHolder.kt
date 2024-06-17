package com.example.cognitivetests.recyclerView

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cognitivetests.R
class ScoreViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val testType: TextView = view.findViewById(R.id.testTypeTv)
    val dateTime: TextView = view.findViewById(R.id.testDateTv)
    val score: TextView = view.findViewById(R.id.testScoreTv)
    val time: TextView = view.findViewById(R.id.testTimeTv)
    val mistakeCount: TextView = view.findViewById(R.id.testMistakeCount)
    val totalScore: TextView = view.findViewById(R.id.testTotalScore)
}