package com.example.foodapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodapp.ViewModel.HomeViewModel
import com.example.foodapp.activiteis.MainActivity
import com.example.foodapp.adapters.MealsAdapter
import com.example.foodapp.databinding.FragmentSearchBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SearchFragment : Fragment() {

    private lateinit var binding :FragmentSearchBinding
    private lateinit var viewModel :HomeViewModel
    private lateinit var searchRecViewAdapter :MealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding =FragmentSearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecView()
        binding.imgSearch.setOnClickListener {
            searchMeals()
        }

        observeSearchMealLiveData()

        var searchJob : Job? =null
        binding.edSearchBox.addTextChangedListener{searchQueary ->
            searchJob?.cancel()
            searchJob =lifecycleScope.launch {
                delay(500)
                viewModel.searchMeal(searchQueary.toString())
            }
        }
    }

    private fun observeSearchMealLiveData() {
            viewModel.observeSearchMealLiveData().observe(viewLifecycleOwner, Observer { mealList ->

            searchRecViewAdapter.differ.submitList(mealList)
            })
    }

    private fun searchMeals() {
        val searchQueary = binding.edSearchBox.text.toString()
        if (searchQueary.isNotEmpty()){
            viewModel.searchMeal(searchQueary)
        }
    }

    private fun prepareRecView() {
         searchRecViewAdapter =MealsAdapter()
        binding.rvSearchMeals.apply {
            layoutManager=GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter=searchRecViewAdapter
        }
    }


}