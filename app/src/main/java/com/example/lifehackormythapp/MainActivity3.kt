package com.example.lifehackormythapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity3 : AppCompatActivity() {
    //Reference : https://developer.android.com/training/data-storage/shared-preferences

    private val tag = "MainActivity3"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main3)


        val score = intent.getIntExtra("score", 0)
        val totalQuestions = intent.getIntExtra("totalQuestions", 0)
        val statements = intent.getStringArrayExtra("statements") ?: emptyArray()
        val explanations = intent.getStringArrayExtra("explanations") ?: emptyArray()
        val answers = intent.getStringArrayExtra("answers") ?: emptyArray()

        Log.i(tag, "score screen created")
        val txtScore = findViewById<TextView>(R.id.tvScore)
        val txtFeedback = findViewById<TextView>(R.id.tvScoreFeedback)
        val btnReview = findViewById<Button>(R.id.btnReview)
        val btnReset = findViewById<Button>(R.id.btnRestart)

        //Display score
        txtScore.text = "You scored $score out of $totalQuestions"

        //Personalised feedback based on score
        val feedback = when {
            score == totalQuestions -> "Wow! You answered all the questions correctly."
            score >= totalQuestions * 0.7 -> "Great job! You can do better."
            score >= totalQuestions * 0.4 -> "Not bad, but could be better."
            else -> "Keep practicing to improve your score."
        }
        txtFeedback.text = feedback
        Log.i(tag, "Feedback shown: $feedback")

        //Navigate to review screen
        btnReview.setOnClickListener {
            val intent = Intent(this, MainActivity4::class.java)
            intent.putExtra("statements", statements)
            intent.putExtra("explanations", explanations)
            intent.putExtra("answers", answers)
            startActivity(intent)
        }

        //Reset score and navigate to main screen
        btnReset.setOnClickListener {
            Log.i(tag, "Reset button clicked. Navigating to main screen.")
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
