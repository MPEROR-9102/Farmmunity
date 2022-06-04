package com.example.farmmunity.home.domain.use_case

data class UseCases(
    val getQuestions: GetQuestions,
    val addQuestion: AddQuestion,
    val getQuestionById: GetQuestionById,
    val addAnswer: AddAnswer,
    val getAnswers: GetAnswers
)
