package com.nts.epicchef.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nts.epicchef.R
import com.nts.epicchef.activitys.MainActivity
import com.nts.epicchef.adapters.MealsAdapter
import com.nts.epicchef.databinding.FragmentSearchBinding
import com.nts.epicchef.viewModel.HomeViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SearchFragment : Fragment() {

        private lateinit var bindig : FragmentSearchBinding
        private lateinit var viewModel:HomeViewModel
        private lateinit var searchRecyclerViewAdapter:MealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        bindig=FragmentSearchBinding.inflate(inflater)
        // Inflate the layout for this fragment
        return bindig.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()
        bindig.arrow.setOnClickListener { searchMeals() }

            obserSearchMealLiveData()

            //second approch for chearch
            var searchJob:Job? = null
            bindig.edSearchBox.addTextChangedListener { searchQuery ->
                searchJob?.cancel()
                searchJob =lifecycleScope.launch {
                    delay(500)
                    viewModel.searchMeals(searchQuery.toString())
                }
            }
        }


    private fun obserSearchMealLiveData() {
        viewModel.observSearchMealsLivedata().observe(viewLifecycleOwner, Observer {mealsList->

            searchRecyclerViewAdapter.deffer.submitList(mealsList)

        })
    }

    private fun searchMeals() {
      val searchQueary = bindig.edSearchBox.text.toString()
        if (searchQueary.isNotEmpty()){
            viewModel.searchMeals(searchQueary)
        }
    }

    private fun prepareRecyclerView() {
        searchRecyclerViewAdapter= MealsAdapter()
        bindig.rvSearchMeals.apply {
            layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter =searchRecyclerViewAdapter
        }
    }


}