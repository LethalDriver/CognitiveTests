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

/**
 * Fragment for displaying scores.
 * This fragment handles the UI and logic for displaying scores of different tests.
 * It extends the Fragment class.
 */
class ScoresFragment : Fragment() {
    private val httpService: HttpService by inject()

    /**
     * Inflates the layout for this fragment.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_scores, container, false)
    }

    /**
     * Sets up the view components and logic for the scores.
     */
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

        setupChipFilters(view, scores, recyclerView)
    }

    /**
     * Sets up the chip filters for the scores.
     */
    private fun setupChipFilters(view: View, scores: MutableList<Score>, recyclerView: RecyclerView) {
        val stroopChip = view.findViewById<Chip>(R.id.stroopChip)
        val tmtChip = view.findViewById<Chip>(R.id.tmtChip)
        val digitSubChip = view.findViewById<Chip>(R.id.digitSubChip)

        val chips = arrayOf(stroopChip, tmtChip, digitSubChip)
        val selectedTestTypes = mutableListOf<String>()

        for (chip in chips) {
            chip.isCheckable = true
            chip.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    selectedTestTypes.add(chip.text.toString())
                } else {
                    selectedTestTypes.remove(chip.text.toString())
                }

                val filteredScores = if (selectedTestTypes.isEmpty()) {
                    scores
                } else {
                    scores.filter { it.testType in selectedTestTypes }.toMutableList()
                }
                recyclerView.adapter = ScoresAdapter(filteredScores)
            }
        }
    }

    /**
     * Fetches all evaluations.
     */
    private suspend fun getScores(): EvaluationsGetRequest {
        return httpService.fetchAllEvaluations()
    }

    /**
     * Maps the evaluations to a flat score array.
     */
    private suspend fun mapToFlatScoreArray(evaluations: EvaluationsGetRequest): Array<Score> {
        val scores = mutableListOf<Score>()
        for (evaluation in evaluations.stroop) {
            val testType = "Stroop"
            val date = evaluation.datetime
            val mistakes = evaluation.mistake_count
            val totalScore = evaluation.total_score
            val score = Score(testType, date, null, mistakes, null, totalScore)
            scores.add(score)
        }
        for (evaluation in evaluations.digit_substitution) {
            val testType = "Digit Substitution"
            val date = evaluation.datetime
            val mistakes = evaluation.mistake_count
            val correctAnswers = evaluation.correct_answers
            val totalScore = evaluation.total_score
            val score = Score(testType, date, correctAnswers, mistakes, null, totalScore)
            scores.add(score)
        }
        for (evaluation in evaluations.trail_making) {
            val testType = "Trail Making"
            val date = evaluation.datetime
            val mistakes = evaluation.mistake_count
            val time = evaluation.time
            val totalScore = evaluation.total_score
            val score = Score(testType, date, null, mistakes, time, totalScore)
            scores.add(score)
        }
        return scores.toTypedArray()
    }
}