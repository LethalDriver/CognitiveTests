package com.example.cognitivetests

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cognitivetests.recyclerView.Score
import com.example.cognitivetests.recyclerView.ScoresAdapter
import com.google.android.material.chip.Chip

class ScoresFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scores, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val scores = arrayOf(
            Score("Trail Making", "2021-09-01", 10, null, null),
            Score("Stroop", "2021-09-01", null, 3, 60),
        )
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = ScoresAdapter(scores)

        val stroopChip = view.findViewById<Chip>(R.id.stroopChip)
        val tmtChip = view.findViewById<Chip>(R.id.tmtChip)
        val digitSubChip = view.findViewById<Chip>(R.id.digitSubChip)

        val chips = arrayOf(stroopChip, tmtChip, digitSubChip)

        val selectedTestTypes = mutableListOf<String>()

        for (chip in chips) {
            chip.isCheckable = true
            chip.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    // The chip has been checked
                    selectedTestTypes.add(chip.text.toString())
                } else {
                    // The chip has been unchecked
                    selectedTestTypes.remove(chip.text.toString())
                }

                // Filter the scores based on the selected test types
                val filteredScores = if (selectedTestTypes.isEmpty()) {
                    scores
                } else {
                    scores.filter { it.testType in selectedTestTypes }.toTypedArray()
                }
                recyclerView.adapter = ScoresAdapter(filteredScores)
            }
        }
    }
}