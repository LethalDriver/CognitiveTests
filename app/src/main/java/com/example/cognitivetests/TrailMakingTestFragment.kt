package com.example.cognitivetests

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class TrailMakingTestFragment : Fragment(), TrailMakingTestListener {
    private lateinit var trailMakingTestView: TrailMakingTestView
    private lateinit var timerTextView: TextView
    private var secondsElapsed = 0
    private val handler = Handler(Looper.getMainLooper())
    private val runnable = object : Runnable {
        override fun run() {
            secondsElapsed++
            timerTextView.text = secondsElapsed.toString()
            handler.postDelayed(this, 1000)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_trail_making_test, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        trailMakingTestView = view.findViewById(R.id.trailMakingTestView)
        timerTextView = view.findViewById(R.id.tvTimer)
        trailMakingTestView.setTrailMakingTestListener(this)
    }

    override fun onTestCompleted() {
        Toast.makeText(context, "Test completed.", Toast.LENGTH_SHORT).show()
    }

    override fun onTestStarted() {
        handler.post(runnable)
        return
    }

    override fun onMistake() {
        // TODO
    }
}