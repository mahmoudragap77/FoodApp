package com.example.foodapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.databinding.PopularItemBinding
import com.example.foodapp.pogo.MealsByCategory

class MostPopularAdapter() :RecyclerView.Adapter<MostPopularAdapter.PopularMealViewHolder>(){
    lateinit var onItemClick :((MealsByCategory) -> Unit)
     var onLongItemCLick :((MealsByCategory) -> Unit)?=null
    private var mealsList =ArrayList<MealsByCategory>()

    fun setMeals(mealsList :ArrayList<MealsByCategory>){
        this.mealsList=mealsList
        notifyDataSetChanged()
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMealViewHolder {
       return PopularMealViewHolder(PopularItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount()= mealsList.size

    override fun onBindViewHolder(holder: PopularMealViewHolder, position: Int) {
       Glide.with(holder.itemView)
           .load(mealsList[position].strMealThumb)
           .into(holder.binding.imgPopularMealItem)

        holder.itemView.setOnClickListener{
            onItemClick.invoke(mealsList[position])
        }

        holder.itemView.setOnClickListener {
            onLongItemCLick?.invoke(mealsList[position])
            true

        }
    }
    class PopularMealViewHolder( var binding : PopularItemBinding) : RecyclerView.ViewHolder(binding.root)

}