package com.example.cognitivetests.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.cognitivetests.R
import com.example.cognitivetests.activity.MainActivity
import com.example.cognitivetests.viewModel.LoginViewModel
import com.example.cognitivetests.viewModel.UserDataViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterFragment : Fragment() {
    private val viewModel: UserDataViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val childFragment = UserDataInputFragment.newInstance()
        childFragmentManager.beginTransaction().replace(R.id.child_fragment_container, childFragment).commit()
        val button = view.findViewById<Button>(R.id.button)

        button.setOnClickListener {
            viewModel.register()
        }

        viewModel.authenticationState.observe(viewLifecycleOwner) { authenticationState ->
            when (authenticationState) {
                UserDataViewModel.AuthenticationState.LOADING -> {
                    button.isEnabled = false
                }

                UserDataViewModel.AuthenticationState.AUTHENTICATED -> {
                    button.isEnabled = true
                    val intent = Intent(requireContext(), MainActivity::class.java)
                    startActivity(intent)
                    activity?.finish()
                }

                UserDataViewModel.AuthenticationState.FAILED -> {
                    button.isEnabled = true
                }
                null -> {
                    button.isEnabled = true
                }
            }
        }

        val loginLink = view.findViewById<TextView>(R.id.loginLink)

        loginLink.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }
}