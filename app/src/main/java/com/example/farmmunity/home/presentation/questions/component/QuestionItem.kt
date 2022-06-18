package com.example.farmmunity.home.presentation.questions.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.farmmunity.R
import com.example.farmmunity.home.core.HomeUtils
import com.example.farmmunity.home.domain.model.Question

@Composable
fun QuestionItem(
    question: Question,
    modifier: Modifier = Modifier,
    onQuestionClicked: (questionId: String) -> Unit
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 8.dp,
        modifier = modifier
            .clickable {
                onQuestionClicked(question.uid)
            }
            .padding(horizontal = 8.dp)
            .padding(bottom = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {
            if (question.photoUrl.isNotBlank()) {
                AsyncImage(
                    model = question.photoUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(250.dp)
                        .background(Color.LightGray),
                    filterQuality = FilterQuality.Low,
                    placeholder = painterResource(id = R.drawable.image_placeholder)
                )
            }
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = HomeUtils.getFormattedPosted(question.posted),
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = question.profile.photo,
                        contentDescription = null,
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(25.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = question.profile.name,
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = question.title,
                        color = Color.Black,
                        maxLines = 1,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Justify
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = question.description,
                        color = Color.Black,
                        fontSize = 14.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Justify
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = HomeUtils.getFormattedAnswerCount(question.answerCount),
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(bottom = 8.dp)
                    )
                }
            }
        }
    }
}
