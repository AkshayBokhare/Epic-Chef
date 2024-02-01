package com.nts.epicchef.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.nts.epicchef.R
import com.nts.epicchef.activitys.CategoryMealsActivity
import com.nts.epicchef.activitys.MainActivity
import com.nts.epicchef.activitys.MealActivity
import com.nts.epicchef.adapters.CategoriesAdapter
import com.nts.epicchef.adapters.MostPopulerAdapter
import com.nts.epicchef.dataClass.MealsByCategory
import com.nts.epicchef.dataClass.Meal
import com.nts.epicchef.databinding.FragmentHomeBinding
import com.nts.epicchef.viewModel.HomeViewModel


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var randomMeal: Meal
    private lateinit var populerItemAdapter: MostPopulerAdapter
    private lateinit var categoreisAdapter: CategoriesAdapter

    companion object {
        const val MEAL_ID = "com.nts.epicchef.fragment.idmeal"
        const val MEAL_NAME = "com.nts.epicchef.fragment.mealname"
        const val MEAL_THUMB = "com.nts.epicchef.fragment.mealthumb"
        const val CATEGORY_NAME = "com.nts.epicchef.fragment.categoryname"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
//        homeMvvm = ViewModelProvider(this).get(HomeViewModel::class.java)
        populerItemAdapter = MostPopulerAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        preparePopulerItemsRecyclerView()

        observRandomMeal()
        onRandomMealClick()

        viewModel.getPopulerItem()
        observPopulerItemsLiveData()
        onPopulerItemClick()

        prepareCategoriesRecyclerView()
        viewModel.getCategories()
        observCategoriesLiveData()

        oncategoryClick()

        onsearchIconClick()



    }

    private fun onsearchIconClick() {
        binding.imgSearch.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }
    }

    private fun oncategoryClick() {
        categoreisAdapter.onItemClick ={ category ->
            val intent = Intent(activity, CategoryMealsActivity::class.java)
            intent.putExtra(CATEGORY_NAME,category.strCategory)
            startActivity(intent)
        }
    }

    private fun prepareCategoriesRecyclerView() {
        categoreisAdapter = CategoriesAdapter()
        binding.recViewCategoris.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = categoreisAdapter
        }
    }

    private fun observCategoriesLiveData() {
        viewModel.observCategoriesLiveData().observe(viewLifecycleOwner, Observer
        { categories ->
            categoreisAdapter.setCategoriesList(categories)
        })
    }

    private fun onPopulerItemClick() {
        populerItemAdapter.onItemClick = { meal ->
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, meal.idMeal)
            intent.putExtra(MEAL_NAME, meal.strMeal)
            intent.putExtra(MEAL_THUMB, meal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun preparePopulerItemsRecyclerView() {
        binding.recViewMealsPopuler.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = populerItemAdapter

        }
    }

    private fun observPopulerItemsLiveData() {
        viewModel.observPopulerItemsLiveData().observe(viewLifecycleOwner,
            { mealList ->

                populerItemAdapter.setMeals(mealList = mealList as ArrayList<MealsByCategory>)
            }
        )
    }

    private fun onRandomMealClick() {
        binding.randomMealCard.setOnClickListener {
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, randomMeal.idMeal)
            intent.putExtra(MEAL_NAME, randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB, randomMeal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observRandomMeal() {
        viewModel.observRandomLiveData().observe(viewLifecycleOwner,
            { meal ->

                Glide.with(this@HomeFragment)
                    .load(meal!!.strMealThumb)
                    .into(binding.imgRandomMeal)
                this.randomMeal = meal

            })
    }


}