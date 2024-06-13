package com.example.cognitivetests.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import com.example.cognitivetests.R
import com.example.cognitivetests.view.TrailMakingTestListener
import com.example.cognitivetests.view.TrailMakingTestView

class TrailMakingTestFragment : TestFragment(), TrailMakingTestListener {
    private lateinit var trailMakingTestView: TrailMakingTestView
    private lateinit var timerTextView: TextView
    private var secondsElapsed = 0
    private var mistakeCount = 0
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

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            handleBackPress()
        }
    }

    override fun onTestCompleted() {
        handler.removeCallbacks(runnable)
        val isSaved = showTestCompletedDialog()
        if (isSaved) {
            postResult(secondsElapsed, mistakeCount)
        }
        findNavController().navigate(R.id.action_trailMakingTest_to_mainFragment)
    }

    override fun onTestStarted() {
        handler.post(runnable)
        return
    }

    override fun onMistake() {
        mistakeCount++
        return
    }

    private fun postResult(secondsElapsed: Int, mistakes: Int) {
        //TODO
    }
}