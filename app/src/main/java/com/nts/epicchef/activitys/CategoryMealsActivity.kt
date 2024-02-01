package com.nts.epicchef.activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.nts.epicchef.adapters.CategoriesMealAdapter
import com.nts.epicchef.databinding.ActivityCategoryMealsBinding
import com.nts.epicchef.fragment.HomeFragment
import com.nts.epicchef.viewModel.CategoriesMealsViewModel

class CategoryMealsActivity : AppCompatActivity() {

    lateinit var binding: ActivityCategoryMealsBinding
    lateinit var categoriesMealsViewModel: CategoriesMealsViewModel
    lateinit var categoryMealsAdapter: CategoriesMealAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prepareRecyclerView()

        categoriesMealsViewModel = ViewModelProvider(this)[CategoriesMealsViewModel::class.java]
        categoriesMealsViewModel.getMealsByCategories(intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!)


        categoriesMealsViewModel.observMealsLiveData().observe(this, Observer { mealsList ->


            categoryMealsAdapter.setMealslist(mealsList)
            binding.tvCategorieCount.text = mealsList.size.toString()

        })
    }

    private fun prepareRecyclerView() {

        categoryMealsAdapter = CategoriesMealAdapter()

        binding.rvMeals.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = categoryMealsAdapter
        }
    }
}