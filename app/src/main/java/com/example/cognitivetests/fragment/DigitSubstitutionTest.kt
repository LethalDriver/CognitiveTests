package com.example.cognitivetests.fragment

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.cognitivetests.DTO.PostDigitSubstitutionTestRequest
import com.example.cognitivetests.R
import com.example.cognitivetests.service.HttpService
import com.google.android.material.button.MaterialButton
import com.google.android.material.progressindicator.LinearProgressIndicator
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Fragment for the Digit Substitution Test.
 * This fragment handles the UI and logic for the Digit Substitution Test.
 * It extends the TestFragment class.
 */
class DigitSubstitutionTest() : TestFragment() {
    private lateinit var buttonIdToDigitMap: Map<Int, Int>
    private lateinit var countDownTimer: CountDownTimer
    private val httpService: HttpService by inject()
    private var goodAnswers = 0
    private var mistakes = 0
    private var currentDigit = 1

    /**
     * Inflates the layout for this fragment.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_digit_substitution_test, container, false)
    }

    /**
     * Sets up the view components and logic for the test.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val progressIndicator = view.findViewById<LinearProgressIndicator>(R.id.progressIndicator)
        val digitTv = view.findViewById<TextView>(R.id.digitTv)

        progressIndicator.max = 120

        progressIndicator.progress = 0

        currentDigit = (1..9).random()
        digitTv.text = currentDigit.toString()


        countDownTimer = object : CountDownTimer(120000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                progressIndicator.progress += 1
            }

            override fun onFinish() {
                lifecycleScope.launch {
                    val isSaved = showTestCompletedDialog()
                    if (isSaved) {
                        postResult(goodAnswers, mistakes)
                    }
                    findNavController().navigate(R.id.action_digitSubstitutionTest_to_mainFragment)
                }
            }
        }

        countDownTimer.start()

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
            val legendButton = view.findViewById<Button>(legendButtonsIds[digit!! - 1])
            val buttonIcon = button.icon
            val newIcon = buttonIcon.constantState?.newDrawable() // Create a new Drawable instance
            context?.let { ContextCompat.getColor(it, R.color.white) }?.let { newIcon?.setTint(it) } // Set the tint to white
            legendButton.background = newIcon
        }


        for (buttonId in buttonIds) {
            val button = view.findViewById<MaterialButton>(buttonId)
            button.setOnClickListener {
                if (buttonIdToDigitMap[buttonId] == currentDigit) {
                    goodAnswers++
                } else {
                    mistakes++
                }
                currentDigit = (1..9).random()
                digitTv.text = currentDigit.toString()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            handleBackPress()
        }

    }

    /**
     * Cancels the countdown timer when the fragment is destroyed.
     */
    override fun onDestroy() {
        super.onDestroy()
        countDownTimer.cancel()
    }

    /**
     * Posts the result of the test to the server.
     * @param goodAnswers The number of correct answers.
     * @param mistakes The number of mistakes.
     */
    private suspend fun postResult(goodAnswers: Int, mistakes: Int) {
        val currentDateTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")

        val result = PostDigitSubstitutionTestRequest(
            currentDateTime.format(formatter),
            mistakes,
            goodAnswers,
        )

        httpService.postDigitSubstitutionResult(result)
    }

}