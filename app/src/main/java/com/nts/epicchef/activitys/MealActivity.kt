package com.nts.epicchef.activitys

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.nts.epicchef.R
import com.nts.epicchef.dataClass.Meal
import com.nts.epicchef.databinding.ActivityMealBinding
import com.nts.epicchef.db.MealDatabase
import com.nts.epicchef.fragment.HomeFragment
import com.nts.epicchef.viewModel.HomeViewModel
import com.nts.epicchef.viewModel.MealViewModel
import com.nts.epicchef.viewModel.MealViewModelFactory
import com.squareup.picasso.Picasso

class MealActivity : AppCompatActivity() {

    lateinit var binding: ActivityMealBinding
    lateinit var mealId:String
    lateinit var mealName:String
    lateinit var mealThumb:String
    lateinit var youtubeLink:String
    private lateinit var mealMvvm:MealViewModel
    private lateinit var homeMvvm:HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding =ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mealDatabase = MealDatabase.getInstance(this)
        val viewModelFactory = MealViewModelFactory(mealDatabase)
        mealMvvm =ViewModelProvider(this,viewModelFactory).get(MealViewModel::class.java)

//        mealMvvm = ViewModelProvider(this).get(MealViewModel::class.java)

        getMealInformationFromIntent()

        setInformationInViews()

        loadingCase()

        mealMvvm.getMealDetails(mealId)

        observMealDetailsLiveData()
        onResponceCase()

        onFevoriteClick()
        onYoutubeImgeClick()


    }

     fun onFevoriteClick() {
        binding.btnAddToFev.setOnClickListener {
            mealToSave?.let {

                mealMvvm.insertMeal(it)
//              mealMvvm.insertMeal(it)

                Toast.makeText(this,"Aded Into Fevorite",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun onYoutubeImgeClick() {
        binding.imgYoutube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW,Uri.parse(youtubeLink))
            startActivity(intent)
        }
    }


    private var mealToSave:Meal? = null
    private fun observMealDetailsLiveData() {

       mealMvvm.observMealDetailsLiveData().observe(this ,object:Observer<Meal>{
           override fun onChanged(t: Meal) {

               val meal = t
                mealToSave =meal
               binding.tvCatego.text = "Category :${meal.strCategory}"
               binding.tvArea.text = "Area :${meal.strArea}"
               binding.deatilsDiss.text = meal.strInstructions

               youtubeLink = meal.strYoutube

           }

       })
    }


    private fun getMealInformationFromIntent() {
        val intent = intent
        mealId =intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName =intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb =intent.getStringExtra(HomeFragment.MEAL_THUMB)!!
    }

    private fun setInformationInViews() {
//        Glide.with(this)
//            .load(mealThumb)
//            .into(binding.imgmeal)

        //set Image
        Picasso.get().load(mealThumb).into(binding.imgmeal);
        binding.collapsingToolbar.title=mealName
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.white))
    }


    fun loadingCase(){
        binding.progressbar.visibility = View.VISIBLE
        binding.btnAddToFev.visibility =View.INVISIBLE
        binding.tvInstruction.visibility =View.INVISIBLE
        binding.tvArea.visibility =View.INVISIBLE
        binding.tvCatego.visibility =View.INVISIBLE
        binding.imgYoutube.visibility =View.INVISIBLE
    }
    fun onResponceCase(){
        binding.progressbar.visibility=View.INVISIBLE
        binding.btnAddToFev.visibility =View.VISIBLE
        binding.tvInstruction.visibility =View.VISIBLE
        binding.tvArea.visibility =View.VISIBLE
        binding.tvCatego.visibility =View.VISIBLE
        binding.imgYoutube.visibility =View.VISIBLE
    }


}