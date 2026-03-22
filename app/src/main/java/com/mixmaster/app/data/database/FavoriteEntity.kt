package com.mixmaster.app.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteEntity(
    @PrimaryKey val cocktailId: String,
    val cocktailName: String,
    val cocktailThumb: String?,
    val addedAt: Long = System.currentTimeMillis()
)
