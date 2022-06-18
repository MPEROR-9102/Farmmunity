package com.example.farmmunity.home.presentation.question_details.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.farmmunity.home.core.HomeUtils
import com.example.farmmunity.home.domain.model.Answer

@Composable
fun AnswerItem(
    answer: Answer,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))
            .padding(bottom = 16.dp),
        elevation = 4.dp
    ) {
        Row(modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp)) {
            AsyncImage(
                model = answer.profile.photo,
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp)
                    .clip(CircleShape)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp)
            ) {
                Text(
                    text = "${answer.profile.name} • ${HomeUtils.getFormattedPosted(answer.posted)}",
                    fontSize = 12.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = answer.title,
                    textAlign = TextAlign.Justify
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}