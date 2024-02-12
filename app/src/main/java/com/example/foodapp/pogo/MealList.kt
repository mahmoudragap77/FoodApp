package com.example.foodapp.pogo


import com.google.gson.annotations.SerializedName

data class MealList(
    @SerializedName("meals")
    val meals: List<Meal> = listOf()
)