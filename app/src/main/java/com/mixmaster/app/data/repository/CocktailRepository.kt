package com.mixmaster.app.data.repository

import com.mixmaster.app.data.api.RetrofitClient
import com.mixmaster.app.data.model.Cocktail
import com.mixmaster.app.data.model.CocktailListItem
import com.mixmaster.app.data.model.toCocktail
import com.mixmaster.app.data.model.toListItem
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class CocktailRepository {
    private val api = RetrofitClient.instance

    suspend fun searchByName(query: String): Result<List<CocktailListItem>> = runCatching {
        api.searchByName(query).drinks?.map { it.toListItem() } ?: emptyList()
    }

    suspend fun filterByAlcohol(alcoholic: Boolean): Result<List<CocktailListItem>> = runCatching {
        val filter = if (alcoholic) "Alcoholic" else "Non_Alcoholic"
        api.filterByAlcohol(filter).drinks?.map { it.toListItem() } ?: emptyList()
    }

    suspend fun filterByIngredient(ingredient: String): Result<List<CocktailListItem>> = runCatching {
        api.filterByIngredient(ingredient).drinks?.map { it.toListItem() } ?: emptyList()
    }

    suspend fun filterByCategory(category: String): Result<List<CocktailListItem>> = runCatching {
        api.filterByCategory(category).drinks?.map { it.toListItem() } ?: emptyList()
    }

    suspend fun getById(id: String): Result<Cocktail> = runCatching {
        api.getById(id).drinks?.firstOrNull()?.toCocktail()
            ?: error("Коктейль не найден")
    }

    suspend fun getRandom(): Result<Cocktail> = runCatching {
        api.getRandom().drinks?.firstOrNull()?.toCocktail()
            ?: error("Не удалось загрузить коктейль")
    }

    suspend fun getCategories(): Result<List<String>> = runCatching {
        api.getCategories().drinks?.map { it.name } ?: emptyList()
    }

    /** Load [count] random cocktails in parallel. */
    suspend fun getPopularCocktails(count: Int = 8): List<CocktailListItem> = coroutineScope {
        (1..count).map {
            async { runCatching { api.getRandom().drinks?.firstOrNull()?.toListItem() }.getOrNull() }
        }.map { it.await() }
            .filterNotNull()
            .distinctBy { it.id }
    }
}
