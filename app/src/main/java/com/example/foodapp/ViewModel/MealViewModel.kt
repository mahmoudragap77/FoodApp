package com.example.foodapp.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapp.db.MealDataBase
import com.example.foodapp.pogo.Meal
import com.example.foodapp.pogo.MealList
import com.example.foodapp.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealViewModel(
   val mealDataBase : MealDataBase
) :ViewModel() {
    private var mealDetailLiveData =MutableLiveData<Meal>()

    fun getMealDetail(id :String){
        RetrofitInstance.api.getMealDeatails(id).enqueue(object :Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body() != null){
                    mealDetailLiveData.value =response.body()!!.meals[0]
                }else
                {
                    return
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
               Log.d("MealActivity",t.message.toString())
            }

        })
    }

    fun observeMealDetailLiveData() : LiveData<Meal> {
        return mealDetailLiveData
    }

    fun  insertMeal(meal :Meal){
        viewModelScope.launch {
            mealDataBase.daoMealDao().upsert(meal)
        }
    }
    fun  delete(meal :Meal){
        viewModelScope.launch {
            mealDataBase.daoMealDao().deleteMeal(meal)
        }
    }
}