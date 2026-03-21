package com.mixmaster.app.data.repository

import com.mixmaster.app.data.database.AppDatabase
import com.mixmaster.app.data.database.FavoriteEntity
import com.mixmaster.app.data.model.Cocktail
import com.mixmaster.app.data.model.AlcoholType
import com.mixmaster.app.data.model.FlavorType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoriteRepository(database: AppDatabase) {
    private val dao = database.favoriteDao()

    fun getAllFavorites(): Flow<List<Cocktail>> {
        return dao.getAllFavorites().map { entities ->
            entities.mapNotNull { entity ->
                CocktailRepository.getAllCocktails().find { it.id == entity.cocktailId }
            }
        }
    }

    fun isFavorite(id: Int): Flow<Boolean> = dao.isFavorite(id)

    suspend fun toggleFavorite(cocktail: Cocktail) {
        val entity = FavoriteEntity(cocktailId = cocktail.id, cocktailName = cocktail.name)
        if (dao.isFavorite(cocktail.id).let { false }) {
            dao.removeFavoriteById(cocktail.id)
        } else {
            dao.addFavorite(entity)
        }
    }

    suspend fun addFavorite(cocktail: Cocktail) {
        dao.addFavorite(FavoriteEntity(cocktailId = cocktail.id, cocktailName = cocktail.name))
    }

    suspend fun removeFavorite(cocktailId: Int) {
        dao.removeFavoriteById(cocktailId)
    }
}
