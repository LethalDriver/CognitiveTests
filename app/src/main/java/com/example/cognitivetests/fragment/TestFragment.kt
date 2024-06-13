package com.example.cognitivetests.fragment

import android.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

open class TestFragment : Fragment() {
    fun handleBackPress() {
        AlertDialog.Builder(requireContext())
            .setTitle("Abandon Test")
            .setMessage("Are you sure you want to abandon the test?")
            .setPositiveButton("Yes") { _, _ ->
                findNavController().popBackStack()
            }
            .setNegativeButton("No", null)
            .show()
    }

    fun showTestCompletedDialog(): Boolean {
        var result = false
        AlertDialog.Builder(requireContext())
            .setTitle("Test Completed")
            .setMessage("Congratulations! You have completed the test. Do you want to save the results of the test?")
            .setPositiveButton("Yes") { _, _ ->
                result = true
            }
            .setNegativeButton("No") { _, _ ->
                result = false
            }
            .show()
        return result
    }
}