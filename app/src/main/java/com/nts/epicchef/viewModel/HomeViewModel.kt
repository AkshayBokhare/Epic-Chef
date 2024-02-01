package com.nts.epicchef.viewModel

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nts.epicchef.R
import com.nts.epicchef.activitys.MainActivity
import com.nts.epicchef.dataClass.Category
import com.nts.epicchef.dataClass.CategoryList
import com.nts.epicchef.dataClass.MealsBYCategoriesList
import com.nts.epicchef.dataClass.MealsByCategory
import com.nts.epicchef.dataClass.Meal
import com.nts.epicchef.dataClass.MealList

import com.nts.epicchef.db.MealDatabase
import com.nts.epicchef.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@SuppressLint("StaticFieldLeak")
class HomeViewModel(private val mealDatabase: MealDatabase):ViewModel()  {

    private  var randomMealLiveData  = MutableLiveData<Meal>()
    private var populerItemsLiveData =  MutableLiveData<List<MealsByCategory>>()
    private var categoriesLiveData   = MutableLiveData<List<Category>>()
    private var favoritesLiveData =mealDatabase.mealDoa().getAllMeals()
    private var searchMealsLiveData = MutableLiveData<List<Meal>>()

    init {
        //if hardware configuration change original state still remin
        getRandomMeal()
    }

    fun getRandomMeal(){

        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealList?> {
            override fun onResponse(call: Call<MealList?>, response: Response<MealList?>) {

                if (response.body() != null){

                    val randomMeal : Meal = response.body()!!.meals[0]
                    randomMealLiveData.value = randomMeal




                }else{
                    return
                }
            }
            override fun onFailure(call: Call<MealList?>, t: Throwable) {
                Log.d("Home Fragment" , t.message.toString())
            }
        })
    }

    fun getPopulerItem(){
        RetrofitInstance.api.getPopulerItem("Seafood").enqueue(object : Callback<MealsBYCategoriesList?> {
            override fun onResponse(
                call: Call<MealsBYCategoriesList?>,
                response: Response<MealsBYCategoriesList?>
            ) {
                if (response.body() != null){

                    populerItemsLiveData.value=response.body()!!.meals

                }
            }

            override fun onFailure(call: Call<MealsBYCategoriesList?>, t: Throwable) {
               Log.d("HomeFragemnt",t.message.toString())
            }
        })

    }

    fun getCategories(){
        RetrofitInstance.api.getCategories().enqueue(object:Callback<CategoryList>{
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {

                response.body()?.let {categoryList ->
                    categoriesLiveData.postValue(categoryList.categories)
                }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Log.e("HomeViewFragemnt",t.message.toString())
            }

        })
    }

    fun deleteMeal(meal: Meal) {
        viewModelScope.launch {
            mealDatabase.mealDoa().delete(meal)
        }
    }
    //Room Operations
    fun insertMeal(meal: Meal){
        viewModelScope.launch{
            mealDatabase.mealDoa().upsert(meal)
        }
    }

    fun searchMeals(searchQuery: String) = RetrofitInstance.api.searchMeal(searchQuery).enqueue(object : Callback<MealList?> {
        override fun onResponse(call: Call<MealList?>, response: Response<MealList?>) {
           val mealsList =response.body()?.meals
            mealsList?.let {
                searchMealsLiveData.postValue(it)

            }
        }

        override fun onFailure(call: Call<MealList?>, t: Throwable) {
            Log.e("HomeViewFragemnt",t.message.toString())
        }
    })


    fun observRandomLiveData() :LiveData<Meal>{
        return randomMealLiveData
    }
    fun observPopulerItemsLiveData() :LiveData<List<MealsByCategory>>{
        return populerItemsLiveData
    }
    fun observCategoriesLiveData() :LiveData<List<Category>>{
        return categoriesLiveData
    }
    fun observFavoresitemsMealsLivedata():LiveData<List<Meal>>{
        return favoritesLiveData
    }
    fun observSearchMealsLivedata():LiveData<List<Meal>> = searchMealsLiveData


//    fun loadingCase(){
//
//        val progressbar = findViewById<ProgressBar>(R.id.test_progresbarr)
//        progressbar.visibility=View.VISIBLE
//    }
////    fun onResponceCase(){
////        val progressbar = findViewById(R.id.test_progresbarr)
////        progressbar.visibility=View.INVISIBLE
////    }

}