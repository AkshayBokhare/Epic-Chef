package com.nts.epicchef.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.nts.epicchef.activitys.MainActivity
import com.nts.epicchef.adapters.MealsAdapter
import com.nts.epicchef.databinding.FragmentFavoritesBinding
import com.nts.epicchef.viewModel.HomeViewModel


class FavoritesFragment : Fragment() {
    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var favoritesAdapter: MealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavoritesBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()
        observFavorites()

        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                val position = viewHolder.adapterPosition
                viewModel.deleteMeal(favoritesAdapter.deffer.currentList[position])

                Toast.makeText(context, "Meal Deleted", Toast.LENGTH_SHORT).show()


                //    viewModel.insertMeal(favoritesAdapter.deffer.currentList[position])


//                    "Undo" ,
//                    View.OnClickListener {
//
//                            viewModel.insertMeal(favoritesAdapter.deffer.currentList[position])
//
//                    }
//                ).show()

            }
        }
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.rvFavorets)
    }

    private fun prepareRecyclerView() {
        favoritesAdapter = MealsAdapter()
        binding.rvFavorets.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = favoritesAdapter
        }
    }

    private fun observFavorites() {
        viewModel.observFavoresitemsMealsLivedata().observe(requireActivity(), Observer
        { meal ->

            favoritesAdapter.deffer.submitList(meal)

        })
    }

}