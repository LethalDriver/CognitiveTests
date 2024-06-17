package com.example.cognitivetests.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import com.example.cognitivetests.R
import com.example.cognitivetests.viewModel.UserDataViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class EditProfileDataFragment : Fragment() {
    private val viewModel: UserDataViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_profile_data, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val childFragment = UserDataInputFragment.newInstance()
        childFragmentManager.beginTransaction().replace(R.id.child_fragment_container, childFragment).commit()
        val button = view.findViewById<Button>(R.id.button)

        viewModel.fetchCurrentUserData()

        button.setOnClickListener {
            viewModel.updateUserData()
        }
    }


}