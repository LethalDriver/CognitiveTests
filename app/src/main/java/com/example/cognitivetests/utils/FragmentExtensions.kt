package com.example.cognitivetests.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.cognitivetests.R
import com.google.android.material.snackbar.Snackbar


fun Fragment.showSnackBar(message: String, error: Boolean) {
    val snackbar = Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT)
    snackbar.setTextColor(
        ContextCompat.getColor(
            requireContext(),
            R.color.white
        )
    )
    if (error) {
        snackbar.setBackgroundTint(
            ContextCompat.getColor(
                requireContext(),
                R.color.snackBarError
            )
        )
    } else {
        snackbar.setBackgroundTint(
            ContextCompat.getColor(
                requireContext(),
                R.color.snackBarSuccessful
            )
        )
    }
    snackbar.show()
}

fun Fragment.hideKeyboard(view: View) {
    val inputMethodManager = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}