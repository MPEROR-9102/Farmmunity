package com.example.farmmunity.home.presentation.question_details.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.farmmunity.home.presentation.question_details.QuestionDetailsEvent
import com.example.farmmunity.home.presentation.question_details.QuestionDetailsViewModel

@Composable
fun AnswerEntryDialog(
    questionDetailsViewModel: QuestionDetailsViewModel = hiltViewModel()
) {
    val (answer, onAnswerChange) = remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = {
            questionDetailsViewModel.openDialog.value = false
        },
        title = { Text(text = "Answer") },
        text = { TextField(value = answer, onValueChange = onAnswerChange) },
        buttons = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                TextButton(onClick = {
                    onAnswerChange("")
                }) {
                    Text(text = "Clear")
                }
                TextButton(onClick = {
                    questionDetailsViewModel.onEvent(
                        QuestionDetailsEvent.OnSubmitAnswerClicked(answer)
                    )
                }) {
                    Text(text = "Submit")
                }
            }
        }
    )
}