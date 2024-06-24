package com.example.cognitivetests.recyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cognitivetests.R
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class StatsAdapter(private val myDataset: MutableList<Stat>) :
    RecyclerView.Adapter<ScoreViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.score_item, parent, false)

        return ScoreViewHolder(view)
    }

    override fun onBindViewHolder(holder: ScoreViewHolder, position: Int) {
        val myDateObj = LocalDateTime.parse(myDataset[position].lastTestDate);
        val format = DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy", Locale.ENGLISH)
        val formattedDate = format.format(myDateObj)

        holder.testType.text = myDataset[position].testType
        holder.dateTime.text = "Last test date: $formattedDate"
        if (myDataset[position].topScore != null) {
            holder.score.text = "Top score: ${myDataset[position].topScore}"
            holder.score.visibility = View.VISIBLE
        } else {
            holder.score.visibility = View.GONE
        }

        if (myDataset[position].meanTotalScore != null) {
            holder.time.text = "Mean total score: ${myDataset[position].meanTotalScore} seconds"
            holder.time.visibility = View.VISIBLE
        } else {
            holder.time.visibility = View.GONE
        }

        if (myDataset[position].meanMistakeCount != null) {
            holder.mistakeCount.text = "Mean mistake count: ${myDataset[position].meanMistakeCount}"
            holder.mistakeCount.visibility = View.VISIBLE
        } else {
            holder.mistakeCount.visibility = View.GONE
        }

        if (myDataset[position].testCount != null) {
            holder.totalScore.text = "Test count: ${myDataset[position].testCount}"
            holder.totalScore.visibility = View.VISIBLE
        } else {
            holder.totalScore.visibility = View.GONE
        }
    }

    override fun getItemCount() = myDataset.size
}