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


/**
 * Fragment for displaying instructions for a specific test.
 * This fragment handles the UI and logic for displaying instructions.
 * It extends the Fragment class.
 */
class InstructionFragment : Fragment() {
    val args: InstructionFragmentArgs by navArgs()
    private lateinit var viewPager: ViewPager
    private lateinit var adapter: InstructionAdapter
    private var instructions = mutableListOf<String>()

    /**
     * Inflates the layout for this fragment.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_instruction, container, false)
    }

    /**
     * Sets up the view components and logic for the instructions.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ... rest of the code ...
    }

    /**
     * Handles the logic for page scrolling in the ViewPager.
     * Navigates to the appropriate test fragment when the last instruction is reached.
     */
    private fun setupPageChangeListener() {
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                // ... rest of the code ...
            }

            override fun onPageSelected(position: Int) {
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })
    }
}