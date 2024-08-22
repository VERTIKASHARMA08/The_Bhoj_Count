package com.example.thebhojcount.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.thebhojcount.data.CalorieViewModel
import com.example.thebhojcount.ui.theme.TheBhojCountTheme

fun calculateCalorieIntake(
    age: Int,
    weight: Double,
    height: Double,
    gender: String,
    dietType: String,
    activityType: String,
    onNextClick: () -> Unit
): Int {
    // Simplified calculation for demonstration
    val bmr = when (gender) {
        "Male" -> (13.75 * weight) + (5.003 * height) - (6.75 * age) + 66.5
        "Female" -> (9.563 * weight) + (1.850 * height) - (4.676 * age) + 655.1
        else -> 0.0
    }
    val dailyEnergy = when (activityType) {
        "Sedentary (Little or No Exercise)" -> 1.2
        "Lightly Active (Exercise 1-3 times/week)" -> 1.375
        "Moderately Active (Exercise 4-5 times/week)" -> 1.55
        "Active (Daily Exercise)" -> 1.725
        "Very Active (Intense Exercise 4-5 times/week)" -> 1.8
        else -> 1.2
    }

    val calorieMultiplier = when (dietType) {
        "Normal Healthy Diet" -> 1.0
        "Weight Loss Diet" -> 0.81
        "Weight Gain Diet" -> 1.2
        else -> 1.2
    }


    return (bmr * calorieMultiplier * dailyEnergy).toInt()
}
@Composable
fun CalorieIntakePage(
    name: String,
    age: String,
    weight: String,
    height: String,
    gender: String,
    dietType: String,
    activityType: String,
    onNextClick: () -> Unit,
    viewModel: CalorieViewModel
) {
    LaunchedEffect(age, weight, height, gender, dietType) {
        viewModel.calculateCalorieIntake(
            age = age.toIntOrNull() ?: 0,
            weight = weight.toDoubleOrNull() ?: 0.0,
            height = height.toDoubleOrNull() ?: 0.0,
            gender = gender,
            dietType = dietType,
            activityType = activityType
        )
    }

    val calorieIntake by viewModel.calorieIntake.observeAsState(0)

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Hi,$name,",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF596420)

                )
            
            Spacer(modifier = Modifier.height(60.dp))
            Card(
                modifier = Modifier.padding(8.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 10.dp
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "According to your preferences your daily calorie intake should be $calorieIntake calories.",
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        ) {
            Button(
                onClick = onNextClick,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp),
                shape = CircleShape
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowForward, // Replace with your desired icon
                    contentDescription = "Next",
                    tint = Color.White // Set your desired icon color here
                )
            }
        }
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CalorieIntakePagePreview() {

    TheBhojCountTheme {
        val viewModel = CalorieViewModel()
        Surface(color = MaterialTheme.colorScheme.background){
            CalorieIntakePage(
                name = " ",
                age = " ",
                weight = " ",
                height = " ",
                gender = " ",
                dietType = " ",
                activityType = " ",
                onNextClick = { },
                viewModel = viewModel
            )
        }
    }
}