package com.example.cognitivetests.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.example.cognitivetests.viewModel.LoginViewModel
import com.example.cognitivetests.activity.MainActivity
import com.example.cognitivetests.R
import com.example.cognitivetests.utils.showSnackBar
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Fragment for user login.
 * This fragment handles the UI and logic for user login.
 * It extends the Fragment class.
 */
class LoginFragment : Fragment() {
    private val viewModel: LoginViewModel by viewModel()

    /**
     * Inflates the layout for this fragment.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    /**
     * Sets up the view components and logic for the login.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ... rest of the code ...
    }

    /**
     * Observes the authentication state and updates the UI accordingly.
     */
    private fun observeAuthenticationState() {
        viewModel.authenticationState.observe(viewLifecycleOwner) { authenticationState ->
            // ... rest of the code ...
        }
    }

    /**
     * Observes the exception message and shows a snack bar with the message when it changes.
     */
    private fun observeExceptionMessage() {
        viewModel.exceptionMessage.observe(viewLifecycleOwner) { message ->
            showSnackBar(message, true)
        }
    }
}