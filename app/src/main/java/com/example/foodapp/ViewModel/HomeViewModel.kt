package com.example.foodapp.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapp.db.MealDataBase
import com.example.foodapp.pogo.Category
import com.example.foodapp.pogo.CategoryList
import com.example.foodapp.pogo.Meal
import com.example.foodapp.pogo.MealList
import com.example.foodapp.pogo.MealsByCategory
import com.example.foodapp.pogo.MealsByCateogryList
import com.example.foodapp.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(
    private val mealDataBase: MealDataBase
) : ViewModel() {
    private var randomMealLiveData = MutableLiveData<Meal>()
    private var popularItemiveData = MutableLiveData<List<MealsByCategory>>()
    private var categoriesLiveData = MutableLiveData<List<Category>>()
    private var favouriteMealsLiveData =mealDataBase.daoMealDao().getAllMeals()
    private var bottomSheetMealLiveData =MutableLiveData<Meal>()
    private var searchMealsLiveData =MutableLiveData<List<Meal>>()

   private var saveStatRandomMeal : Meal ?=null
    fun getRandomMeal() {
        saveStatRandomMeal?.let {
            randomMeal ->
            randomMealLiveData.postValue(randomMeal)
            return
        }
        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body() != null) {
                    val randoMeal: Meal = response.body()!!.meals[0]
                    randomMealLiveData.value = randoMeal
                    saveStatRandomMeal=randoMeal
                } else {
                    return
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("HomeFragment ", t.message.toString())
            }

        })
    }

    fun getpopularItems() {
        RetrofitInstance.api.getPopularItems("Seafood")
            .enqueue(object : Callback<MealsByCateogryList> {
                override fun onResponse(
                    call: Call<MealsByCateogryList>,
                    response: Response<MealsByCateogryList>,
                ) {
                    if (response.body() != null) {
                        popularItemiveData.value = response.body()!!.meals
                    } else {
                        return
                    }

                }

                override fun onFailure(call: Call<MealsByCateogryList>, t: Throwable) {
                    Log.d("HomeFragment ", t.message.toString())
                }

            })
    }

    fun getCategories() {
        RetrofitInstance.api.getCategory().enqueue(object : Callback<CategoryList> {
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                response.body()?.let {
                    categoryList ->
                    categoriesLiveData.postValue(categoryList.categories)
                }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Log.e("HomeViewModel ", t.message.toString())
            }

        })
    }

    fun getMealById(id :String){
        RetrofitInstance.api.getMealDeatails(id).enqueue(object  : Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                val meal =response.body()?.meals?.first()
                meal?.let {
                    meal ->
                    bottomSheetMealLiveData.postValue(meal)
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.e("HomeViewModel ", t.message.toString())
            }

        })
    }

    fun  delete(meal :Meal) {
        viewModelScope.launch {
            mealDataBase.daoMealDao().deleteMeal(meal)
        }
    }

    fun  insertMeal(meal :Meal){
        viewModelScope.launch {
            mealDataBase.daoMealDao().upsert(meal)
        }
    }

    fun searchMeal(searchQueary :String) =RetrofitInstance.api.searchMeals(searchQueary).enqueue(object :Callback<MealList>{
        override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
            val mealsList =response.body()?.meals
            mealsList?.let {meal ->
                searchMealsLiveData.postValue(meal)
            }
        }

        override fun onFailure(call: Call<MealList>, t: Throwable) {
            Log.e("HomeViewModel ", t.message.toString())
        }

    })

    fun observeSearchMealLiveData(): LiveData<List<Meal>> {
        return searchMealsLiveData
    }
    fun observeRandomMealLiveData(): LiveData<Meal> {
        return randomMealLiveData
    }

    fun observePopularItemLiveData(): LiveData<List<MealsByCategory>> {
        return popularItemiveData
    }
    fun observeCategoriesLiveData(): LiveData<List<Category>> {
        return categoriesLiveData
    }

    fun observeFavouriteMealLiveData(): LiveData<List<Meal>>{
       return  favouriteMealsLiveData
    }

    fun observeBottomSheetMealLiveData(): LiveData<Meal> =  bottomSheetMealLiveData



}