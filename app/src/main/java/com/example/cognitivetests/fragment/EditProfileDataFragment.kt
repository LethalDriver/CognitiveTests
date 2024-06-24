package com.example.cognitivetests.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.cognitivetests.R
import com.example.cognitivetests.viewModel.UserDataViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * Fragment for editing user profile data.
 * This fragment handles the UI and logic for editing user profile data.
 * It extends the Fragment class.
 */
class EditProfileDataFragment : Fragment() {
    private val viewModel: UserDataViewModel by viewModel()

    /**
     * Called when the fragment is being created.
     * @param savedInstanceState If the fragment is being re-created from a previous saved state, this is the state.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    /**
     * Called to have the fragment instantiate its user interface view.
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_profile_data, container, false)
    }

    /**
     * Called immediately after onCreateView(LayoutInflater, ViewGroup, Bundle) has returned, but before any saved state has been restored in to the view.
     * @param view The View returned by onCreateView(LayoutInflater, ViewGroup, Bundle).
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val childFragment = UserDataInputFragment.newInstance()
        childFragmentManager.beginTransaction().replace(R.id.child_fragment_container, childFragment).commit()
        val button = view.findViewById<Button>(R.id.button)

        viewModel.fetchCurrentUserData()

        button.setOnClickListener {
            viewModel.updateUserData()
            findNavController().navigate(R.id.action_editUserData_to_userProfileFragment)
        }
    }
}