package com.mixmaster.app.ui.screens

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocalBar
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.mixmaster.app.data.model.CocktailListItem
import com.mixmaster.app.ui.components.ShimmerListItem
import com.mixmaster.app.ui.theme.GoldAccent
import com.mixmaster.app.ui.theme.SurfaceCard
import com.mixmaster.app.ui.theme.TextMuted
import com.mixmaster.app.ui.theme.TextPrimary
import com.mixmaster.app.ui.theme.TextSecondary
import com.mixmaster.app.ui.viewmodel.ResultsViewModel

@Composable
fun ResultsScreen(
    query: String,
    filter: String,
    onCocktailClick: (String) -> Unit,
    onBack: () -> Unit,
    viewModel: ResultsViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(query, filter) {
        viewModel.load(query, filter)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        // Top bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    Icons.Filled.ArrowBack,
                    contentDescription = "Назад",
                    tint = GoldAccent,
                    modifier = Modifier.size(24.dp)
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = when {
                        query.isNotBlank() -> "«$query»"
                        filter == "Alcoholic" -> "Алкогольные"
                        filter == "Non_Alcoholic" -> "Безалкогольные"
                        else -> "Все коктейли"
                    },
                    style = MaterialTheme.typography.titleLarge,
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                if (!uiState.isLoading && uiState.error == null) {
                    Text(
                        text = "${uiState.items.size} коктейлей",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextMuted
                    )
                }
            }
        }

        when {
            uiState.isLoading -> {
                LazyColumn(
                    contentPadding = PaddingValues(vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(6) {
                        ShimmerListItem()
                    }
                }
            }

            uiState.error != null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text("⚠️", fontSize = 48.sp)
                        Text(
                            text = uiState.error!!,
                            style = MaterialTheme.typography.bodyLarge,
                            color = TextSecondary,
                            modifier = Modifier.padding(horizontal = 32.dp)
                        )
                    }
                }
            }

            uiState.items.isEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Icon(
                            Icons.Filled.SearchOff,
                            contentDescription = null,
                            tint = TextMuted,
                            modifier = Modifier.size(64.dp)
                        )
                        Text(
                            text = "Ничего не найдено",
                            style = MaterialTheme.typography.titleMedium,
                            color = TextSecondary
                        )
                        Text(
                            text = "Попробуйте другой запрос",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextMuted
                        )
                    }
                }
            }

            else -> {
                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    itemsIndexed(uiState.items) { index, item ->
                        androidx.compose.animation.AnimatedVisibility(
                            visible = true,
                            enter = fadeIn(tween(300, delayMillis = index * 60)) +
                                    slideInVertically(tween(300, delayMillis = index * 60)) { it / 2 }
                        ) {
                            CocktailCard(
                                item = item,
                                onClick = { onCocktailClick(item.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CocktailCard(
    item: CocktailListItem,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .clip(RoundedCornerShape(20.dp))
            .clickable(onClick = onClick)
    ) {
        // Image
        if (item.imageUrl != null) {
            AsyncImage(
                model = item.imageUrl,
                contentDescription = item.name,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(SurfaceCard),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Filled.LocalBar,
                    contentDescription = null,
                    tint = TextMuted,
                    modifier = Modifier.size(64.dp)
                )
            }
        }

        // Gradient overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color(0x66000000),
                            Color(0xCC000000)
                        ),
                        startY = 80f
                    )
                )
        )

        // Name
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.BottomStart
        ) {
            Text(
                text = item.name,
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
