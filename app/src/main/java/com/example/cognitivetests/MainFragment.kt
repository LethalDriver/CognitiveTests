package com.example.cognitivetests

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        val stroopTestButton = view.findViewById<Button>(R.id.button_stroop_test)
        stroopTestButton.setOnClickListener {
            findNavController(view).navigate(R.id.action_mainFragment_to_stroopTest)
        }
        val tmTestButton = view.findViewById<Button>(R.id.button_trail_making_test)
        tmTestButton.setOnClickListener {
            findNavController(view).navigate(R.id.action_mainFragment_to_trailMakingTest)
        }
        val dsTestButton = view.findViewById<Button>(R.id.button_digit_substitution_test)
        dsTestButton.setOnClickListener {
            findNavController(view).navigate(R.id.action_mainFragment_to_digitSubstitutionTest)
        }
        return view
    }

}