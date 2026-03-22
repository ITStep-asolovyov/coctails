package com.mixmaster.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mixmaster.app.data.model.CocktailListItem
import com.mixmaster.app.ui.theme.FavoriteColor
import com.mixmaster.app.ui.theme.GoldAccent
import com.mixmaster.app.ui.theme.SurfaceElevated
import com.mixmaster.app.ui.theme.TextMuted
import com.mixmaster.app.ui.theme.TextPrimary
import com.mixmaster.app.ui.theme.TextSecondary
import com.mixmaster.app.ui.viewmodel.FavoritesViewModel

@Composable
fun FavoritesScreen(
    bottomPadding: Dp = 0.dp,
    onCocktailClick: (String) -> Unit,
    viewModel: FavoritesViewModel = viewModel()
) {
    val favorites by viewModel.favorites.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        // Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Text(
                text = "Избранное",
                style = MaterialTheme.typography.headlineMedium,
                color = TextPrimary,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = if (favorites.isEmpty()) "Нет сохранённых коктейлей"
                else "${favorites.size} коктейлей",
                style = MaterialTheme.typography.bodyMedium,
                color = TextMuted
            )
        }

        if (favorites.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = bottomPadding),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Icon(
                        Icons.Outlined.FavoriteBorder,
                        contentDescription = null,
                        tint = TextMuted,
                        modifier = Modifier.size(72.dp)
                    )
                    Text(
                        text = "Нет избранного",
                        style = MaterialTheme.typography.titleMedium,
                        color = TextSecondary
                    )
                    Text(
                        text = "Добавляйте коктейли,\nнажимая ❤ на экране деталей",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextMuted,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(
                    start = 16.dp,
                    end = 16.dp,
                    top = 8.dp,
                    bottom = bottomPadding + 16.dp
                ),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(favorites, key = { it.id }) { item ->
                    FavoriteCocktailCard(
                        item = item,
                        onClick = { onCocktailClick(item.id) },
                        onRemove = { viewModel.remove(item.id) }
                    )
                }
            }
        }
    }
}

@Composable
private fun FavoriteCocktailCard(
    item: CocktailListItem,
    onClick: () -> Unit,
    onRemove: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
    ) {
        CocktailCard(item = item, onClick = onClick)

        // Favorite badge
        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(12.dp)
                .size(32.dp)
                .clip(CircleShape)
                .background(FavoriteColor.copy(alpha = 0.9f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Filled.Favorite,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(18.dp)
            )
        }

        // Remove button
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(12.dp)
                .size(32.dp)
                .clip(CircleShape)
                .background(Color.Black.copy(alpha = 0.6f)),
            contentAlignment = Alignment.Center
        ) {
            IconButton(
                onClick = onRemove,
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    Icons.Filled.Close,
                    contentDescription = "Убрать из избранного",
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}
