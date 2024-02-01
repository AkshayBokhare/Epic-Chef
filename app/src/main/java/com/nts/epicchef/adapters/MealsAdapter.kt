package com.nts.epicchef.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nts.epicchef.dataClass.Meal
import com.nts.epicchef.databinding.MealItemBinding

class MealsAdapter: RecyclerView.Adapter<MealsAdapter.FavoritesMealsAdapterViewHolder>(){

  inner class FavoritesMealsAdapterViewHolder(val binding: MealItemBinding):RecyclerView.ViewHolder(binding.root)

    // New concept
    private val diffUtil = object :DiffUtil.ItemCallback<Meal>(){

        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
           return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
         return oldItem == newItem
        }
    }

    var deffer = AsyncListDiffer(this,diffUtil)

       override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MealsAdapter.FavoritesMealsAdapterViewHolder {
        return FavoritesMealsAdapterViewHolder(
            MealItemBinding.inflate(
            LayoutInflater.from(parent.context),parent,false
        )
      )
    }
    override fun onBindViewHolder(
        holder: MealsAdapter.FavoritesMealsAdapterViewHolder,
        position: Int
    ) {

        val meal = deffer.currentList[position]

        Glide.with(holder.itemView).load(meal.strMealThumb).into(holder.binding.imgMealCat)

        holder.binding.tvMealNameCat.text = meal.strMeal

    }

    override fun getItemCount(): Int {
        return deffer.currentList.size
    }

}