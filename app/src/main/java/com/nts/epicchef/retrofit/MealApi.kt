package com.nts.epicchef.retrofit

import com.nts.epicchef.dataClass.CategoryList
import com.nts.epicchef.dataClass.MealsBYCategoriesList
import com.nts.epicchef.dataClass.MealList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {

    @GET("random.php")
    fun getRandomMeal() : Call<MealList>

    @GET("lookup.php?")
    fun getMealDetails(@Query("i") id:String) : Call<MealList>

    @GET("filter.php?")
    fun getPopulerItem(@Query("c") categoryName:String) :Call<MealsBYCategoriesList>

    @GET("categories.php")
    fun getCategories():Call<CategoryList>


    @GET("filter.php")
    fun getMealsByCategory(@Query("c") categoryName: String):Call<MealsBYCategoriesList>

    @GET("search.php")
    fun searchMeal(@Query("s") searchQuery: String):Call<MealList>
}