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
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {
    private val viewModel: LoginViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        if (viewModel.isUserLoggedIn()) {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        }

        val registerLink = view.findViewById<TextView>(R.id.registerLink)
        val editTextEmail = view.findViewById<TextView>(R.id.editTextEmail)
        val editTextPassword = view.findViewById<TextView>(R.id.editTextPassword)
        val loginButton = view.findViewById<TextView>(R.id.loginButton)

        registerLink.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        editTextEmail.addTextChangedListener { text ->
            viewModel.updateEmail(text.toString())
        }

        editTextPassword.addTextChangedListener { text ->
            viewModel.updatePassword(text.toString())
        }

        loginButton.setOnClickListener {
            viewModel.authenticate()
        }

        viewModel.authenticationState.observe(viewLifecycleOwner) { authenticationState ->
            when (authenticationState) {
                LoginViewModel.AuthenticationState.LOADING -> {
                    loginButton.isEnabled = false
                }

                LoginViewModel.AuthenticationState.AUTHENTICATED -> {
                    loginButton.isEnabled = true
                    val intent = Intent(requireContext(), MainActivity::class.java)
                    startActivity(intent)
                    activity?.finish()
                }

                LoginViewModel.AuthenticationState.FAILED -> {
                    loginButton.isEnabled = true
                }
                null -> {
                    loginButton.isEnabled = true
                }
            }
        }
        viewModel.exceptionMessage.observe(viewLifecycleOwner) { message ->

        }
    }
}