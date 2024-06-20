package com.example.cognitivetests.fragment

import android.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.cognitivetests.R
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

open class TestFragment : Fragment() {
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