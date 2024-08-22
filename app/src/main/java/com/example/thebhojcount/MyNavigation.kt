package com.example.thebhojcount

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.thebhojcount.data.CalorieViewModel
import com.example.thebhojcount.data.dish
import com.example.thebhojcount.pages.AddMealScreen
import com.example.thebhojcount.pages.CalorieIntakePage
import com.example.thebhojcount.pages.DetailScreen0
import com.example.thebhojcount.pages.DetailScreen1
import com.example.thebhojcount.pages.DetailScreen2
import com.example.thebhojcount.pages.DetailScreen3
import com.example.thebhojcount.pages.DetailScreen4
import com.example.thebhojcount.pages.DetailScreen5
import com.example.thebhojcount.pages.DetailScreen6
import com.example.thebhojcount.pages.LoginPage
import com.example.thebhojcount.pages.MainPage
import com.example.thebhojcount.pages.SignUpPage

@Composable
fun MyAppNavigation(modifier: Modifier = Modifier, authViewModel: AuthViewModel) {
    val navController = rememberNavController()
    var name by remember { mutableStateOf(" ") }
    var age by remember { mutableStateOf(" ") }
    var weight by remember { mutableStateOf("") }
    var height by remember { mutableStateOf(" ") }
    var gender by remember { mutableStateOf(" ") }
    var dietType by remember { mutableStateOf(" ") }
    var activityType by remember { mutableStateOf(" ") }

    val viewModel: CalorieViewModel = viewModel()

    NavHost(navController = navController, startDestination = "mainpage", builder = {
        composable("mainpage") {
            MainPage(
                onSignUpClick = {navController.navigate("signup")},
                onLoginClick = {navController.navigate("login")}
            )
            }
        composable("login") {
            LoginPage(modifier, navController, authViewModel)
        }
        composable("signup") {
            SignUpPage(modifier, navController, authViewModel)
        }
        composable("detailscreen0") {
            DetailScreen0(
                name = name,
                onNameChange = { name = it },
                onNextClick = {
                    navController.navigate("detailscreen1")
                })
        }

        composable("detailscreen1") {
            DetailScreen1(
                dietType = dietType,
                onDietChange = { dietType = it },
                onNextClick = {
                    navController.navigate("detailscreen2")
                }
            )
        }
        composable("detailscreen2") {
            DetailScreen2(
                gender = gender,
                onSelectedGender = { gender = it },
                onNextClick = {
                    navController.navigate("detailscreen3")
                })
        }
        composable("detailscreen3") {
            DetailScreen3(
                age = age,
                onAgeChange = { age = it },
                onNextClick = {
                    navController.navigate("detailscreen4")
                })
        }
        composable("detailscreen4") {
            DetailScreen4(
                height = height,
                onHeightChange = { height = it },
                onNextClick = {
                    navController.navigate("detailscreen5")
                }
            )
        }
        composable("detailscreen5") {
            DetailScreen5(
                weight = weight,
                onWeightChange = { weight = it },
                onNextClick = {
                    navController.navigate("detailscreen6")
                }
            )
        }
        composable("detailscreen6") {
            DetailScreen6(
                activityType = activityType,
                onActivityChange = { activityType = it },
                onNextClick = {
                    navController.navigate("outputscreen")
                }
            )
        }
        composable("outputscreen") {
            CalorieIntakePage(
                name = name,
                age = age,
                weight = weight,
                height = height,
                gender = gender,
                dietType = dietType,
                activityType = activityType,
                onNextClick = { navController.navigate("inputmeal")},
                viewModel = viewModel
            )
        }
        composable("inputmeal") {
            AddMealScreen(
                availableDishes = dish,
                viewModel = viewModel,
                onMealAdded = { _, _, _ -> },
                authViewModel = AuthViewModel())
        }
    }
    )
}