package com.mixmaster.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.mixmaster.app.data.model.AlcoholType
import com.mixmaster.app.data.model.Difficulty
import com.mixmaster.app.data.model.FlavorType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class HomeUiState(
    val selectedAlcohol: AlcoholType? = null,
    val selectedFlavors: Set<FlavorType> = emptySet(),
    val selectedDifficulty: Difficulty? = null,
    val maxStrength: Int = 100
)

class HomeViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun selectAlcohol(type: AlcoholType?) {
        _uiState.value = _uiState.value.copy(selectedAlcohol = type)
    }

    fun toggleFlavor(flavor: FlavorType) {
        val current = _uiState.value.selectedFlavors.toMutableSet()
        if (flavor in current) current.remove(flavor) else current.add(flavor)
        _uiState.value = _uiState.value.copy(selectedFlavors = current)
    }

    fun selectDifficulty(difficulty: Difficulty?) {
        _uiState.value = _uiState.value.copy(selectedDifficulty = difficulty)
    }

    fun setMaxStrength(value: Int) {
        _uiState.value = _uiState.value.copy(maxStrength = value)
    }

    fun reset() {
        _uiState.value = HomeUiState()
    }
}
