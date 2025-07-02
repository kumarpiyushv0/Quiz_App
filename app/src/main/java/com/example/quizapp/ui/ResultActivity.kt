package com.example.quizapp.ui

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.quizapp.R

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val total = intent.getIntExtra("totalQuestions", 0)
        val score = intent.getIntExtra("correctAnswers", 0)
        val textView = findViewById<TextView>(R.id.text_view_score)
        textView.text = "You scored $score out of $total!"
    }
}
