package com.mixmaster.app.data.api

import retrofit2.http.GET
import retrofit2.http.Query

interface CocktailApiService {

    @GET("search.php")
    suspend fun searchByName(@Query("s") name: String): CocktailResponse

    @GET("lookup.php")
    suspend fun getById(@Query("i") id: String): CocktailResponse

    @GET("random.php")
    suspend fun getRandom(): CocktailResponse

    @GET("filter.php")
    suspend fun filterByAlcohol(@Query("a") alcoholic: String): FilterResponse

    @GET("filter.php")
    suspend fun filterByIngredient(@Query("i") ingredient: String): FilterResponse

    @GET("filter.php")
    suspend fun filterByCategory(@Query("c") category: String): FilterResponse

    @GET("list.php")
    suspend fun getCategories(@Query("c") type: String = "list"): CategoryListResponse
}
