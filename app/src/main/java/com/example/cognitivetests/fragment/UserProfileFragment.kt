package com.example.cognitivetests.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.cognitivetests.DTO.UserDTO
import com.example.cognitivetests.R
import com.example.cognitivetests.activity.MainActivity
import com.example.cognitivetests.activity.StartActivity
import com.example.cognitivetests.service.HttpService
import com.example.cognitivetests.service.TokenManager
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class UserProfileFragment : Fragment() {

    private val httpService: HttpService by inject()
    private val tokenManager: TokenManager by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textViewFirstName = view.findViewById<TextView>(R.id.firstName)
        val textViewLastName = view.findViewById<TextView>(R.id.lastName)
        val textViewEmail = view.findViewById<TextView>(R.id.email)
        val editProfileBtn = view.findViewById<TextView>(R.id.editProfileButton)
        val logoutButton = view.findViewById<TextView>(R.id.logoutButton)
        val deleteAccountButton = view.findViewById<TextView>(R.id.deleteAccountButton)

        lifecycleScope.launch {
            val user = fetchUserData()
            textViewFirstName.text = user.first_name
            textViewLastName.text = user.last_name
            textViewEmail.text = user.email
        }

        editProfileBtn.setOnClickListener {
            findNavController().navigate(R.id.action_userProfileFragment_to_editUserData)
        }

        logoutButton.setOnClickListener {
            tokenManager.deleteTokens()
            val intent = Intent(requireContext(), StartActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }


    }

    private suspend fun fetchUserData(): UserDTO {
        return httpService.fetchUserInfo()
    }
}