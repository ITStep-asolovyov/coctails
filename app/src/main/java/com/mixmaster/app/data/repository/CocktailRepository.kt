package com.mixmaster.app.data.repository

import com.mixmaster.app.data.api.RetrofitClient
import com.mixmaster.app.data.model.Cocktail
import com.mixmaster.app.data.model.CocktailListItem
import com.mixmaster.app.data.model.toCocktail
import com.mixmaster.app.data.model.toListItem

class CocktailRepository {
    private val api = RetrofitClient.instance

    suspend fun searchByName(query: String): Result<List<CocktailListItem>> = runCatching {
        val response = api.searchByName(query)
        response.drinks?.map { it.toListItem() } ?: emptyList()
    }

    suspend fun filterByAlcohol(alcoholic: Boolean): Result<List<CocktailListItem>> = runCatching {
        val filter = if (alcoholic) "Alcoholic" else "Non_Alcoholic"
        val response = api.filterByAlcohol(filter)
        response.drinks?.map { it.toListItem() } ?: emptyList()
    }

    suspend fun getById(id: String): Result<Cocktail> = runCatching {
        val response = api.getById(id)
        response.drinks?.firstOrNull()?.toCocktail()
            ?: error("Коктейль не найден")
    }

    suspend fun getRandom(): Result<Cocktail> = runCatching {
        val response = api.getRandom()
        response.drinks?.firstOrNull()?.toCocktail()
            ?: error("Не удалось загрузить коктейль")
    }
}
