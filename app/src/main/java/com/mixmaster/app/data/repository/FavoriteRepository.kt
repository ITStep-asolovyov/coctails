package com.mixmaster.app.data.repository

import com.mixmaster.app.data.database.AppDatabase
import com.mixmaster.app.data.database.FavoriteEntity
import com.mixmaster.app.data.model.CocktailListItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoriteRepository(database: AppDatabase) {
    private val dao = database.favoriteDao()

    fun getAllFavorites(): Flow<List<CocktailListItem>> {
        return dao.getAllFavorites().map { entities ->
            entities.map { entity ->
                CocktailListItem(
                    id = entity.cocktailId,
                    name = entity.cocktailName,
                    imageUrl = entity.cocktailThumb
                )
            }
        }
    }

    fun isFavorite(id: String): Flow<Boolean> = dao.isFavorite(id)

    suspend fun addFavorite(id: String, name: String, thumb: String?) {
        dao.addFavorite(FavoriteEntity(cocktailId = id, cocktailName = name, cocktailThumb = thumb))
    }

    suspend fun removeFavorite(cocktailId: String) {
        dao.removeFavoriteById(cocktailId)
    }
}
