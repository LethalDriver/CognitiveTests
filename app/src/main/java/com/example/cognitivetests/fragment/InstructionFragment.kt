package com.example.cognitivetests.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.cognitivetests.R


class InstructionFragment : Fragment() {
    private val args: com.example.cognitivetests.InstructionFragmentArgs by navArgs()
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
            "digit_substitution" -> {
                instructionTextView.text = getString(R.string.digit_substitution_instruction)
            }
        }
        startButton.setOnClickListener {
            when (args.test) {
                "stroop" -> {
                    findNavController().navigate(R.id.action_instructionFragment_to_stroopTest)
                }
                "trail_making" -> {
                    findNavController().navigate(R.id.action_instructionFragment_to_trailMakingTest)
                }
                "digit_substitution" -> {
                    findNavController().navigate(com.example.cognitivetests.InstructionFragmentDirections.actionInstructionFragmentToDigitSubstitutionTest())
                }
            }
        }
    }
}