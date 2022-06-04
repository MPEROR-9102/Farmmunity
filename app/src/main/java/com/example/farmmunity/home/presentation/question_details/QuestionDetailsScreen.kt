package com.example.farmmunity.home.presentation.question_details

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.farmmunity.authentication.component.ProgressIndicator
import com.example.farmmunity.core.AppConstants
import com.example.farmmunity.home.core.HomeUtils.Companion.getFormattedPosted
import com.example.farmmunity.home.domain.model.Response
import com.example.farmmunity.home.presentation.question_details.component.AnswerEntryDialog
import com.example.farmmunity.home.presentation.question_details.component.AnswerItem
import com.example.farmmunity.home.presentation.question_details.component.PhotoSection
import com.example.farmmunity.home.presentation.question_details.component.ProfileSection

@Composable
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
fun QuestionDetailsScreen(
    navHostController: NavHostController,
    questionDetailsViewModel: QuestionDetailsViewModel = hiltViewModel()
) {

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    questionDetailsViewModel.openDialog.value = true
                }
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }
    ) {

        if (questionDetailsViewModel.openDialog.value) {
            AnswerEntryDialog()
        }

        when (val event = questionDetailsViewModel.questionDetailsState.value) {
            Response.Loading -> {
                ProgressIndicator()
            }
            is Response.Success -> {
                val question = event.data
                LazyColumn {
                    item {
                        PhotoSection(photoUrl = question.photoUrl) {
                            navHostController.navigateUp()
                        }
                        Column(
                            modifier = Modifier
                                .padding(vertical = 8.dp)
                                .padding(horizontal = 16.dp)
                        ) {
                            ProfileSection(
                                profile = question.profile,
                                posted = getFormattedPosted(question.posted),
                                modifier = Modifier
                                    .padding(horizontal = 8.dp)
                                    .padding(top = 8.dp, bottom = 32.dp)
                            )
                            Text(
                                text = question.title,
                                style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Bold)
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(text = question.description)
                        }
                    }
                    when (val answers = questionDetailsViewModel.answersState.value) {
                        is Response.Success -> {
                            items(answers.data) {
                                AnswerItem(answer = it)
                            }
                        }
                        else -> {}
                    }
                }
            }
            is Response.Error -> {
                Log.e(AppConstants.TAG, "QuestionDetailsScreen: ${event.message}")
            }
        }
    }
}