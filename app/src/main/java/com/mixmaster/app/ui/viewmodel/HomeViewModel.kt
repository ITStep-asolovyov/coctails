package com.mixmaster.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mixmaster.app.data.repository.CocktailRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

enum class AlcoholFilter(val displayName: String) {
    ALL("Все"),
    ALCOHOLIC("Алкогольные"),
    NON_ALCOHOLIC("Безалкогольные")
}

data class HomeUiState(
    val searchQuery: String = "",
    val selectedFilter: AlcoholFilter = AlcoholFilter.ALL,
    val isLoadingRandom: Boolean = false,
    val randomCocktailId: String? = null,
    val error: String? = null
)

class HomeViewModel : ViewModel() {
    private val repository = CocktailRepository()
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun setSearchQuery(query: String) {
        _uiState.value = _uiState.value.copy(searchQuery = query, error = null)
    }

    fun setFilter(filter: AlcoholFilter) {
        _uiState.value = _uiState.value.copy(selectedFilter = filter)
    }

    fun getRandomCocktail() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoadingRandom = true, error = null)
            repository.getRandom()
                .onSuccess { cocktail ->
                    _uiState.value = _uiState.value.copy(
                        isLoadingRandom = false,
                        randomCocktailId = cocktail.id
                    )
                }
                .onFailure { e ->
                    _uiState.value = _uiState.value.copy(
                        isLoadingRandom = false,
                        error = "Ошибка сети. Проверьте подключение."
                    )
                }
        }
    }

    fun clearRandomCocktailId() {
        _uiState.value = _uiState.value.copy(randomCocktailId = null)
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}
