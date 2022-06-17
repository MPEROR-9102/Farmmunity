package com.example.farmmunity.home.presentation.questions.component

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun QuestionsTopAppBar(
    photoUrl: Uri,
    onProfileClicked: () -> Unit,
    onSearchClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = photoUrl,
                contentDescription = null,
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable {
                        onProfileClicked()
                    }
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Home",
                style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Bold)
            )
        }
//        Icon(
//            imageVector = Icons.Default.Search,
//            contentDescription = null,
//            modifier = Modifier
//                .size(30.dp)
//                .clip(CircleShape)
//                .clickable {
//                    onSearchClicked()
//                }
//        )
    }
}