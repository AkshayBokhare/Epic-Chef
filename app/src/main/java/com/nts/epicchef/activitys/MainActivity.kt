package com.nts.epicchef.activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nts.epicchef.R
import com.nts.epicchef.db.MealDatabase
import com.nts.epicchef.viewModel.HomeViewModel
import com.nts.epicchef.viewModel.HomeViewModelFactory

class MainActivity : AppCompatActivity() {



     val viewModel:HomeViewModel by lazy {
         val mealDatabase=MealDatabase.getInstance(this)
         val homeViewModelProviderFactory = HomeViewModelFactory(mealDatabase)
         ViewModelProvider(this,homeViewModelProviderFactory)[HomeViewModel::class.java]
     }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_nav)
        val navController = Navigation.findNavController(this, R.id.host_fragment)
        NavigationUI.setupWithNavController(bottomNavigation,navController)

    }


}