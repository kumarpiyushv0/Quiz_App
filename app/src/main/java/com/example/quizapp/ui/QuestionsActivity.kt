package com.example.quizapp.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.toColorInt
import com.example.quizapp.R
import com.example.quizapp.model.Question
import com.example.quizapp.utils.Constants

class QuestionsActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var progressBar: ProgressBar
    private lateinit var textViewProgress: TextView
    private lateinit var textViewQuestions: TextView
    private lateinit var flagImage: ImageView
    private lateinit var checkButton: Button
    private lateinit var timerTextView: TextView

    private lateinit var textViewOption1: TextView
    private lateinit var textViewOption2: TextView
    private lateinit var textViewOption3: TextView
    private lateinit var textViewOption4: TextView

    private lateinit var options: List<TextView>
    private lateinit var questionsList: MutableList<Question>

    private var currentPosition = 1
    private var selectedOptionPosition = 0
    private var correctAnswers = 0
    private var isAnswerChecked = false
    private var timer: CountDownTimer? = null
    private val questionTimeMillis = 15000L // 15s per question

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_questions)

        progressBar = findViewById(R.id.progressBar)
        textViewProgress = findViewById(R.id.text_view_progress)
        textViewQuestions = findViewById(R.id.question_text_view)
        flagImage = findViewById(R.id.Image_flag)
        checkButton = findViewById(R.id.button_check)
        timerTextView = findViewById(R.id.text_view_timer)

        textViewOption1 = findViewById(R.id.text_view_option_1)
        textViewOption2 = findViewById(R.id.text_view_option_2)
        textViewOption3 = findViewById(R.id.text_view_option_3)
        textViewOption4 = findViewById(R.id.text_view_option_4)

        options = listOf(textViewOption1, textViewOption2, textViewOption3, textViewOption4)

        options.forEach { it.setOnClickListener(this) }
        checkButton.setOnClickListener(this)

        questionsList = Constants.getQuestions()
        setQuestion()
    }

    @SuppressLint("SetTextI18n")
    private fun setQuestion() {
        resetOptions()
        isAnswerChecked = false
        startTimer()

        val anim = AnimationUtils.loadAnimation(this, R.anim.fade_in)

        val question = questionsList[currentPosition - 1]
        flagImage.setImageResource(question.image)
        textViewQuestions.text = question.question
        textViewOption1.text = question.optionOne
        textViewOption2.text = question.optionTwo
        textViewOption3.text = question.optionThree
        textViewOption4.text = question.optionFour

        progressBar.progress = currentPosition
        textViewProgress.text = "$currentPosition/${progressBar.max}"

        checkButton.text = if (currentPosition == questionsList.size) "FINISH" else "CHECK"

        textViewQuestions.startAnimation(anim)
        flagImage.startAnimation(anim)
    }

    private fun resetOptions() {
        selectedOptionPosition = 0
        for (option in options) {
            option.setTextColor("#7A8089".toColorInt())
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(this, R.drawable.default_option_border_bg)
        }
    }

    private fun selectedOption(textView: TextView, selectedOptionNumber: Int) {
        if (isAnswerChecked) return
        resetOptions()
        selectedOptionPosition = selectedOptionNumber
        textView.setTextColor("#363A43".toColorInt())
        textView.setTypeface(textView.typeface, Typeface.BOLD)
        textView.background = ContextCompat.getDrawable(this, R.drawable.selected_option_border_bg)
    }

    private fun highlightAnswer(answer: Int, drawableId: Int) {
        val option = options[answer - 1]
        option.background = ContextCompat.getDrawable(this, drawableId)
    }

    private fun startTimer() {
        timer?.cancel()
        timerTextView.text = "${questionTimeMillis / 1000}s"
        timer = object : CountDownTimer(questionTimeMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timerTextView.text = "${millisUntilFinished / 1000}s"
            }

            override fun onFinish() {
                timerTextView.text = "0s"
                val question = questionsList[currentPosition - 1]
                highlightAnswer(question.correctAnswer, R.drawable.correct_option_border_bg)
                isAnswerChecked = true
                checkButton.text = if (currentPosition == questionsList.size) "FINISH" else "NEXT"
            }
        }.start()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.text_view_option_1 -> selectedOption(textViewOption1, 1)
            R.id.text_view_option_2 -> selectedOption(textViewOption2, 2)
            R.id.text_view_option_3 -> selectedOption(textViewOption3, 3)
            R.id.text_view_option_4 -> selectedOption(textViewOption4, 4)
            R.id.button_check -> {
                timer?.cancel()
                val question = questionsList[currentPosition - 1]

                if (!isAnswerChecked) {
                    if (selectedOptionPosition == 0) {
                        Toast.makeText(this, "Please select an option", Toast.LENGTH_SHORT).show()
                        return
                    }

                    isAnswerChecked = true
                    if (selectedOptionPosition != question.correctAnswer) {
                        highlightAnswer(selectedOptionPosition, R.drawable.wrong_option_border_bg)
                    } else {
                        correctAnswers++
                    }
                    highlightAnswer(question.correctAnswer, R.drawable.correct_option_border_bg)
                    checkButton.text = if (currentPosition == questionsList.size) "FINISH" else "NEXT"
                } else {
                    currentPosition++
                    if (currentPosition <= questionsList.size) {
                        setQuestion()
                    } else {
                        val intent = Intent(this, ResultActivity::class.java)
                        intent.putExtra("totalQuestions", questionsList.size)
                        intent.putExtra("correctAnswers", correctAnswers)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        timer?.cancel()
        super.onDestroy()
    }
}
