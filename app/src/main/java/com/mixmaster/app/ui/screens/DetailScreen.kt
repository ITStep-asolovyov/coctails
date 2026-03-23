package com.mixmaster.app.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocalBar
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.mixmaster.app.data.model.Cocktail
import com.mixmaster.app.data.model.Ingredient
import com.mixmaster.app.ui.theme.AlcoholicColor
import com.mixmaster.app.ui.theme.BackgroundGradientEnd
import com.mixmaster.app.ui.theme.BackgroundGradientStart
import com.mixmaster.app.ui.theme.FavoriteColor
import com.mixmaster.app.ui.theme.GoldAccent
import com.mixmaster.app.ui.theme.NonAlcoholicColor
import com.mixmaster.app.ui.theme.PurpleContainer
import com.mixmaster.app.ui.theme.PurpleLight
import com.mixmaster.app.ui.theme.SurfaceCard
import com.mixmaster.app.ui.theme.SurfaceElevated
import com.mixmaster.app.ui.theme.TextMuted
import com.mixmaster.app.ui.theme.TextPrimary
import com.mixmaster.app.ui.theme.TextSecondary
import com.mixmaster.app.ui.viewmodel.DetailUiState
import com.mixmaster.app.ui.viewmodel.DetailViewModel

@Composable
fun DetailScreen(
    cocktailId: String,
    onBack: () -> Unit,
    viewModel: DetailViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(cocktailId) {
        viewModel.load(cocktailId)
    }

    when (val state = uiState) {
        is DetailUiState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Brush.verticalGradient(listOf(BackgroundGradientStart, BackgroundGradientEnd))),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator(color = GoldAccent, modifier = Modifier.size(48.dp))
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Загрузка...", color = TextSecondary)
                }
            }
        }

        is DetailUiState.Error -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Brush.verticalGradient(listOf(BackgroundGradientStart, BackgroundGradientEnd))),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(32.dp)
                ) {
                    Text("⚠️", fontSize = 48.sp)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(state.message, color = TextSecondary, style = MaterialTheme.typography.bodyLarge)
                    Spacer(modifier = Modifier.height(16.dp))
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Назад", tint = GoldAccent)
                    }
                }
            }
        }

        is DetailUiState.Success -> {
            DetailContent(
                cocktail = state.cocktail,
                isFavorite = state.isFavorite,
                onBack = onBack,
                onToggleFavorite = { viewModel.toggleFavorite() }
            )
        }
    }
}

@Composable
private fun DetailContent(
    cocktail: Cocktail,
    isFavorite: Boolean,
    onBack: () -> Unit,
    onToggleFavorite: () -> Unit
) {
    val scrollState = rememberScrollState()
    val imageHeight = 340
    val imageParallax = (scrollState.value * 0.4f).coerceAtMost(120f)

    val favoriteScale by animateFloatAsState(
        targetValue = if (isFavorite) 1.2f else 1.0f,
        animationSpec = spring(dampingRatio = 0.4f, stiffness = 300f),
        label = "favScale"
    )
    val favoriteColor by animateColorAsState(
        targetValue = if (isFavorite) FavoriteColor else Color.White,
        label = "favColor"
    )

    Box(modifier = Modifier.fillMaxSize()) {
        // Parallax image
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(imageHeight.dp)
                .graphicsLayer { translationY = -imageParallax }
        ) {
            if (cocktail.imageUrl != null) {
                AsyncImage(
                    model = cocktail.imageUrl,
                    contentDescription = cocktail.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(listOf(Color(0xFF2D1040), Color(0xFF0D0020)))
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Filled.LocalBar,
                        contentDescription = null,
                        tint = TextMuted,
                        modifier = Modifier.size(80.dp)
                    )
                }
            }
            // Bottom fade
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color(0xCC000000)),
                            startY = 200f
                        )
                    )
            )
        }

        // Scrollable content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            Spacer(modifier = Modifier.height((imageHeight - 60).dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp))
                    .background(
                        Brush.verticalGradient(listOf(BackgroundGradientStart, BackgroundGradientEnd))
                    )
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    // Name + badges
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = cocktail.name,
                                style = MaterialTheme.typography.headlineMedium,
                                color = TextPrimary,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            if (cocktail.glass.isNotBlank()) {
                                Text(
                                    text = "🥃 ${cocktail.glass}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = TextSecondary
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(
                                    if (cocktail.isAlcoholic) AlcoholicColor.copy(alpha = 0.2f)
                                    else NonAlcoholicColor.copy(alpha = 0.2f)
                                )
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = cocktail.alcoholicLabel,
                                color = if (cocktail.isAlcoholic) AlcoholicColor else NonAlcoholicColor,
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    if (cocktail.category.isNotBlank()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = cocktail.category,
                            style = MaterialTheme.typography.bodyMedium,
                            color = GoldAccent.copy(alpha = 0.8f)
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                    Divider(color = Color.White.copy(alpha = 0.1f))
                    Spacer(modifier = Modifier.height(24.dp))

                    // Ingredients
                    Text(
                        text = "Ингредиенты",
                        style = MaterialTheme.typography.titleLarge,
                        color = GoldAccent,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    cocktail.ingredients.forEach { ingredient ->
                        IngredientRow(ingredient = ingredient)
                        Spacer(modifier = Modifier.height(12.dp))
                    }

                    if (cocktail.instructions.isNotBlank()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Divider(color = Color.White.copy(alpha = 0.1f))
                        Spacer(modifier = Modifier.height(24.dp))

                        Text(
                            text = "Приготовление",
                            style = MaterialTheme.typography.titleLarge,
                            color = GoldAccent,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(16.dp))
                                .background(SurfaceCard)
                                .padding(16.dp)
                        ) {
                            Text(
                                text = cocktail.instructions,
                                style = MaterialTheme.typography.bodyLarge,
                                color = TextSecondary,
                                lineHeight = 26.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(100.dp))
                }
            }
        }

        // Top action bar (back + favorite)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 8.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(Color.Black.copy(alpha = 0.4f)),
                contentAlignment = Alignment.Center
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        Icons.Filled.ArrowBack,
                        contentDescription = "Назад",
                        tint = Color.White,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }

            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(Color.Black.copy(alpha = 0.4f)),
                contentAlignment = Alignment.Center
            ) {
                IconButton(onClick = onToggleFavorite) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = if (isFavorite) "Убрать из избранного" else "В избранное",
                        tint = favoriteColor,
                        modifier = Modifier
                            .size(22.dp)
                            .scale(favoriteScale)
                    )
                }
            }
        }
    }
}

@Composable
private fun IngredientRow(ingredient: Ingredient) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(SurfaceCard)
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = ingredient.imageUrl,
            contentDescription = ingredient.name,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(SurfaceElevated),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = ingredient.name,
            style = MaterialTheme.typography.bodyLarge,
            color = TextPrimary,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f)
        )
        if (ingredient.measure.isNotBlank()) {
            Text(
                text = ingredient.measure,
                style = MaterialTheme.typography.bodyMedium,
                color = PurpleLight,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}
