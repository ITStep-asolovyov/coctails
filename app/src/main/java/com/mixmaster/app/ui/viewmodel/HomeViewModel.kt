package com.mixmaster.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mixmaster.app.data.model.CocktailListItem
import com.mixmaster.app.data.repository.CocktailRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

enum class AlcoholTypeFilter(
    val displayName: String,
    val emoji: String,
    val ingredient: String
) {
    ALL("Все", "🍹", ""),
    VODKA("Водка", "🥃", "Водка"),
    RUM("Ром", "🍺", "Ром"),
    GIN("Джин", "🍸", "Джин"),
    TEQUILA("Текила", "🌵", "Текила"),
    WHISKEY("Виски", "🥃", "Виски"),
    WINE("Вино", "🍷", "Вино"),
}

data class HomeUiState(
    val selectedAlcohol: AlcoholTypeFilter = AlcoholTypeFilter.ALL,
    val popularCocktails: List<CocktailListItem> = emptyList(),
    val isLoadingPopular: Boolean = true,
    val isLoadingRandom: Boolean = false,
    val randomCocktailId: String? = null,
    val error: String? = null
)

class HomeViewModel : ViewModel() {
    private val repository = CocktailRepository()
    private val _state = MutableStateFlow(HomeUiState())
    val state: StateFlow<HomeUiState> = _state.asStateFlow()

    init {
        loadPopular()
    }

    private fun loadPopular() {
        val alcohol = _state.value.selectedAlcohol
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoadingPopular = true, error = null)
            val result = if (alcohol == AlcoholTypeFilter.ALL) {
                Result.success(repository.getPopularCocktails(8))
            } else {
                repository.filterByIngredient(alcohol.ingredient, count = 8)
            }
            result
                .onSuccess { items ->
                    _state.value = _state.value.copy(
                        popularCocktails = items.take(20),
                        isLoadingPopular = false
                    )
                }
                .onFailure {
                    _state.value = _state.value.copy(
                        isLoadingPopular = false,
                        error = "Ошибка загрузки. Проверьте соединение."
                    )
                }
        }
    }

    fun selectAlcohol(filter: AlcoholTypeFilter) {
        if (_state.value.selectedAlcohol == filter) return
        _state.value = _state.value.copy(selectedAlcohol = filter)
        loadPopular()
    }

    fun getRandomCocktail() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoadingRandom = true, error = null)
            repository.getRandom()
                .onSuccess { cocktail ->
                    _state.value = _state.value.copy(
                        isLoadingRandom = false,
                        randomCocktailId = cocktail.id
                    )
                }
                .onFailure {
                    _state.value = _state.value.copy(
                        isLoadingRandom = false,
                        error = "Ошибка сети. Попробуйте снова."
                    )
                }
        }
    }

    fun clearRandomId() = run { _state.value = _state.value.copy(randomCocktailId = null) }
    fun clearError() = run { _state.value = _state.value.copy(error = null) }
}
