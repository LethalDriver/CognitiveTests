package com.example.cognitivetests.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.example.cognitivetests.DTO.UserDTO
import com.example.cognitivetests.R
import com.example.cognitivetests.service.HttpService
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class UserProfileFragment : Fragment() {

    private val httpService: HttpService by inject()
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

        lifecycleScope.launch {
            val user = fetchUserData()
            textViewFirstName.text = user.first_name
            textViewLastName.text = user.last_name
            textViewEmail.text = user.email
        }
    }

    private suspend fun fetchUserData(): UserDTO {
        val user = httpService.fetchUserInfo()
        return user
    }
}