package com.mixmaster.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mixmaster.app.data.model.Cocktail
import com.mixmaster.app.ui.theme.*
import com.mixmaster.app.ui.viewmodel.ResultsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultsScreen(
    alcohol: String,
    flavors: String,
    difficulty: String,
    maxStrength: Int,
    onCocktailClick: (Int) -> Unit,
    onBack: () -> Unit,
    vm: ResultsViewModel = viewModel()
) {
    val results by vm.results.collectAsState()

    LaunchedEffect(alcohol, flavors, difficulty, maxStrength) {
        vm.load(alcohol, flavors, difficulty, maxStrength)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Результаты (${results.size})",
                        style = MaterialTheme.typography.titleLarge,
                        color = OnPrimary
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Назад", tint = OnPrimary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Surface)
            )
        },
        containerColor = Background
    ) { padding ->
        if (results.isEmpty()) {
            Box(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Ничего не найдено", style = MaterialTheme.typography.headlineMedium, color = OnBackground)
                    Text("Попробуйте изменить фильтры", style = MaterialTheme.typography.bodyMedium, color = OnBackground.copy(0.6f))
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(results) { cocktail ->
                    CocktailCard(cocktail = cocktail, onClick = { onCocktailClick(cocktail.id) })
                }
            }
        }
    }
}

@Composable
fun CocktailCard(cocktail: Cocktail, onClick: () -> Unit) {
    val strengthColor = when {
        cocktail.strengthPercent == 0 -> Strength0
        cocktail.strengthPercent <= 15 -> Strength1
        cocktail.strengthPercent <= 25 -> Strength2
        else -> Strength3
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = Surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            // Left accent bar
            Box(
                modifier = Modifier
                    .width(6.dp)
                    .height(100.dp)
                    .background(
                        brush = Brush.verticalGradient(listOf(CardGradientStart, CardGradientEnd))
                    )
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = cocktail.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = OnSurface,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = cocktail.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = OnSurface.copy(0.7f),
                    maxLines = 2
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Chip(label = cocktail.alcoholType.displayName)
                    Chip(label = cocktail.difficulty.displayName)
                    Box(
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.small)
                            .background(strengthColor.copy(0.2f))
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "${cocktail.strengthPercent}%",
                            style = MaterialTheme.typography.labelSmall,
                            color = strengthColor,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun Chip(label: String) {
    Box(
        modifier = Modifier
            .clip(MaterialTheme.shapes.small)
            .background(SurfaceVariant)
            .padding(horizontal = 8.dp, vertical = 2.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = OnSurface.copy(0.8f)
        )
    }
}
