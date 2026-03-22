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

sealed class DetailUiState {
    object Loading : DetailUiState()
    data class Success(val cocktail: Cocktail, val isFavorite: Boolean) : DetailUiState()
    data class Error(val message: String) : DetailUiState()
}

class DetailViewModel(application: Application) : AndroidViewModel(application) {
    private val cocktailRepository = CocktailRepository()
    private val favoriteRepository = FavoriteRepository(AppDatabase.getDatabase(application))

    private val _uiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    private var currentCocktail: Cocktail? = null

    fun load(id: String) {
        viewModelScope.launch {
            _uiState.value = DetailUiState.Loading
            cocktailRepository.getById(id)
                .onSuccess { cocktail ->
                    currentCocktail = cocktail
                    favoriteRepository.isFavorite(id).collect { isFav ->
                        _uiState.value = DetailUiState.Success(cocktail, isFav)
                    }
                }
                .onFailure {
                    _uiState.value = DetailUiState.Error("Не удалось загрузить коктейль")
                }
        }
    }

    fun toggleFavorite() {
        val cocktail = currentCocktail ?: return
        val current = (_uiState.value as? DetailUiState.Success) ?: return
        viewModelScope.launch {
            if (current.isFavorite) {
                favoriteRepository.removeFavorite(cocktail.id)
            } else {
                favoriteRepository.addFavorite(cocktail.id, cocktail.name, cocktail.imageUrl)
            }
        }
    }
}
