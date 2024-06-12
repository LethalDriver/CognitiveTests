package com.example.cognitivetests

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel


class UserDataInputFragment : Fragment() {
    private val viewModel: UserDataViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_user_data_input, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val editTextFirstName = view.findViewById<EditText>(R.id.editTextFirstName)
        val editTextLastName = view.findViewById<EditText>(R.id.editTextLastName)
        val editTextEmail = view.findViewById<EditText>(R.id.editTextEmail)
        val editTextPassword = view.findViewById<EditText>(R.id.editTextPassword)
        val editTextConfirmPassword = view.findViewById<EditText>(R.id.editTextConfirmPassword)


        editTextFirstName.addTextChangedListener { text ->
            viewModel.updateFirstName(text.toString())
            if (!viewModel.isFirstNameValid()) {
                editTextFirstName.error = "First name cannot be blank"
            }
        }

        editTextLastName.addTextChangedListener { text ->
            viewModel.updateLastName(text.toString())
            if (!viewModel.isLastNameValid()) {
                editTextLastName.error = "Last name cannot be blank"
            }
        }

        editTextEmail.addTextChangedListener { text ->
            viewModel.updateEmail(text.toString())
            if (!viewModel.isEmailValid()) {
                editTextEmail.error = "Invalid email"
            }
        }

        editTextPassword.addTextChangedListener { text ->
            viewModel.updatePassword(text.toString())
            if (!viewModel.isPasswordValid()) {
                editTextPassword.error = "Password must be at least 8 characters long, " +
                        "contain a number, and a special character"
            }
        }

        editTextConfirmPassword.addTextChangedListener { text ->
            viewModel.updateConfirmPassword(text.toString())
            if (!viewModel.isConfirmPasswordValid()) {
                editTextConfirmPassword.error = "Passwords do not match"
            }
        }

    }
}