package com.example.farmmunity.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import com.example.farmmunity.core.AppConstants
import com.example.farmmunity.ui.theme.FarmmunityTheme

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FarmmunityTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = AppConstants.COLOR_BROWN
                ) {
                    Text(text = "Hello Farmers!")
                }
            }
        }
    }
}