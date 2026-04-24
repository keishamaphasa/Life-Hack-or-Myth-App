package com.example.lifehackormythapp

import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity4 : AppCompatActivity() {
    private val tag = "MainActivity4"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.`Review Screen.xml`)

        val statements = intent.getStringArrayExtra("statements") ?: emptyArray()
        val explanations = intent.getStringArrayExtra("explanations") ?: emptyArray()
        val answers = intent.getStringArrayExtra("answers") ?: emptyArray()

        Log.i(tag, "review screen created. Showing ${statements.size} questions")

        val reviewContainer = findViewById<LinearLayout>(R.id.reviewContainer)

        //Create a card style view for each question
        for (i in statements.indices) {
            val statementView = TextView(this).apply {
                text = statements[i]
                textSize = 18f
                setPadding(16, 16, 16, 16)
                setTypeface(null , Typeface.BOLD)
            }
            val explanationView = TextView(this).apply {
                text = explanations[i]
                textSize = 16f
                setPadding(16, 16, 16, 16)
            }
            val answerView = TextView(this).apply {
                text = answers[i]
                textSize = 16f
                setPadding(16, 16, 16, 16)
                setTypeface(null , Typeface.BOLD)
            }
            reviewContainer.addView(statementView)
            reviewContainer.addView(explanationView)
            reviewContainer.addView(answerView)
            
            Log.d(tag, "Added Review Card for question $i: ${statements[i]}")
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
