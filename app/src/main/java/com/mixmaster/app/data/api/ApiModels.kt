package com.mixmaster.app.data.api

import com.google.gson.annotations.SerializedName

// ─── apmix.ru DTOs ───────────────────────────────────────────────────────────

data class ApmixCocktailDto(
    @SerializedName("id") val id: String,
    @SerializedName("cocktail_name") val name: String?,
    @SerializedName("cocktail_alias") val alias: List<String>?,
    @SerializedName("cocktail_base_type") val baseType: String?,
    @SerializedName("cocktail_cooking_type") val cookingType: String?,
    @SerializedName("cocktail_type_drinks") val typeDrinks: String?,
    @SerializedName("cocktail_type_cap") val typeCap: String?,
    @SerializedName("cocktail_type") val type: String?,
    @SerializedName("cocktail_taste") val taste: List<String>?,
    @SerializedName("cocktail_ingredients") val ingredients: List<String>?,
    @SerializedName("cocktail_build") val build: String?,
    @SerializedName("cocktail_note") val note: String?,
    @SerializedName("cocktail_complexity_type") val complexity: String?,
    @SerializedName("cocktail_author") val author: String?,
    @SerializedName("cocktail_history") val history: String?,
    @SerializedName("cocktail_story") val story: String?
)

// ─── TheCocktailDB (image fetching only) ─────────────────────────────────────

data class CocktailDbSearchResponse(
    @SerializedName("drinks") val drinks: List<CocktailDbThumbDto>?
)

data class CocktailDbThumbDto(
    @SerializedName("strDrinkThumb") val thumb: String?
)
