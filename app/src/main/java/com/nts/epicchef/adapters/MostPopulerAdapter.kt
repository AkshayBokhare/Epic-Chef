package com.nts.epicchef.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nts.epicchef.dataClass.MealsByCategory
import com.nts.epicchef.databinding.PopulerItemBinding

class MostPopulerAdapter() :RecyclerView.Adapter<MostPopulerAdapter.PopulerMealViewHolder>() {
    lateinit var onItemClick:((MealsByCategory) -> Unit)
    private var mealList =ArrayList<MealsByCategory>()

    fun setMeals(mealList: ArrayList<MealsByCategory>){
        this.mealList=mealList
        notifyDataSetChanged()
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopulerMealViewHolder {

        return PopulerMealViewHolder(PopulerItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    }


    override fun onBindViewHolder(holder: PopulerMealViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(mealList[position].strMealThumb)
            .into(holder.binding.imgPopulerItem)
        holder.itemView.setOnClickListener {
            onItemClick.invoke(mealList[position])
        }
    }

    override fun getItemCount(): Int {
        return mealList.size
    }

    class PopulerMealViewHolder(val binding:PopulerItemBinding):RecyclerView.ViewHolder(binding.root)
}