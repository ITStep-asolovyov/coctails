package com.mixmaster.app.data.api

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApmixApiService {

    @GET("api/v1/cocktails/id/random")
    suspend fun getRandom(): ApmixCocktailDto

    @GET("api/v1/cocktails/id/{id}")
    suspend fun getById(@Path("id") id: String): ApmixCocktailDto

    @GET("api/v1/cocktails/name/{name}")
    suspend fun getByName(@Path("name") name: String): ApmixCocktailDto

    @GET("api/v1/cocktails/ingredient/{ingredient}")
    suspend fun getByIngredient(@Path("ingredient") ingredient: String): ApmixCocktailDto
}

interface CocktailDbImageService {

    @GET("search.php")
    suspend fun searchByName(@Query("s") name: String): CocktailDbSearchResponse
}
