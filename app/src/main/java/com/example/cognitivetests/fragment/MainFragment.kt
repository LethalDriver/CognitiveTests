package com.example.cognitivetests.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation.findNavController
import com.example.cognitivetests.R

class MainFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        val stroopTestButton = view.findViewById<Button>(R.id.button_stroop_test)
        stroopTestButton.setOnClickListener {
            val action =
                com.example.cognitivetests.fragment.MainFragmentDirections.actionMainFragmentToInstructionFragment(
                    "stroop"
                )
            findNavController(view).navigate(action)
        }
        val tmTestButton = view.findViewById<Button>(R.id.button_trail_making_test)
        tmTestButton.setOnClickListener {
            val action =
                com.example.cognitivetests.fragment.MainFragmentDirections.actionMainFragmentToInstructionFragment(
                    "trail_making"
                )
            findNavController(view).navigate(action)
        }
        val dsTestButton = view.findViewById<Button>(R.id.button_digit_substitution_test)
        dsTestButton.setOnClickListener {
            val action =
                com.example.cognitivetests.fragment.MainFragmentDirections.actionMainFragmentToInstructionFragment(
                    "digit_substitution"
                )
            findNavController(view).navigate(action)
        }
        return view
    }

}