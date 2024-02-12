package com.example.foodapp.retrofit

import com.example.foodapp.pogo.CategoryList
import com.example.foodapp.pogo.MealList
import com.example.foodapp.pogo.MealsByCateogryList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {
    @GET("random.php")
    fun getRandomMeal(): Call<MealList>

    @GET("lookup.php")
    fun getMealDeatails(@Query("i") id :String):Call<MealList>

    @GET("filter.php")
    fun getPopularItems(@Query("c") categoryName :String):Call<MealsByCateogryList>

    @GET("categories.php")
    fun getCategory():Call<CategoryList>

    @GET("filter.php")
    fun getMealsByCategory(@Query( "c") categoryName: String):Call<MealsByCateogryList>

    @GET("search.php")
    fun searchMeals(@Query( "s") searchQuery: String):Call<MealList>
}