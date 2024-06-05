package com.example.cognitivetests.recyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cognitivetests.R

class ScoresAdapter(private val myDataset: Array<Score>) :
    RecyclerView.Adapter<ScoreViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.score_item, parent, false)

        return ScoreViewHolder(view)
    }

    override fun onBindViewHolder(holder: ScoreViewHolder, position: Int) {
        holder.testType.text = myDataset[position].testType
        holder.dateTime.text = myDataset[position].dateTime
        if (myDataset[position].score != null) {
            holder.score.text = myDataset[position].score.toString()
        } else if (myDataset[position].mistakeCount != null) {
            holder.score.text = myDataset[position].mistakeCount.toString()
        }
        if (myDataset[position].time != null) {
            holder.time.text = myDataset[position].time.toString()
        }
    }

    override fun getItemCount() = myDataset.size
}