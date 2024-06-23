package com.example.cognitivetests.recyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cognitivetests.R
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class ScoresAdapter(private val myDataset: MutableList<Score>) :
    RecyclerView.Adapter<ScoreViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.score_item, parent, false)

        return ScoreViewHolder(view)
    }

    override fun onBindViewHolder(holder: ScoreViewHolder, position: Int) {
        val myDateObj = LocalDateTime.parse(myDataset[position].dateTime);
        val format = DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy", Locale.ENGLISH)
        val formattedDate = format.format(myDateObj)

        holder.testType.text = myDataset[position].testType
        holder.dateTime.text = "Date: $formattedDate"
        if (myDataset[position].score != null) {
            holder.score.text = "Score: ${myDataset[position].score}"
            holder.score.visibility = View.VISIBLE
        } else {
            holder.score.visibility = View.GONE
        }

        if (myDataset[position].time != null) {
            holder.time.text = "Time: ${myDataset[position].time} seconds"
            holder.time.visibility = View.VISIBLE
        } else {
            holder.time.visibility = View.GONE
        }

        if (myDataset[position].mistakeCount != null) {
            holder.mistakeCount.text = "Mistakes: ${myDataset[position].mistakeCount}"
            holder.mistakeCount.visibility = View.VISIBLE
        } else {
            holder.mistakeCount.visibility = View.GONE
        }

        if (myDataset[position].totalScore != null) {
            holder.totalScore.text = "Total Score: ${myDataset[position].totalScore}"
            holder.totalScore.visibility = View.VISIBLE
        } else {
            holder.totalScore.visibility = View.GONE
        }
    }

    override fun getItemCount() = myDataset.size
}