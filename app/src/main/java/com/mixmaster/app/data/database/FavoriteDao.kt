package com.mixmaster.app.data.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorites ORDER BY addedAt DESC")
    fun getAllFavorites(): Flow<List<FavoriteEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE cocktailId = :id)")
    fun isFavorite(id: String): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(entity: FavoriteEntity)

    @Query("DELETE FROM favorites WHERE cocktailId = :id")
    suspend fun removeFavoriteById(id: String)
}
