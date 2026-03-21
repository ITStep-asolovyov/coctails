package com.mixmaster.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mixmaster.app.data.model.AlcoholType
import com.mixmaster.app.data.model.Difficulty
import com.mixmaster.app.data.model.FlavorType
import com.mixmaster.app.ui.theme.*
import com.mixmaster.app.ui.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onSearch: (alcohol: String, flavors: String, difficulty: String, maxStrength: Int) -> Unit,
    onFavoritesClick: () -> Unit,
    vm: HomeViewModel = viewModel()
) {
    val state by vm.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "MixMaster",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = OnPrimary
                    )
                },
                actions = {
                    IconButton(onClick = onFavoritesClick) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Избранное",
                            tint = FavoriteActive
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Surface)
            )
        },
        containerColor = Background
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(
                        brush = Brush.horizontalGradient(listOf(CardGradientStart, CardGradientEnd)),
                        shape = MaterialTheme.shapes.large
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        "Найди свой коктейль",
                        style = MaterialTheme.typography.headlineMedium,
                        color = OnPrimary,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "Выбери параметры и открой рецепт",
                        style = MaterialTheme.typography.bodyMedium,
                        color = OnPrimary.copy(alpha = 0.8f)
                    )
                }
            }

            // Alcohol type
            SectionCard(title = "Тип алкоголя") {
                WrapRow {
                    FilterChip(
                        selected = state.selectedAlcohol == null,
                        onClick = { vm.selectAlcohol(null) },
                        label = { Text("Любой") },
                        colors = chipColors()
                    )
                    AlcoholType.values().forEach { type ->
                        FilterChip(
                            selected = state.selectedAlcohol == type,
                            onClick = { vm.selectAlcohol(if (state.selectedAlcohol == type) null else type) },
                            label = { Text(type.displayName) },
                            colors = chipColors()
                        )
                    }
                }
            }

            // Flavors
            SectionCard(title = "Вкус") {
                WrapRow {
                    FlavorType.values().forEach { flavor ->
                        FilterChip(
                            selected = flavor in state.selectedFlavors,
                            onClick = { vm.toggleFlavor(flavor) },
                            label = { Text(flavor.displayName) },
                            colors = chipColors()
                        )
                    }
                }
            }

            // Difficulty
            SectionCard(title = "Сложность") {
                WrapRow {
                    FilterChip(
                        selected = state.selectedDifficulty == null,
                        onClick = { vm.selectDifficulty(null) },
                        label = { Text("Любая") },
                        colors = chipColors()
                    )
                    Difficulty.values().forEach { diff ->
                        FilterChip(
                            selected = state.selectedDifficulty == diff,
                            onClick = { vm.selectDifficulty(if (state.selectedDifficulty == diff) null else diff) },
                            label = { Text(diff.displayName) },
                            colors = chipColors()
                        )
                    }
                }
            }

            // Strength slider
            SectionCard(title = "Крепость до ${state.maxStrength}%") {
                Slider(
                    value = state.maxStrength.toFloat(),
                    onValueChange = { vm.setMaxStrength(it.toInt()) },
                    valueRange = 0f..100f,
                    steps = 9,
                    colors = SliderDefaults.colors(
                        thumbColor = Secondary,
                        activeTrackColor = Secondary,
                        inactiveTrackColor = SurfaceVariant
                    )
                )
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("0%", style = MaterialTheme.typography.labelSmall, color = OnSurface.copy(0.6f))
                    Text("100%", style = MaterialTheme.typography.labelSmall, color = OnSurface.copy(0.6f))
                }
            }

            // Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = { vm.reset() },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = OnSurface)
                ) {
                    Text("Сбросить")
                }
                Button(
                    onClick = {
                        onSearch(
                            state.selectedAlcohol?.name ?: "",
                            state.selectedFlavors.joinToString(",") { it.name },
                            state.selectedDifficulty?.name ?: "",
                            state.maxStrength
                        )
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Secondary)
                ) {
                    Text("Найти коктейли", color = OnSecondary)
                }
            }

            Spacer(Modifier.height(16.dp))
        }
    }
}

@Composable
private fun SectionCard(title: String, content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Surface),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = OnSurface,
                fontWeight = FontWeight.SemiBold
            )
            content()
        }
    }
}

@OptIn(androidx.compose.foundation.layout.ExperimentalLayoutApi::class)
@Composable
private fun WrapRow(content: @Composable () -> Unit) {
    androidx.compose.foundation.layout.FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        content()
    }
}

@Composable
private fun chipColors() = FilterChipDefaults.filterChipColors(
    selectedContainerColor = ChipSelected,
    selectedLabelColor = OnSecondary,
    containerColor = ChipUnselected,
    labelColor = OnSurface
)
