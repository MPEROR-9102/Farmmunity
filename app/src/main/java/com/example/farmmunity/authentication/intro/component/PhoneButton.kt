package com.example.farmmunity.authentication.intro.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.farmmunity.authentication.core.AuthenticationConstants
import com.example.farmmunity.core.AppConstants

@Composable
fun PhoneButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(AppConstants.COLOR_BROWN),
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Default.Phone,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(30.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = AuthenticationConstants.PHONE_TEXT,
            style = MaterialTheme.typography.button.copy(
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@Preview
@Composable
fun PhoneButtonPreview() {
    PhoneButton {}
}
