package com.example.cognitivetests.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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

/**
 * Fragment for User Profile.
 * This fragment handles the UI and logic for displaying and interacting with the user profile.
 * It extends the Fragment class.
 */
class UserProfileFragment : Fragment() {

    private val httpService: HttpService by inject()
    private val tokenManager: TokenManager by inject()

    /**
     * Inflates the layout for this fragment.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_profile, container, false)
    }

    /**
     * Sets up the view components and logic for the User Profile.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textViewFirstName = view.findViewById<TextView>(R.id.firstName)
        val textViewLastName = view.findViewById<TextView>(R.id.lastName)
        val textViewEmail = view.findViewById<TextView>(R.id.email)
        val editProfileBtn = view.findViewById<TextView>(R.id.editProfileButton)
        val logoutButton = view.findViewById<TextView>(R.id.logoutButton)
        val statsButton = view.findViewById<Button>(R.id.statsButton)

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

        statsButton.setOnClickListener {
            findNavController().navigate(R.id.action_userProfileFragment_to_statsFragment)
        }
    }

    /**
     * Fetches the user data.
     * @return UserDTO object containing the user data.
     */
    private suspend fun fetchUserData(): UserDTO {
        return httpService.fetchUserInfo()
    }
}