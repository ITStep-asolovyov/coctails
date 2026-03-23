package com.mixmaster.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mixmaster.app.data.model.CocktailListItem
import com.mixmaster.app.data.repository.CocktailRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class SearchUiState(
    val query: String = "",
    val results: List<CocktailListItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val hasSearched: Boolean = false
)

class SearchViewModel : ViewModel() {
    private val repository = CocktailRepository()
    private val _state = MutableStateFlow(SearchUiState())
    val state: StateFlow<SearchUiState> = _state.asStateFlow()

    private var searchJob: Job? = null

    fun onQueryChange(query: String) {
        _state.value = _state.value.copy(query = query, error = null)
        searchJob?.cancel()
        if (query.isBlank()) {
            _state.value = _state.value.copy(results = emptyList(), isLoading = false, hasSearched = false)
            return
        }
        searchJob = viewModelScope.launch {
            delay(500)
            performSearch(query)
        }
    }

    fun search(query: String = _state.value.query) {
        if (query.isBlank()) return
        searchJob?.cancel()
        searchJob = viewModelScope.launch { performSearch(query) }
    }

    private suspend fun performSearch(query: String) {
        _state.value = _state.value.copy(isLoading = true, error = null)
        repository.searchByName(query)
            .onSuccess { items ->
                _state.value = _state.value.copy(
                    results = items,
                    isLoading = false,
                    hasSearched = true
                )
            }
            .onFailure {
                _state.value = _state.value.copy(
                    isLoading = false,
                    hasSearched = true,
                    error = "Ошибка сети. Проверьте соединение."
                )
            }
    }
}
