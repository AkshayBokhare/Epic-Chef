package com.nts.epicchef.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nts.epicchef.dataClass.MealsBYCategoriesList
import com.nts.epicchef.dataClass.MealsByCategory
import com.nts.epicchef.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoriesMealsViewModel:ViewModel() {

    val mealsLiveData = MutableLiveData<List<MealsByCategory>>()

    fun getMealsByCategories(categoryName:String){
        RetrofitInstance.api.getMealsByCategory(categoryName).enqueue(object : Callback<MealsBYCategoriesList?> {
            override fun onResponse(
                call: Call<MealsBYCategoriesList?>,
                response: Response<MealsBYCategoriesList?>
            ) {
                    response.body()?.let { mealList ->

                    mealsLiveData.postValue(mealList.meals)

                    }
            }

            override fun onFailure(call: Call<MealsBYCategoriesList?>, t: Throwable) {
                Log.e("CategoriesMealViewModel",t.message.toString())

            }
        })
    }

    //
        fun observMealsLiveData() : LiveData<List<MealsByCategory>>{
            return mealsLiveData
        }
}