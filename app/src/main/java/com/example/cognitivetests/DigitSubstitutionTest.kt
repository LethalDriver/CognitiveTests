package com.example.cognitivetests

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ImageSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.graphics.drawable.toBitmap
import com.google.android.material.button.MaterialButton

class DigitSubstitutionTest : Fragment() {

    private lateinit var buttonIdToDigitMap: Map<Int, Int>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_digit_substitution_test, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val legendButtonsIds = listOf(
            R.id.button,
            R.id.button2,
            R.id.button3,
            R.id.button4,
            R.id.button5,
            R.id.button6,
            R.id.button7,
            R.id.button8,
            R.id.button9
        )

        // Create a list of all button IDs
        val buttonIds = listOf(
            R.id.triangleBtn,
            R.id.circleBtn,
            R.id.lineBtn,
            R.id.arrowUpBtn,
            R.id.crossBtn,
            R.id.arrowLeftBtn,
            R.id.arrowRightBtn,
            R.id.squareBtn,
            R.id.arrowDownBtn
        )

        // Create a list of digits from 1 to 9 and shuffle it
        val digits = (1..9).shuffled()

        // Create a map that associates each button ID with a corresponding digit
        buttonIdToDigitMap = buttonIds.zip(digits).toMap()

        for (i in buttonIdToDigitMap.keys.indices) {
            val button = view.findViewById<MaterialButton>(buttonIds[i])
            val digit = buttonIdToDigitMap[buttonIds[i]]
            val legendButton = view.findViewById<MaterialButton>(legendButtonsIds[digit!! - 1])
            val buttonIcon = button.icon
            val newIcon = buttonIcon.constantState?.newDrawable() // Create a new Drawable instance
            legendButton.background = newIcon
        }


    }


}