package com.example.farmmunity.home.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.farmmunity.home.presentation.add_question.AddQuestionScreen
import com.example.farmmunity.home.presentation.add_question.AddQuestionViewModel
import com.example.farmmunity.home.presentation.question_details.QuestionDetailsScreen
import com.example.farmmunity.home.presentation.questions.QuestionsScreen
import com.example.farmmunity.home.presentation.questions.QuestionsViewModel

@Composable
fun HomeNavGraph(
    navHostController: NavHostController,
    questionViewModel: QuestionsViewModel,
    addQuestionViewModel: AddQuestionViewModel
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.QuestionsScreen.route
    ) {
        composable(
            route = Screen.QuestionsScreen.route
        ) {
            QuestionsScreen(
                questionsViewModel = questionViewModel,
                navHostController = navHostController
            )
        }
        composable(
            route = Screen.AddQuestionScreen.route
        ) {
            AddQuestionScreen(
                addQuestionViewModel = addQuestionViewModel,
                navHostController = navHostController
            )
        }
        composable(
            route = Screen.QuestionDetailsScreen.route + "/{questionId}"
        ) {
            QuestionDetailsScreen(
                navHostController = navHostController
            )
        }
    }
}