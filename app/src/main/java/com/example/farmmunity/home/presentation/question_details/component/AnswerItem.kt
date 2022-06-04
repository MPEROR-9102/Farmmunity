package com.example.farmmunity.home.presentation.question_details.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.farmmunity.home.domain.model.Answer

@Composable
fun AnswerItem(answer: Answer) {
    Text(
        text = answer.title,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    )
}