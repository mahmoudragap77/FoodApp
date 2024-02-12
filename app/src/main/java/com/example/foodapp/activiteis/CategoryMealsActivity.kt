package com.example.foodapp.activiteis

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodapp.ViewModel.CategoryMealsViewModel
import com.example.foodapp.adapters.CategoryMealsAdapter
import com.example.foodapp.databinding.ActivityCategoryMealsBinding
import com.example.foodapp.fragments.HomeFragment

class CategoryMealsActivity : AppCompatActivity() {

    lateinit var binding: ActivityCategoryMealsBinding
    lateinit var categoryMealsViewModel: CategoryMealsViewModel
    lateinit var categoryMealAdapter: CategoryMealsAdapter

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prepareRecView()
        categoryMealsViewModel =ViewModelProviders.of(this)[CategoryMealsViewModel::class.java]

        categoryMealsViewModel.getMealByCategory(intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!)

        categoryMealsViewModel.observeLiveDataCategory().observe(this, Observer {
            mealsList ->
            binding.tvCategoryCount.text= " The Count is ${mealsList.size}"
           categoryMealAdapter.setMealsList(mealsList)
        })
    }

    private fun prepareRecView() {
        categoryMealAdapter =CategoryMealsAdapter()
        binding.rvMeals.apply {
            layoutManager =GridLayoutManager(context ,2 ,GridLayoutManager.VERTICAL,false)
            adapter =categoryMealAdapter


        }

    }
}