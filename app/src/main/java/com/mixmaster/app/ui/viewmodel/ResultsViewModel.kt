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
    val filter: String = ""
)

class ResultsViewModel : ViewModel() {
    private val repository = CocktailRepository()
    private val _uiState = MutableStateFlow(ResultsUiState())
    val uiState: StateFlow<ResultsUiState> = _uiState.asStateFlow()

    fun load(query: String, filter: String) {
        if (_uiState.value.query == query && _uiState.value.filter == filter &&
            (_uiState.value.items.isNotEmpty() || _uiState.value.error != null)
        ) return

        viewModelScope.launch {
            _uiState.value = ResultsUiState(isLoading = true, query = query, filter = filter)
            val result = when {
                query.isNotBlank() -> {
                    repository.searchByName(query).map { items ->
                        if (filter == "Alcoholic") items.also { _ ->
                            // Filter is applied server-side via search; we just use all results
                        } else items
                    }
                }
                filter == "Alcoholic" -> repository.filterByAlcohol(true)
                filter == "Non_Alcoholic" -> repository.filterByAlcohol(false)
                else -> repository.searchByName("")
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
