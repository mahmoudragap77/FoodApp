package com.example.foodapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.databinding.MealItemBinding
import com.example.foodapp.pogo.Meal

class MealsAdapter :
    RecyclerView.Adapter<MealsAdapter.FavouritesMealViewHolder>() {
    inner class FavouritesMealViewHolder(val binding: MealItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    private val diffutil = object : DiffUtil.ItemCallback<Meal>() {
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return newItem == oldItem
        }

    }

    val differ =AsyncListDiffer(this,diffutil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouritesMealViewHolder {
       return FavouritesMealViewHolder(
           MealItemBinding.inflate(
               LayoutInflater.from(parent.context),parent,false
           )
       )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: FavouritesMealViewHolder, position: Int) {
val meal = differ.currentList[position]
    Glide.with(holder.itemView)
        .load(meal.strMealThumb)
        .into(holder.binding.imgMeal)
    holder.binding.tvMealName.text =meal.strMeal
    }
}