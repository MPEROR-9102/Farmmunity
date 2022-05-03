package com.example.farmmunity.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.farmmunity.authentication.AuthenticationActivity
import com.example.farmmunity.core.AppConstants
import com.example.farmmunity.ui.theme.FarmmunityTheme
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {

    @Inject
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FarmmunityTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = AppConstants.COLOR_BROWN
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Button(
                            onClick = {
                                auth.signOut()
                                startActivity(
                                    Intent(
                                        this@HomeActivity,
                                        AuthenticationActivity::class.java
                                    )
                                )
                                finish()
                            },
                            colors = ButtonDefaults.buttonColors(Color.White)
                        ) {
                            Text(
                                text = "Sign Out",
                                color = AppConstants.COLOR_BROWN
                            )
                        }
                    }
                }
            }
        }
    }
}