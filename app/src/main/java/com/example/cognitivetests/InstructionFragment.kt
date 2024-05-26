package com.example.cognitivetests

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs


class InstructionFragment : Fragment() {
    val args: InstructionFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_instruction, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val instructionTextView = view.findViewById<TextView>(R.id.tvInstruction)
        val startButton = view.findViewById<TextView>(R.id.btnStartTest)
        when (args.test) {
            "trail_making" -> {
                instructionTextView.text = getString(R.string.trail_making_instruction)
            }
            "stroop" -> {
                instructionTextView.text = getString(R.string.stroop_instruction)
            }
        }
        startButton.setOnClickListener {
            when (args.test) {
                "trail_making" -> {
                    findNavController().navigate(InstructionFragmentDirections.actionInstructionFragmentToTrailMakingTest())
                }

                "stroop" -> {
                    findNavController().navigate(InstructionFragmentDirections.actionInstructionFragmentToStroopTest())
                }
            }
        }
    }
}