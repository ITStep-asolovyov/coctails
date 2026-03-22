package com.mixmaster.app.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.mixmaster.app.data.database.AppDatabase
import com.mixmaster.app.data.model.CocktailListItem
import com.mixmaster.app.data.repository.FavoriteRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = FavoriteRepository(AppDatabase.getDatabase(application))

    val favorites: StateFlow<List<CocktailListItem>> = repository.getAllFavorites()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun remove(cocktailId: String) {
        viewModelScope.launch {
            repository.removeFavorite(cocktailId)
        }
    }
}
