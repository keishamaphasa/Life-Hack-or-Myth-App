package com.example.lifehackormythapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

data class Question(
    val statement: String,
    val isHack: Boolean,
    val explanation: String
)

class MainActivity2 : AppCompatActivity() {
    private val tag = "MainActivity2"

    private var currentIndex = 0
    private var score = 0
    private var answered = false

    private lateinit var txtStatement: TextView
    private lateinit var txtFeedback: TextView
    private lateinit var txtProgress: TextView
    private lateinit var btnHack: Button
    private lateinit var btnMyth: Button
    private lateinit var btnNext: Button

    //full list of flashcard question activity
    private val questions = listOf(
        Question(
            "Drinking water before eating a meal is good for your health",
            isHack = true,
            explanation = "Studies show that drinking water before meals can reduce calories"
        ),
        Question(
            "You should wait 24 hours for reporting a missing person to the police",
            isHack = false,
            explanation = "This is a myth. You can immediately report a missing person to the police."
        ),
        Question(
            "Putting your phone in rice fixes water damage",
            isHack = false,
            explanation = "Rice is ineffective. Silica gel or professional drying work"
        ),
        Question(
            "Chewing gum improves concentration and memory",
            isHack = true,
            explanation = "Studies show that chewing gum can boost alertness and short-term memory"
        ),
        Question(
            "We only use 10% of our brain",
            isHack = false,
            explanation = "This is a myth. Studies show that we use virtually all parts of our brain"
        ),
        Question(
            "Taking short breaks inbetween studying improves long term retention",
            isHack = true,
            explanation = "The pomodoro technique and spaced rentention are well-supported by science"
        ),
        Question(
            "Reading in dim lights ruins your eyesight",
            isHack = false,
            explanation = "Dim lights may cause eyestrain but dont permentally damage your eyes"
        ),
        Question(
            "Freezing a battery can restore its energy",
            isHack = false,
            explanation = "This is a myth and can actually damage modern litium batteries"
        ),
        Question(
            "Keeping a smartphone in the charger overnight can damage the battery",
            isHack = false,
            explanation = "This is a myth and modern smartphones have overnight charge so it doesnt damage the battery"
        ),
        Question(
            "Writing things by hand helps you remember better than typing",
            isHack = true,
            explanation = "Studies shows handwriting engages deeper cognitive processes than typing"
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.`Flashcard Question Screen.xml`)

        Log.i(tag, "question screen created")

        txtStatement = findViewById(R.id.tvStatement)
        txtFeedback = findViewById(R.id.tvFeedback)
        txtProgress = findViewById(R.id.tvProgress)
        btnHack = findViewById(R.id.btnHack)
        btnMyth = findViewById(R.id.btnMyth)
        btnNext = findViewById(R.id.btnNext)

        loadQuestion()

        btnHack.setOnClickListener {
            if (!answered) {
                checkAnswer(userSaysHack = true)
            }
        }
        btnMyth.setOnClickListener {
            if (!answered) {
                checkAnswer(userSaysHack = false)
            }
        }
        btnNext.setOnClickListener {
            currentIndex++
            Log.d(tag, "Moving to question index $currentIndex")
            if (currentIndex < questions.size) {
                loadQuestion()
            } else {
                //all questions answered - go to score screen
                Log.i(tag, "All questions answered. Score : $score")
                navigateToScoreScreen()
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    //Loads the current question onto the screen and resets the UI
    private fun loadQuestion() {
        answered = false
        txtFeedback.visibility = View.INVISIBLE
        btnNext.visibility = View.INVISIBLE

        val question = questions[currentIndex]
        txtStatement.text = question.statement
        txtProgress.text = "Question ${currentIndex + 1} of ${questions.size}"

        Log.i(tag, "Current question index: $currentIndex : ${question.statement}")
    }

    //checks the users answer against the correct answer
    //provides feedback and updates the score
    private fun checkAnswer(userSaysHack: Boolean) {
        val question = questions[currentIndex]
        answered = true
        val isCorrect = userSaysHack == question.isHack

        if (isCorrect) {
            score++
            txtFeedback.text = "Correct answer, that's a time saver!"
            Log.i(tag, "Correct answer. Score: $score")
        } else {
            txtFeedback.text = "Wrong answer, try again."
            Log.i(tag, "Wrong answer. Score: $score")
        }
        txtFeedback.visibility = View.VISIBLE
        btnNext.visibility = View.VISIBLE
    }

    //navigates to the score screen, passing the score and question data
    private fun navigateToScoreScreen() {
        val intent = Intent(this, MainActivity3::class.java)
        intent.putExtra("score", score)
        intent.putExtra("totalQuestions", questions.size)

        //pass statements and explanations as arrays for the review screen
        val statements = questions.map { it.statement }.toTypedArray()
        val explanations = questions.map { it.explanation }.toTypedArray()
        val answers = questions.map { if (it.isHack) "Hack" else "Myth" }.toTypedArray()

        intent.putExtra("statements", statements)
        intent.putExtra("explanations", explanations)
        intent.putExtra("answers", answers)

        startActivity(intent)
        finish() //remove questionActivity from the back stack
    }
}
