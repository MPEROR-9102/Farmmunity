package com.example.farmmunity.home.presentation.add_question

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.example.farmmunity.authentication.component.ProgressIndicator
import com.example.farmmunity.home.domain.model.Response
import com.example.farmmunity.home.presentation.add_question.component.AddQuestionTopAppBar
import com.example.farmmunity.home.presentation.add_question.component.GalleryButton
import com.example.farmmunity.home.presentation.add_question.component.PhotoItem

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddQuestionScreen(
    addQuestionViewModel: AddQuestionViewModel,
    navHostController: NavHostController
) {

    val (title, onTitleChanged) = addQuestionViewModel.questionTitle
    val (description, onDescriptionChange) = addQuestionViewModel.questionDescription

    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(Unit) {
        addQuestionViewModel.eventFlow.collect { event ->
            when (event) {
                AddQuestionViewModel.UIEvent.ShowQuestionsScreen -> {
                    navHostController.navigateUp()
                }
                is AddQuestionViewModel.UIEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(event.message)
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar {
                AddQuestionTopAppBar(
                    onBackArrowClicked = {
                        addQuestionViewModel.onEvent(AddQuestionEvent.OnBackNavClicked)
                    },
                    onSubmitClicked = {
                        addQuestionViewModel.onEvent(AddQuestionEvent.OnSubmitClicked)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 4.dp, end = 8.dp)
                )
            }
        },
        scaffoldState = scaffoldState
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            val (photoItem, questionText, descriptionText, imageSection) = createRefs()

            when (addQuestionViewModel.showProgressBar.value) {
                Response.Loading -> {
                    ProgressIndicator()
                }
                else -> {
                    if (addQuestionViewModel.uploadUrl.value != Uri.EMPTY) {
                        PhotoItem(
                            photoUri = addQuestionViewModel.uploadUrl.value,
                            onDeleteClicked = {
                                addQuestionViewModel.uploadUrl.value = Uri.EMPTY
                            },
                            modifier = Modifier.constrainAs(photoItem) {
                                top.linkTo(parent.top)
                            }
                        )
                    }
                    TextField(
                        value = title,
                        onValueChange = onTitleChanged,
                        modifier = Modifier
                            .fillMaxHeight(.1f)
                            .fillMaxWidth()
                            .constrainAs(questionText) {
                                top.linkTo(photoItem.bottom, margin = 32.dp)
                            },
                        placeholder = {
                            Text(text = "Question...")
                        }
                    )
                    TextField(
                        value = description,
                        onValueChange = onDescriptionChange,
                        modifier = Modifier
                            .fillMaxHeight(.2f)
                            .fillMaxWidth()
                            .constrainAs(descriptionText) {
                                top.linkTo(questionText.bottom, margin = 16.dp)
                            },
                        placeholder = {
                            Text(text = "Description...")
                        }
                    )
                    if (addQuestionViewModel.uploadUrl.value == Uri.EMPTY) {
                        Column(
                            modifier = Modifier
                                .constrainAs(imageSection) {
                                    start.linkTo(parent.start, margin = 8.dp)
                                    bottom.linkTo(parent.bottom, margin = 8.dp)
                                }
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(text = "Add an image to your post.")
                            Spacer(modifier = Modifier.height(24.dp))
                            GalleryButton {
                                try {
                                    addQuestionViewModel.onEvent(AddQuestionEvent.OnGalleryClicked)
                                } catch (exception: Exception) {

                                }
                            }
                        }
                    }
                }
            }
            if (addQuestionViewModel.isQuestionAdded.value is Response.Success) {
                navHostController.navigateUp()
            }
        }

    }
}