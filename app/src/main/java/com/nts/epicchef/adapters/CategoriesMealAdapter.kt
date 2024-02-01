package com.nts.epicchef.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.nts.epicchef.dataClass.MealsByCategory
import com.nts.epicchef.databinding.MealItemBinding

class CategoriesMealAdapter : RecyclerView.Adapter<CategoriesMealAdapter.CategoriesMealViewModel>() {

    var mealsList = ArrayList<MealsByCategory>()

    fun setMealslist(mealsList: List<MealsByCategory>) {
        this.mealsList = mealsList as ArrayList<MealsByCategory>
        notifyDataSetChanged()
    }

    inner class CategoriesMealViewModel(val binding: MealItemBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesMealViewModel {
        return CategoriesMealViewModel(MealItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }


    override fun onBindViewHolder(holder: CategoriesMealViewModel, position: Int) {

        Glide.with(holder.itemView).load(mealsList[position].strMealThumb)
            .into(holder.binding.imgMealCat)

        holder.binding.tvMealNameCat.text = mealsList[position].strMeal
    }

    override fun getItemCount(): Int {
        return mealsList.size
    }
}