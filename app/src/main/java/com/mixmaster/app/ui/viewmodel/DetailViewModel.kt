package com.mixmaster.app.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.mixmaster.app.data.database.AppDatabase
import com.mixmaster.app.data.model.Cocktail
import com.mixmaster.app.data.repository.CocktailRepository
import com.mixmaster.app.data.repository.FavoriteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailViewModel(application: Application) : AndroidViewModel(application) {
    private val favoriteRepository = FavoriteRepository(AppDatabase.getDatabase(application))

    private val _cocktail = MutableStateFlow<Cocktail?>(null)
    val cocktail: StateFlow<Cocktail?> = _cocktail.asStateFlow()

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite.asStateFlow()

    fun load(id: Int) {
        _cocktail.value = CocktailRepository.getById(id)
        viewModelScope.launch {
            favoriteRepository.isFavorite(id).collect { fav ->
                _isFavorite.value = fav
            }
        }
    }

    fun toggleFavorite() {
        val c = _cocktail.value ?: return
        viewModelScope.launch {
            if (_isFavorite.value) {
                favoriteRepository.removeFavorite(c.id)
            } else {
                favoriteRepository.addFavorite(c)
            }
        }
    }
}
