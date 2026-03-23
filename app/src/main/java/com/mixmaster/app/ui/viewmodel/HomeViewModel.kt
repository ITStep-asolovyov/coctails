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
    val ingredient: String,
    val isNonAlcoholic: Boolean = false
) {
    ALL("Все", "🍹", ""),
    VODKA("Водка", "🥃", "Vodka"),
    RUM("Ром", "🍺", "Rum"),
    GIN("Джин", "🍸", "Gin"),
    TEQUILA("Текила", "🌵", "Tequila"),
    WHISKEY("Виски", "🥃", "Whiskey"),
    WINE("Вино", "🍷", "Wine"),
    NON_ALCOHOLIC("Без алкоголя", "🧃", "", isNonAlcoholic = true)
}

data class HomeUiState(
    val categories: List<String> = emptyList(),
    val selectedAlcohol: AlcoholTypeFilter = AlcoholTypeFilter.ALL,
    val selectedCategory: String? = null,
    val popularCocktails: List<CocktailListItem> = emptyList(),
    val isLoadingPopular: Boolean = true,
    val isLoadingCategories: Boolean = true,
    val isLoadingRandom: Boolean = false,
    val randomCocktailId: String? = null,
    val error: String? = null
)

class HomeViewModel : ViewModel() {
    private val repository = CocktailRepository()
    private val _state = MutableStateFlow(HomeUiState())
    val state: StateFlow<HomeUiState> = _state.asStateFlow()

    init {
        loadCategories()
        loadPopular()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            repository.getCategories()
                .onSuccess { cats ->
                    _state.value = _state.value.copy(
                        categories = cats,
                        isLoadingCategories = false
                    )
                }
                .onFailure {
                    _state.value = _state.value.copy(isLoadingCategories = false)
                }
        }
    }

    private fun loadPopular() {
        val alcohol = _state.value.selectedAlcohol
        val category = _state.value.selectedCategory
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoadingPopular = true, error = null)
            val result: Result<List<CocktailListItem>> = when {
                category != null -> repository.filterByCategory(category)
                alcohol == AlcoholTypeFilter.NON_ALCOHOLIC -> repository.filterByAlcohol(false)
                alcohol != AlcoholTypeFilter.ALL -> repository.filterByIngredient(alcohol.ingredient)
                else -> Result.success(repository.getPopularCocktails(8))
            }
            result
                .onSuccess { items ->
                    _state.value = _state.value.copy(
                        popularCocktails = items.take(20),
                        isLoadingPopular = false
                    )
                }
                .onFailure { e ->
                    _state.value = _state.value.copy(
                        isLoadingPopular = false,
                        error = "Ошибка загрузки. Проверьте соединение."
                    )
                }
        }
    }

    fun selectAlcohol(filter: AlcoholTypeFilter) {
        if (_state.value.selectedAlcohol == filter) return
        _state.value = _state.value.copy(
            selectedAlcohol = filter,
            selectedCategory = null
        )
        loadPopular()
    }

    fun selectCategory(category: String?) {
        if (_state.value.selectedCategory == category) return
        _state.value = _state.value.copy(
            selectedCategory = category,
            selectedAlcohol = AlcoholTypeFilter.ALL
        )
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
    fun clearError()    = run { _state.value = _state.value.copy(error = null) }
}
