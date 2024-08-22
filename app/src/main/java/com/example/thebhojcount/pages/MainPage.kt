package com.example.thebhojcount.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.thebhojcount.R
import com.example.thebhojcount.ui.theme.TheBhojCountTheme

@Composable
fun MainPage(
    onSignUpClick: () -> Unit,
    onLoginClick: () -> Unit

) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(100.dp))
            Image(
                modifier = Modifier
                    .size(300.dp)

                    .clip(RoundedCornerShape(16.dp)),
                painter = painterResource(id = R.drawable.tbc),
                contentDescription = null
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "The Bhoj Count",
                style = MaterialTheme.typography.titleLarge,
            )
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = onSignUpClick,
                modifier = Modifier
                    .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)


            ) {
                Text(
                    text = "Sign Up",
                    style = MaterialTheme.typography.titleLarge,)
            }

            Button(
                onClick = onLoginClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),

            ) {
                Text(text = "Login",
                    style = MaterialTheme.typography.titleLarge,)
            }
            }
        }

    }


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainPagePreview(){
    TheBhojCountTheme {
        MainPage(
            onSignUpClick = {},
            onLoginClick = {}
        )
    }
}