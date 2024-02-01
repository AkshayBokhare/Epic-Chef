package com.nts.epicchef.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nts.epicchef.dataClass.Category
import com.nts.epicchef.dataClass.CategoryList
import com.nts.epicchef.databinding.CategoryItemBinding
import com.squareup.picasso.Picasso

class CategoriesAdapter() : RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>() {
    private var categoryList = ArrayList<Category>()
        var onItemClick :((Category)->Unit)? = null


    fun setCategoriesList(categoryList:List<Category>){
        this.categoryList = categoryList as ArrayList<Category>
        notifyDataSetChanged()
    }

    inner class CategoriesViewHolder(val binding: CategoryItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        return CategoriesViewHolder(
            CategoryItemBinding.inflate(
                LayoutInflater.from(parent.context)
        )
        )
    }
    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {

        //set Image
        Glide.with(holder.itemView)
            .load(categoryList[position].strCategoryThumb)
            .into(holder.binding.imgCategory)
        holder.binding.tvCategoryName.text =categoryList[position].strCategory

        holder.itemView.setOnClickListener {
            onItemClick!!.invoke(categoryList[position])
        }


    }
    override fun getItemCount(): Int {
       return categoryList.size
    }



}