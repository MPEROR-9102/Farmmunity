package com.example.farmmunity.authentication.intro

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.farmmunity.R
import com.example.farmmunity.authentication.core.AuthenticationConstants
import com.example.farmmunity.authentication.intro.component.GoogleButton
import com.example.farmmunity.authentication.intro.component.PhoneButton
import com.example.farmmunity.core.AppConstants

@Composable
fun IntroScreen(
    navHostController: NavHostController? = null
) {

    val lobsterFontFamily = FontFamily(
        Font(R.font.lobster_regular, FontWeight.Normal)
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.farm),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(top = 160.dp, bottom = 32.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = AuthenticationConstants.APP_NAME,
                style = MaterialTheme.typography.h2.copy(
                    fontWeight = FontWeight.Bold,
                    color = AppConstants.COLOR_BROWN.copy(alpha = .75f)
                ),
                fontFamily = lobsterFontFamily
            )
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxHeight(.5f)
                    .fillMaxWidth()
            ) {
                GoogleButton(
                    modifier = Modifier.fillMaxWidth(.8f)
                ) {
                    //
                }
                Spacer(modifier = Modifier.height(32.dp))
                PhoneButton(
                    modifier = Modifier.fillMaxWidth(.8f)
                ) {
                    //
                }
            }
        }
    }
}

//context.startActivity(Intent(context, HomeActivity::class.java))
//(context as Activity).finish()

@Preview
@Composable
fun IntroScreenPreview() {
    Surface {
        IntroScreen()
    }
}