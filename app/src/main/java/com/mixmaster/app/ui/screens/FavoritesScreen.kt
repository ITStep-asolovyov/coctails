package com.mixmaster.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mixmaster.app.ui.theme.*
import com.mixmaster.app.ui.viewmodel.FavoritesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    onCocktailClick: (Int) -> Unit,
    onBack: () -> Unit,
    vm: FavoritesViewModel = viewModel()
) {
    val favorites by vm.favorites.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Избранное",
                        style = MaterialTheme.typography.titleLarge,
                        color = OnPrimary,
                        fontWeight = FontWeight.Bold
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
        if (favorites.isEmpty()) {
            Box(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                        tint = FavoriteInactive,
                        modifier = Modifier.size(64.dp)
                    )
                    Text(
                        "Избранное пусто",
                        style = MaterialTheme.typography.headlineMedium,
                        color = OnBackground
                    )
                    Text(
                        "Добавляйте коктейли на странице деталей",
                        style = MaterialTheme.typography.bodyMedium,
                        color = OnBackground.copy(0.6f)
                    )
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
                items(favorites, key = { it.id }) { cocktail ->
                    Box {
                        CocktailCard(
                            cocktail = cocktail,
                            onClick = { onCocktailClick(cocktail.id) }
                        )
                        IconButton(
                            onClick = { vm.remove(cocktail.id) },
                            modifier = Modifier.align(Alignment.TopEnd)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Удалить",
                                tint = FavoriteActive
                            )
                        }
                    }
                }
            }
        }
    }
}
