package com.example.cognitivetests.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cognitivetests.DTO.EvaluationsGetRequest
import com.example.cognitivetests.DTO.UserStats
import com.example.cognitivetests.R
import com.example.cognitivetests.recyclerView.Score
import com.example.cognitivetests.recyclerView.Stat
import com.example.cognitivetests.recyclerView.StatsAdapter
import com.example.cognitivetests.service.HttpService
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject


/**
 * Fragment for displaying statistics.
 * This fragment handles the UI and logic for displaying statistics of different tests.
 * It extends the Fragment class.
 */
class StatsFragment : Fragment() {
    private val httpService: HttpService by inject()

    /**
     * Inflates the layout for this fragment.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_stats, container, false)
    }

    /**
     * Sets up the view components and logic for the statistics.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        val stats = mutableListOf<Stat>()

        lifecycleScope.launch {
            try {
                val statsResponse = httpService.fetchStats()
                val statsObj = statsResponse.user_stats
                stats.addAll(mapToFlatStatArray(statsObj))
                recyclerView.layoutManager = LinearLayoutManager(context)
                recyclerView.adapter = StatsAdapter(stats)
            } catch (e: Exception) {
                Log.e("StatsFragment", "Error fetching or processing stats", e)
            }
        }
    }

    /**
     * Maps the user statistics to a flat stat array.
     */
    private fun mapToFlatStatArray(user_stats: UserStats): Array<Stat> {
        val stats = mutableListOf<Stat>()
        if (user_stats.stroop != null) {
            stats.add(Stat("Stroop", user_stats.stroop.top_score, user_stats.stroop.mean_total_score, user_stats.stroop.mean_mistake_count, user_stats.stroop.last_test_date, user_stats.stroop.test_count))
        }
        if (user_stats.trail_making != null) {
            stats.add(Stat("Trail Making", user_stats.trail_making.top_score, user_stats.trail_making.mean_total_score, user_stats.trail_making.mean_mistake_count, user_stats.trail_making.last_test_date, user_stats.trail_making.test_count))
        }
        if (user_stats.digit_substitution != null) {
            stats.add(Stat("Digit Substitution", user_stats.digit_substitution.top_score, user_stats.digit_substitution.mean_total_score, user_stats.digit_substitution.mean_mistake_count, user_stats.digit_substitution.last_test_date, user_stats.digit_substitution.test_count))
        }

        return stats.toTypedArray()
    }
}