package com.example.quizapp.utils

import com.example.quizapp.R
import com.example.quizapp.model.Question

object Constants{
    fun getQuestions(): MutableList<Question>{
        val questions = mutableListOf<Question>()

        val q1 = Question(
            1,"What country does this flag belong?",
            R.drawable.italy, "Italy", "India", "Iran", "Ireland", 1
        )
        questions.add(q1)

        val q2 = Question(
            1,"What country does this flag belong?",
            R.drawable.brazil, "Italy", "India", "Brazil", "Ireland", 3
        )
        questions.add(q2)

        val q3 = Question(
            1,"What country does this flag belong?",
            R.drawable.france, "Italy", "India", "Iran", "France", 4
        )
        questions.add(q3)

        val q4 = Question(
            1,"What country does this flag belong?",
            R.drawable.india, "Italy", "India", "Iran", "Ireland", 2
        )
        questions.add(q4)

        val q5 = Question(
            1,"What country does this flag belong?",
            R.drawable.argentina, "Argentina", "India", "Iran", "Ireland", 1
        )
        questions.add(q5)

        val q6 = Question(
            1,"What country does this flag belong?",
            R.drawable.finland, "Italy", "India", "Finland", "Ireland", 3
        )
        questions.add(q6)

        val q7 = Question(
            1,"What country does this flag belong?",
            R.drawable.germany, "Italy", "India", "Iran", "Germany", 4
        )
        questions.add(q7)

        val q8 = Question(
            1,"What country does this flag belong?",
            R.drawable.nigeria, "Nigeria", "India", "Iran", "Ireland", 1
        )
        questions.add(q8)

        val q9 = Question(
            1,"What country does this flag belong?",
            R.drawable.romania, "Italy", "India", "Romania", "Ireland", 3
        )
        questions.add(q9)

        val q10 = Question(
            1,"What country does this flag belong?",
            R.drawable.spain, "Italy", "Spain", "Iran", "Ireland", 2
        )
        questions.add(q10)

        return questions
    }
}