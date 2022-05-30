package com.example.farmmunity.home.presentation.questions

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.farmmunity.authentication.component.ProgressIndicator
import com.example.farmmunity.core.AppConstants
import com.example.farmmunity.home.domain.model.Response
import com.example.farmmunity.home.presentation.navigation.Screen
import com.example.farmmunity.home.presentation.questions.component.QuestionItem
import com.example.farmmunity.home.presentation.questions.component.QuestionsTopAppBar
import kotlinx.coroutines.flow.collectLatest

@Composable
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
fun QuestionsScreen(
    questionsViewModel: QuestionsViewModel,
    navHostController: NavHostController
) {

    LaunchedEffect(Unit) {
        questionsViewModel.eventFlow.collectLatest { event ->
            when (event) {
                QuestionsViewModel.UIEvent.OnAddQuestionClicked -> {
                    Log.d(AppConstants.TAG, "QuestionsScreen: C")
                    navHostController.navigate(Screen.AddQuestionScreen.route)
                    Log.d(AppConstants.TAG, "QuestionsScreen: D")
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(elevation = 8.dp) {
                QuestionsTopAppBar(
                    photoUrl = questionsViewModel.getUser()?.photoUrl ?: Uri.EMPTY,
                    onProfileClicked = {
                        questionsViewModel.onEvent(QuestionsEvent.OnProfileClicked)
                    },
                    onSearchClicked = {
                        //TODO
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 4.dp, end = 8.dp)
                )
            }
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = {
                    Text(
                        text = "Ask Community",
                        style = MaterialTheme.typography.button.copy(fontWeight = FontWeight.Bold)
                    )
                },
                onClick = {
                    Log.d(AppConstants.TAG, "QuestionsScreen: A")
                    questionsViewModel.onEvent(QuestionsEvent.OnAddQuestionClicked)
                },
                icon = {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = null)
                }
            )
        },
        floatingActionButtonPosition = FabPosition.Center
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            when (val questions = questionsViewModel.questionsState.value) {
                Response.Loading -> {
                    ProgressIndicator()
                }
                is Response.Success -> {
                    LazyColumn(contentPadding = PaddingValues(top = 16.dp)) {
                        items(questions.data) {
                            QuestionItem(question = it) { questionId ->
                                navHostController.navigate(
                                    Screen.QuestionDetailsScreen.route + "/$questionId"
                                )
                            }
                        }
                    }
                }
                is Response.Error -> {
                    Log.d(AppConstants.TAG, "QuestionsScreen: ${questions.message}")
                }
            }
        }
    }
}