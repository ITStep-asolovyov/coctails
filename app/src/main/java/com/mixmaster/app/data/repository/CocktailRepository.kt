package com.mixmaster.app.data.repository

import com.mixmaster.app.data.api.RetrofitClient
import com.mixmaster.app.data.model.Cocktail
import com.mixmaster.app.data.model.CocktailListItem
import com.mixmaster.app.data.model.toCocktail
import com.mixmaster.app.data.model.toListItem
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class CocktailRepository {
    private val api = RetrofitClient.apmixApi
    private val imageApi = RetrofitClient.imageApi

    /** Silently fetches a cocktail thumbnail from TheCocktailDB by name. */
    private suspend fun fetchImage(name: String): String? = runCatching {
        imageApi.searchByName(name).drinks?.firstOrNull()?.thumb
    }.getOrNull()

    /**
     * Search by exact name. Returns a list of 0 or 1 items.
     * A 404 (not found) is treated as an empty result, not an error.
     */
    suspend fun searchByName(query: String): Result<List<CocktailListItem>> = runCatching {
        try {
            val dto = api.getByName(query)
            val imageUrl = fetchImage(dto.name ?: query)
            listOf(dto.toListItem(imageUrl))
        } catch (e: retrofit2.HttpException) {
            if (e.code() == 404) emptyList() else throw e
        }
    }

    /**
     * Fetches [count] cocktails for a given ingredient in parallel.
     * Since the API returns one cocktail per call, we make multiple calls and deduplicate.
     */
    suspend fun filterByIngredient(
        ingredient: String,
        count: Int = 8
    ): Result<List<CocktailListItem>> = runCatching {
        coroutineScope {
            (1..count).map {
                async {
                    runCatching {
                        val dto = api.getByIngredient(ingredient)
                        val imageUrl = fetchImage(dto.name ?: "")
                        dto.toListItem(imageUrl)
                    }.getOrNull()
                }
            }.map { it.await() }
                .filterNotNull()
                .distinctBy { it.id }
        }
    }

    suspend fun getById(id: String): Result<Cocktail> = runCatching {
        val dto = api.getById(id)
        val imageUrl = fetchImage(dto.name ?: "")
        dto.toCocktail(imageUrl)
    }

    suspend fun getRandom(): Result<Cocktail> = runCatching {
        val dto = api.getRandom()
        val imageUrl = fetchImage(dto.name ?: "")
        dto.toCocktail(imageUrl)
    }

    /** Loads [count] unique random cocktails in parallel. */
    suspend fun getPopularCocktails(count: Int = 8): List<CocktailListItem> = coroutineScope {
        (1..count).map {
            async {
                runCatching {
                    val dto = api.getRandom()
                    val imageUrl = fetchImage(dto.name ?: "")
                    dto.toListItem(imageUrl)
                }.getOrNull()
            }
        }.map { it.await() }
            .filterNotNull()
            .distinctBy { it.id }
    }
}
