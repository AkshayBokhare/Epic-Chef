package com.nts.epicchef.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nts.epicchef.dataClass.Meal
import com.nts.epicchef.dataClass.MealList
import com.nts.epicchef.db.MealDatabase
import com.nts.epicchef.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealViewModel (
    val mealDatabase: MealDatabase
) : ViewModel() {

    private var mealDetailsLiveData =MutableLiveData<Meal>()
    private var favoritesLiveData =MutableLiveData<List<Meal>>()

    fun getMealDetails(id:String){
        RetrofitInstance.api.getMealDetails(id).enqueue(object : Callback<MealList?> {
            override fun onResponse(call: Call<MealList?>, response: Response<MealList?>) {
                if (response.body() != null){
                    mealDetailsLiveData.value =response.body()!!.meals[0]
                }else{
                    return
                }
            }
            override fun onFailure(call: Call<MealList?>, t: Throwable) {
                Log.d("MealActivity",t.message.toString())
            }
        })
    }
    fun observMealDetailsLiveData():LiveData<Meal>{
        return mealDetailsLiveData
    }

    fun observFavoresitemsLivedata():LiveData<List<Meal>>{
        return favoritesLiveData
    }
    //Room Operations
    fun insertMeal(meal: Meal){
        viewModelScope.launch{
            mealDatabase.mealDoa().upsert(meal)
        }
    }
}
