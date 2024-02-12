package com.example.foodapp.pogo


import com.google.gson.annotations.SerializedName

data class MealsByCateogryList(
    @SerializedName("meals")
    val meals: List<MealsByCategory>
)