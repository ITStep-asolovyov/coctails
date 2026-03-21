package com.mixmaster.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.mixmaster.app.data.model.AlcoholType
import com.mixmaster.app.data.model.Cocktail
import com.mixmaster.app.data.model.Difficulty
import com.mixmaster.app.data.model.FlavorType
import com.mixmaster.app.data.repository.CocktailRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ResultsViewModel : ViewModel() {
    private val _results = MutableStateFlow<List<Cocktail>>(emptyList())
    val results: StateFlow<List<Cocktail>> = _results.asStateFlow()

    fun load(
        alcohol: String,
        flavors: String,
        difficulty: String,
        maxStrength: Int
    ) {
        val alcoholType = if (alcohol.isBlank()) null
            else AlcoholType.values().find { it.name == alcohol }

        val flavorList = if (flavors.isBlank()) emptyList()
            else flavors.split(",").mapNotNull { f ->
                FlavorType.values().find { it.name == f }
            }

        val diff = if (difficulty.isBlank()) null
            else Difficulty.values().find { it.name == difficulty }

        _results.value = CocktailRepository.filter(
            alcoholType = alcoholType,
            flavors = flavorList,
            difficulty = diff,
            maxStrength = maxStrength
        )
    }
}
