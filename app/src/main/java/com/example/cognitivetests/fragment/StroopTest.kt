package com.example.cognitivetests.fragment

// Android imports
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.RippleDrawable
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback

// AndroidX imports
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.cognitivetests.DTO.PostStroopTestRequest
import com.example.cognitivetests.R
import com.example.cognitivetests.service.HttpService
import com.example.cognitivetests.utils.showSnackBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

// Kotlin and Coroutine imports
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import org.koin.android.ext.android.inject
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.coroutines.resume

class StroopTest() : TestFragment() {
    private lateinit var speechRecognizer: SpeechRecognizer
    private val httpService: HttpService by inject()
    private val colors = mapOf(
        "red" to R.color.red,
        "green" to R.color.green,
        "blue" to R.color.blue,
        "yellow" to R.color.yellow,
        "brown" to R.color.brown,
        "purple" to R.color.purple,
    )
    private var displayedColor: String? = null
    private val roundsNb = 20
    private var score = 0
    private var currentRound = 0

    // Register for activity result for permission request
    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        if (!isGranted){
            Toast.makeText(context, "Please enable permission to record audio to take the test.", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_stroopTest_to_mainFragment)        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_stroop_test, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val listenButton = view.findViewById<FloatingActionButton>(R.id.speechListenButton)
        val startTestButton = view.findViewById<Button>(R.id.startTestButton)
        val scoreTv = view.findViewById<TextView>(R.id.scoreTv)

        startTestButton.setOnClickListener {
            startTestButton.visibility = View.GONE
            listenButton.visibility = View.VISIBLE
            lifecycleScope.launch {
                listenButton.isEnabled = false
                displayedColor = showColor(3000L)
                listenButton.isEnabled = true
            }
        }


        listenButton.setOnClickListener {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                requestPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
            }
            lifecycleScope.launch {
                listenForColor()
                nextRound(scoreTv, listenButton)
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            handleBackPress()
        }

    }

    private suspend fun nextRound(
        scoreTv: TextView,
        listenButton: FloatingActionButton
    ) {
        currentRound++
        scoreTv.text = "Score: $score"
        if (currentRound < roundsNb) {
            listenButton.isEnabled = false
            displayedColor = showColor(3000L)
            listenButton.isEnabled = true
        } else {
            val isSaved = showTestCompletedDialog()
            if (isSaved) {
                postResult(score)
            }
            findNavController().navigate(R.id.action_stroopTest_to_mainFragment)
        }
    }

    private suspend fun postResult(score: Int) {
        val currentDateTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")

        val result = PostStroopTestRequest(
            datetime = currentDateTime.format(formatter),
            mistake_count = roundsNb - score
        )

        httpService.postStroopResult(result)

    }

    // Suspend function to start listening for speech input
    private suspend fun startListening(): String? = suspendCancellableCoroutine { continuation ->
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, context?.packageName)
        }

        val listenButton = view?.findViewById<FloatingActionButton>(R.id.speechListenButton)

        speechRecognizer.startListening(intent)
        speechRecognizer.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {
                Snackbar.make(requireView(), "Speak now", Snackbar.LENGTH_SHORT).show()
                val scaleAnimation = ScaleAnimation(
                    1f, 1.2f,  // Scale from 100% to 120%
                    1f, 1.2f,  // Scale from 100% to 120%
                    Animation.RELATIVE_TO_SELF, 0.5f,  // Pivot point is at the center of the button
                    Animation.RELATIVE_TO_SELF, 0.5f
                )
                scaleAnimation.duration = 500  // Duration in milliseconds
                scaleAnimation.repeatCount = Animation.INFINITE  // Repeat indefinitely
                scaleAnimation.repeatMode = Animation.REVERSE  // Reverse each alternate repetition

                // Start the animation
                listenButton?.startAnimation(scaleAnimation)
            }

            override fun onBeginningOfSpeech() {
            }

            override fun onRmsChanged(rmsdB: Float) {
            }

            override fun onBufferReceived(buffer: ByteArray?) {
            }

            override fun onEndOfSpeech() {
                listenButton?.clearAnimation()
            }

            override fun onError(error: Int) {
            }

            override fun onResults(results: Bundle?) {
                val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                if (!matches.isNullOrEmpty()) {
                    continuation.resume(matches[0])
                } else {
                    continuation.resume(null)
                }
            }

            override fun onPartialResults(partialResults: Bundle?) {
                showSnackBar("Please repeat the color once again", true)
            }

            override fun onEvent(eventType: Int, params: Bundle?) {
            }

        })
    }

    override fun onDestroy() {
        super.onDestroy()
        speechRecognizer.destroy()
    }

    // Suspend function to show a color for a certain duration
    private suspend fun showColor(delayValue: Long): String {
        val colorTv = view?.findViewById<TextView>(R.id.colorTv)
        val colorText = colors.keys.random()
        colorTv?.text = colorText
        var colorValue = colors.keys.random()
        while (colorText == colorValue) {
            colorValue = colors.keys.random()
        }
        colorTv?.setTextColor(ContextCompat.getColor(requireContext(), colors[colorValue]!!))
        delay(delayValue)
        colorTv?.text = ""
        return colorValue
    }

    private suspend fun listenForColor() {
        val result = startListening()
        if (result != null) {
            if (result.equals(displayedColor, ignoreCase = true)) {
                showSnackBar("Correct", false)
                score++
            } else {
                showSnackBar("Incorrect", true)
            }
        } else {
            showSnackBar("Please speak the name of the color once again", true)
        }
    }




}