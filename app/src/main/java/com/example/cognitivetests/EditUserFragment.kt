package com.example.cognitivetests

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.cognitivetests.fragment.UserDataInputFragment
import com.example.cognitivetests.viewModel.UserDataViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditUserFragment : Fragment() {
    private val viewModel: UserDataViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.fetchCurrentUserData()

        val childFragment = UserDataInputFragment.newInstance()
        childFragmentManager.beginTransaction()
            .replace(R.id.child_fragment_container, childFragment)
            .commit()

        val button = view.findViewById<Button>(R.id.button)


    }
}