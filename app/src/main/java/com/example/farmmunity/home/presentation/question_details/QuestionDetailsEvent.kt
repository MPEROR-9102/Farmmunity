package com.example.farmmunity.home.presentation.question_details

sealed class QuestionDetailsEvent {
    data class OnSubmitAnswerClicked(val answer: String) : QuestionDetailsEvent()
}
