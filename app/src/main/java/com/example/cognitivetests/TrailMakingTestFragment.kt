package com.example.cognitivetests

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast

class TrailMakingTestFragment : Fragment(), TrailMakingTestListener {
    private lateinit var trailMakingTestView: TrailMakingTestView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_trail_making_test, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        trailMakingTestView = view.findViewById(R.id.trailMakingTestView)
        trailMakingTestView.setTrailMakingTestListener(this)

        val clearButton: Button = view.findViewById(R.id.clearButton)
        clearButton.setOnClickListener {
            trailMakingTestView.clearLine()
        }
    }

    override fun onTestCompleted(isOrderCorrect: Boolean) {
        Toast.makeText(context, "Test completed. Order is correct: $isOrderCorrect", Toast.LENGTH_SHORT).show()
    }

    override fun onTestStarted() {
        return
    }

    override fun onMistake() {
        Toast.makeText(context, "Mistake made", Toast.LENGTH_SHORT).show()
    }
}