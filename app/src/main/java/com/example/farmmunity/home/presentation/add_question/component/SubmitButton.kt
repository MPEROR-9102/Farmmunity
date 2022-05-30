package com.example.farmmunity.home.presentation.add_question.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun GalleryButton(
    modifier: Modifier = Modifier,
    onSubmitClicked: () -> Unit
) {
    Box(
        modifier = modifier
            .size(100.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(Color.Gray)
            .clickable {
                onSubmitClicked()
            }
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Gallery")
    }
}