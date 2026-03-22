package com.mixmaster.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mixmaster.app.data.model.CocktailListItem
import com.mixmaster.app.data.repository.CocktailRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class ResultsUiState(
    val items: List<CocktailListItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val query: String = "",
    val filter: String = "",
    val ingredient: String = ""
)

class ResultsViewModel : ViewModel() {
    private val repository = CocktailRepository()
    private val _uiState = MutableStateFlow(ResultsUiState())
    val uiState: StateFlow<ResultsUiState> = _uiState.asStateFlow()

    fun load(query: String, filter: String, ingredient: String) {
        val state = _uiState.value
        if (state.query == query && state.filter == filter && state.ingredient == ingredient &&
            (state.items.isNotEmpty() || state.error != null)
        ) return

        viewModelScope.launch {
            _uiState.value = ResultsUiState(
                isLoading = true, query = query, filter = filter, ingredient = ingredient
            )
            val result = when {
                query.isNotBlank() -> repository.searchByName(query)
                ingredient.isNotBlank() -> repository.filterByIngredient(ingredient)
                filter == "Alcoholic" -> repository.filterByAlcohol(true)
                filter == "Non_Alcoholic" -> repository.filterByAlcohol(false)
                else -> repository.searchByName("margarita")
            }
            result
                .onSuccess { items ->
                    _uiState.value = _uiState.value.copy(isLoading = false, items = items)
                }
                .onFailure {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = "Ошибка загрузки. Проверьте подключение к интернету."
                    )
                }
        }
    }
}
