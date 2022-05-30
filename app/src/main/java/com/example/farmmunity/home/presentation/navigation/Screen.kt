package com.example.farmmunity.home.presentation.navigation

import com.example.farmmunity.home.core.HomeConstants

sealed class Screen(val route: String) {
    object QuestionsScreen : Screen(HomeConstants.QUESTIONS_SCREEN)
    object AddQuestionScreen : Screen(HomeConstants.ADD_QUESTION_SCREEN)
    object QuestionDetailsScreen : Screen(HomeConstants.QUESTION_DETAILS_SCREEN)
}
