package com.example.cognitivetests.fragment

import android.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.cognitivetests.R
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * Base Fragment for Tests.
 * This fragment handles the common UI and logic for all test fragments.
 * It extends the Fragment class.
 */
open class TestFragment : Fragment() {

    /**
     * Handles the back press event.
     * Shows a dialog to confirm if the user wants to abandon the test.
     */
    fun handleBackPress() {
        AlertDialog.Builder(requireContext())
            .setTitle("Abandon Test")
            .setMessage("Are you sure you want to abandon the test?")
            .setPositiveButton("Yes") { _, _ ->
                findNavController().navigate(R.id.mainFragment)
            }
            .setNegativeButton("No", null)
            .show()
    }

    /**
     * Shows a dialog when the test is completed.
     * Asks the user if they want to save the results of the test.
     * @return Boolean indicating if the user wants to save the results.
     */
    suspend fun showTestCompletedDialog(): Boolean = suspendCoroutine { continuation ->
        AlertDialog.Builder(requireContext())
            .setTitle("Test Completed")
            .setMessage("Congratulations! You have completed the test. Do you want to save the results of the test?")
            .setPositiveButton("Yes") { _, _ ->
                continuation.resume(true)
            }
            .setNegativeButton("No") { _, _ ->
                continuation.resume(false)
            }
            .setOnCancelListener {
                continuation.resume(false)
            }
            .show()
    }
}