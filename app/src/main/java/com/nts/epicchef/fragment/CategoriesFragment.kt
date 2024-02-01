package com.nts.epicchef.fragment

import android.app.ActionBar
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.nts.epicchef.R
import com.nts.epicchef.activitys.CategoryMealsActivity
import com.nts.epicchef.activitys.MainActivity
import com.nts.epicchef.adapters.CategoriesAdapter
import com.nts.epicchef.databinding.FragmentCategoriesBinding
import com.nts.epicchef.viewModel.HomeViewModel

class CategoriesFragment : Fragment() {

    lateinit var binding: FragmentCategoriesBinding
    lateinit var categoryAdapter: CategoriesAdapter
    lateinit var viewModel: HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentCategoriesBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            prepareRecyclerView()
            observCategories()
           oncategoryClick()
    }


    private fun prepareRecyclerView() {
        categoryAdapter= CategoriesAdapter()

            binding.rvCategories.apply {
            layoutManager = GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
            adapter=categoryAdapter
        }
    }

    private fun observCategories() {
        viewModel.observCategoriesLiveData().observe(viewLifecycleOwner, Observer {categories ->
            categoryAdapter.setCategoriesList(categories)

        })
    }

    private fun oncategoryClick() {
           categoryAdapter.onItemClick ={ category ->
            val intent = Intent(activity, CategoryMealsActivity::class.java)
            intent.putExtra(HomeFragment.CATEGORY_NAME,category.strCategory)
            startActivity(intent)
        }
    }


}