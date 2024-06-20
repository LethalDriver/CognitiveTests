package com.example.cognitivetests.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager.widget.ViewPager
import com.example.cognitivetests.R
import com.example.cognitivetests.recyclerView.InstructionAdapter


class InstructionFragment : Fragment() {
    val args: InstructionFragmentArgs by navArgs()
    private lateinit var viewPager: ViewPager
    private lateinit var adapter: InstructionAdapter
    private var instructions = mutableListOf<String>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_instruction, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        when (args.test) {
            "trail_making" -> {
                val splitSentences = getString(R.string.trail_making_instruction).split(".")
                instructions.addAll(splitSentences)
            }
            "stroop" -> {
                val splitSentences = getString(R.string.stroop_instruction).split(".")
                instructions.addAll(splitSentences)
            }
            "digit_substitution" -> {
                val splitSentences = getString(R.string.digit_substitution_instruction).split(".")
                instructions.addAll(splitSentences)
            }
        }

        instructions.removeAt(instructions.size - 1)

        instructions = instructions.map {
            "${it.trim()}."
        }.toMutableList()

        instructions.add("")

        viewPager = view.findViewById(R.id.viewPager)
        adapter = InstructionAdapter(requireContext(), instructions)
        viewPager.adapter = adapter


        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                if (position == instructions.size - 2 && positionOffset > 0) {
                    val navController = findNavController()
                    if (navController.currentDestination?.id == R.id.instructionFragment) {
                        when (args.test) {
                            "stroop" -> {
                                navController.navigate(R.id.action_instructionFragment_to_stroopTest)
                            }
                            "trail_making" -> {
                                navController.navigate(R.id.action_instructionFragment_to_trailMakingTest)
                            }
                            "digit_substitution" -> {
                                navController.navigate(R.id.action_instructionFragment_to_digitSubstitutionTest)
                            }
                        }
                    }
                }
            }

            override fun onPageSelected(position: Int) {
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })
    }
}