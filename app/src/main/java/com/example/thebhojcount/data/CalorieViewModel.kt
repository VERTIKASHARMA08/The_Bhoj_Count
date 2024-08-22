package com.example.thebhojcount.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CalorieViewModel : ViewModel() {
    private val _calorieIntake = MutableLiveData<Int>()
    val calorieIntake: LiveData<Int> get() = _calorieIntake

    fun calculateCalorieIntake(
        age: Int,
        weight: Double,
        height: Double,
        gender: String,
        dietType: String,
        activityType: String
    ) {
        val bmr = when (gender) {
            "Male" -> (13.75 * weight) + (5.003 * height) - (6.755 * age) + 66.47
            "Female" -> (9.563 * weight) + (1.850 * height) - (4.676 * age) + 655.1
            else -> 0.0
        }
        val dailyEnergy = when (activityType) {
            "Sedentary (Little or No Exercise)" -> 1.2
            "Lightly Active (Exercise 1-3 times/week)" -> 1.375
            "Moderately Active (Exercise 4-5 times/week)" -> 1.55
            "Active (Daily Exercise)" -> 1.725
            "Very Active (Intense Exercise 4-5 times/week)" -> 1.9
            else -> 1.2
        }

        val calorieMultiplier = when (dietType) {
            "Normal Healthy Diet" -> 1.0
            "Weight Loss Diet" -> 0.81
            "Weight Gain Diet" -> 1.2
            else -> 1.2
        }

        _calorieIntake.value = (bmr * calorieMultiplier * dailyEnergy).toInt()
    }
}