package com.example.thebhojcount.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.thebhojcount.AuthViewModel
import com.example.thebhojcount.R
import com.example.thebhojcount.data.CalorieViewModel
import com.example.thebhojcount.data.Dish
import com.example.thebhojcount.data.dish
import com.example.thebhojcount.ui.theme.TheBhojCountTheme

enum class MealCategory {
    BREAKFAST,
    LUNCH,
    DINNER,
    SNACK
}


@Composable
fun MealDialog(
    category: MealCategory,
    availableDishes: List<Dish>,
    onDismiss: () -> Unit,
    onMealAdded: (Dish, Int) -> Unit
) {
    var selectedDishName by remember { mutableStateOf("") }
    var servingSize by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    val filteredDishes = availableDishes.filter {
        it.name.contains(selectedDishName, ignoreCase = true)
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Add Meal to ${category.name}")
        },
        text = {
            Column {
                Box {
                    OutlinedTextField(
                        value = selectedDishName,
                        onValueChange = {
                            selectedDishName = it
                            expanded = true
                        },
                        label = { Text("Enter dish name") },
                        modifier = Modifier.padding(vertical = 8.dp)
                    )

                    DropdownMenu(
                        expanded = expanded && filteredDishes.isNotEmpty(),
                        onDismissRequest = { expanded = false }
                    ) {
                        filteredDishes.forEach { dish ->
                            DropdownMenuItem(
                                text = { Text(dish.name) },
                                onClick = {
                                    selectedDishName = dish.name
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = servingSize,
                    onValueChange = { servingSize = it },
                    label = { Text("Enter serving size (in grams)") },
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val selectedDish = availableDishes.find { it.name.equals(selectedDishName, ignoreCase = true) }
                    val servingSizeInt = servingSize.toIntOrNull() ?: 0
                    selectedDish?.let { onMealAdded(it, servingSizeInt) }
                },
                enabled = selectedDishName.isNotEmpty() && servingSize.isNotEmpty()
            ) {
                Text("Add Meal")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun AddMealScreen(
    availableDishes: List<Dish>,
    viewModel: CalorieViewModel,
    onMealAdded: (MealCategory, Dish, Int) -> Unit,
    authViewModel: AuthViewModel
) {
    val maxCalories by viewModel.calorieIntake.observeAsState(0)
    var totalCalories by remember { mutableIntStateOf(0) }
    var selectedCategory by remember { mutableStateOf<MealCategory?>(null) }
    var showDialog by remember { mutableStateOf(false) }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(60.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "Eaten: $totalCalories calories",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                        Text(
                            text = "Remaining: ${maxCalories - totalCalories} calories",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp

                        )
                    }
                    Image(
                        painter = painterResource(R.drawable.meal_removebg_preview),
                        contentDescription = null,
                        modifier = Modifier
                            .size(128.dp)
                            .padding(start = 16.dp)
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                listOf(
                    MealCategory.BREAKFAST to "Breakfast",
                    MealCategory.LUNCH to "Lunch",
                    MealCategory.DINNER to "Dinner",
                    MealCategory.SNACK to "Snack"
                ).forEach { (category, label) ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .clickable {
                                selectedCategory = category
                                showDialog = true
                            },
                        elevation = CardDefaults.cardElevation(8.dp)
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = label,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp)
                        }
                    }
                }
            }

            if (showDialog && selectedCategory != null) {
                MealDialog(
                    category = selectedCategory!!,
                    availableDishes = availableDishes,
                    onDismiss = { showDialog = false },
                    onMealAdded = { dish, servingSize ->
                        onMealAdded(selectedCategory!!, dish, servingSize)
                        totalCalories += (dish.calories * servingSize) / 100
                        showDialog = false
                    }
                )
            }

        }
        TextButton(
            onClick = {
                authViewModel.signout()
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            Text(
                text = "Sign out",
                fontWeight = FontWeight.Bold,
                color = Color(0xFF596420)
            )
        }
    }

    }


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun InputMealPreview() {
    val viewModel = CalorieViewModel()
    TheBhojCountTheme {
        AddMealScreen(
            availableDishes = dish,
            viewModel = viewModel,
            onMealAdded = { _, _, _ -> },
            authViewModel = AuthViewModel()
        )
    }
}