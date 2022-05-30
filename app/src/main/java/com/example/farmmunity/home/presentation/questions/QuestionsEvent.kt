package com.example.farmmunity.home.presentation.questions

sealed class QuestionsEvent {
    object OnAddQuestionClicked : QuestionsEvent()
    object OnProfileClicked : QuestionsEvent()
}