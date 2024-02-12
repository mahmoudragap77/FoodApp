package com.example.foodapp.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodapp.pogo.MealsByCategory
import com.example.foodapp.pogo.MealsByCateogryList
import com.example.foodapp.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryMealsViewModel :ViewModel() {
    val mealLiveData =MutableLiveData<List<MealsByCategory>>()
    fun getMealByCategory(categoryName :String){
        RetrofitInstance.api.getMealsByCategory(categoryName).enqueue(object :Callback<MealsByCateogryList>{
            override fun onResponse(
                call: Call<MealsByCateogryList>,
                response: Response<MealsByCateogryList>,
            ) {
                    response.body()?.let {
                        mealsList ->
                        mealLiveData.postValue(mealsList.meals)
                    }
            }

            override fun onFailure(call: Call<MealsByCateogryList>, t: Throwable) {
                Log.e("Category Meals ViewModel",t.message.toString())
            }

        })
    }


    fun observeLiveDataCategory():LiveData<List<MealsByCategory>>{
        return mealLiveData
    }
}