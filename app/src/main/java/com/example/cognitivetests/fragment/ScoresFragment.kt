package com.example.cognitivetests.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cognitivetests.DTO.EvaluationsGetRequest
import com.example.cognitivetests.R
import com.example.cognitivetests.recyclerView.Score
import com.example.cognitivetests.recyclerView.ScoresAdapter
import com.example.cognitivetests.service.HttpService
import com.google.android.material.chip.Chip
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class ScoresFragment : Fragment() {

    private val httpService: HttpService by inject()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scores, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)

        val scores = mutableListOf<Score>()

        lifecycleScope.launch {
            val evaluations = getScores()
            val flatScoreArray = mapToFlatScoreArray(evaluations)
            scores.addAll(flatScoreArray)
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = ScoresAdapter(scores)
        }
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
                    scores.filter { it.testType in selectedTestTypes }.toMutableList()
                }
                recyclerView.adapter = ScoresAdapter(filteredScores)
            }
        }
    }

    private suspend fun getScores(): EvaluationsGetRequest {
        return httpService.fetchAllEvaluations()
    }

    private suspend fun mapToFlatScoreArray(evaluations: EvaluationsGetRequest): Array<Score> {
        val scores = mutableListOf<Score>()
        for (evaluation in evaluations.stroop) {
            val testType = "Stroop"
            val date = evaluation.datetime
            val mistakes = evaluation.mistake_count
            val score = Score(testType, date, null, mistakes, null)
            scores.add(score)
        }
        for (evaluation in evaluations.digit_substitution) {
            val testType = "Digit Substitution"
            val date = evaluation.datetime
            val mistakes = evaluation.mistake_count
            val correctAnswers = evaluation.correct_answers
            val score = Score(testType, date, correctAnswers, mistakes, null)
            scores.add(score)
        }
        for (evaluation in evaluations.trail_making) {
            val testType = "Trail Making"
            val date = evaluation.datetime
            val mistakes = evaluation.mistake_count
            val time = evaluation.time
            val score = Score(testType, date, null, mistakes, time)
            scores.add(score)
        }
        return scores.toTypedArray()
    }
}