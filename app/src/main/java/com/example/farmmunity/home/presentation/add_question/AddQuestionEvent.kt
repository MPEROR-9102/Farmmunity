package com.example.farmmunity.home.presentation.add_question

sealed class AddQuestionEvent {
    object OnBackNavClicked : AddQuestionEvent()
    object OnGalleryClicked : AddQuestionEvent()
    object OnSubmitClicked : AddQuestionEvent()
}