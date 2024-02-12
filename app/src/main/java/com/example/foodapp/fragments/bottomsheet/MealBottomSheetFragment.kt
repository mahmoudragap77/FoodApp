package com.example.foodapp.fragments.bottomsheet

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.foodapp.ViewModel.HomeViewModel
import com.example.foodapp.activiteis.MainActivity
import com.example.foodapp.activiteis.MealActivity
import com.example.foodapp.databinding.FragmentMealBottomSheetBinding
import com.example.foodapp.fragments.HomeFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


private const val MEAL_ID = "MEAL_ID"


class MealBottomSheetFragment : BottomSheetDialogFragment() {

    private var mealId: String? = null
    private lateinit var binding: FragmentMealBottomSheetBinding
    private lateinit var viewModel :HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mealId = it.getString(MEAL_ID)
        }

        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentMealBottomSheetBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mealId?.let { viewModel.getMealById(it) }

        obsserveBottomSheetMeal()
        onBottomSheetDialogClick()
    }

    private fun onBottomSheetDialogClick() {
        binding.bottomSheet.setOnClickListener{
            if (mealName !=null &&mealThumb != null){
                val intent = Intent(activity, MealActivity::class.java)
                intent.apply {
                    putExtra(HomeFragment.MEAL_ID,mealId)
                    putExtra(HomeFragment.MEAL_NAME,mealName)
                    putExtra(HomeFragment.MEAL_THUMB,mealThumb)
                }
                startActivity(intent)
            }
        }
    }
private var mealName :String? =null
private var mealThumb :String? =null
    private fun obsserveBottomSheetMeal() {
        viewModel.observeBottomSheetMealLiveData().observe(viewLifecycleOwner , Observer {
            meal ->
            Glide.with(this)
                .load(meal.strMealThumb)
                .into(binding.imgBottomSheet)
                binding.tvBottomSheetArea.text =meal.strArea
                binding.tvBottomSheetCategory.text =meal.strCategory
                binding.bottomSheetMealName.text =meal.strMeal

            mealName=meal.strMeal
            mealThumb =meal.strMealThumb
        })
    }

    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String) =
            MealBottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putString(MEAL_ID, param1)
                }
            }
    }
}