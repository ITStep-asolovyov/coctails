package com.mixmaster.app.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocalBar
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.mixmaster.app.data.model.Cocktail
import com.mixmaster.app.data.model.Ingredient
import com.mixmaster.app.ui.theme.AlcoBadge
import com.mixmaster.app.ui.theme.BgCard
import com.mixmaster.app.ui.theme.BgPrimary
import com.mixmaster.app.ui.theme.BgSecondary
import com.mixmaster.app.ui.theme.BgSurface
import com.mixmaster.app.ui.theme.DividerGold
import com.mixmaster.app.ui.theme.FavActive
import com.mixmaster.app.ui.theme.GlassBg
import com.mixmaster.app.ui.theme.GlassBgGold
import com.mixmaster.app.ui.theme.GlassBorder
import com.mixmaster.app.ui.theme.GlassBorderGold
import com.mixmaster.app.ui.theme.GoldAccent
import com.mixmaster.app.ui.theme.NonAlcoBadge
import com.mixmaster.app.ui.theme.TextHint
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

    LaunchedEffect(cocktailId) { viewModel.load(cocktailId) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(BgSecondary, BgPrimary)))
    ) {
        when (val s = uiState) {
            is DetailUiState.Loading -> {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(color = GoldAccent, modifier = Modifier.size(48.dp))
                    Spacer(Modifier.height(16.dp))
                    Text("Загрузка...", color = TextSecondary)
                }
            }

            is DetailUiState.Error -> {
                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text("⚠️", fontSize = 48.sp)
                    Text(s.message, color = TextSecondary, style = MaterialTheme.typography.bodyLarge)
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Назад",
                            tint = GoldAccent
                        )
                    }
                }
            }

            is DetailUiState.Success -> {
                DetailContent(
                    cocktail = s.cocktail,
                    isFavorite = s.isFavorite,
                    onBack = onBack,
                    onToggleFavorite = { viewModel.toggleFavorite() }
                )
            }
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
    val imageHeight = 400
    val imageParallax = (scrollState.value * 0.4f).coerceAtMost(140f)

    val favScale by animateFloatAsState(
        targetValue = if (isFavorite) 1.2f else 1f,
        animationSpec = spring(dampingRatio = 0.4f, stiffness = 300f),
        label = "favScale"
    )
    val favColor by animateColorAsState(
        targetValue = if (isFavorite) FavActive else Color.White,
        label = "favColor"
    )

    Box(modifier = Modifier.fillMaxSize()) {
        // Parallax hero image
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
                        .background(BgSurface),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Filled.LocalBar,
                        contentDescription = null,
                        tint = TextHint,
                        modifier = Modifier.size(80.dp)
                    )
                }
            }
            // Bottom gradient fade
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

        // Scrollable body
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            Spacer(Modifier.height((imageHeight - 80).dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp))
                    .background(Brush.verticalGradient(listOf(BgSecondary, BgPrimary)))
            ) {
                Column(modifier = Modifier.padding(horizontal = 24.dp, vertical = 28.dp)) {

                    // Cocktail name
                    Text(
                        text = cocktail.name,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp,
                        color = TextPrimary,
                        lineHeight = 38.sp,
                        letterSpacing = 0.3.sp
                    )
                    Spacer(Modifier.height(20.dp))

                    // 3 glassmorphism info cards
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        if (cocktail.category.isNotBlank()) {
                            InfoGlassCard(
                                label = "КАТЕГОРИЯ",
                                value = cocktail.category,
                                modifier = Modifier.weight(1f)
                            )
                        }
                        if (cocktail.glass.isNotBlank()) {
                            InfoGlassCard(
                                label = "БОКАЛ",
                                value = cocktail.glass,
                                modifier = Modifier.weight(1f)
                            )
                        }
                        InfoGlassCard(
                            label = "АЛКОГОЛЬ",
                            value = cocktail.alcoholicLabel,
                            valueColor = if (cocktail.isAlcoholic) AlcoBadge else NonAlcoBadge,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(Modifier.height(28.dp))
                    HorizontalDivider(color = DividerGold, thickness = 1.dp)
                    Spacer(Modifier.height(28.dp))

                    // Ingredients
                    Text(
                        text = "ИНГРЕДИЕНТЫ",
                        style = MaterialTheme.typography.labelLarge,
                        color = GoldAccent,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.sp
                    )
                    Spacer(Modifier.height(16.dp))
                    cocktail.ingredients.forEach { ingredient ->
                        IngredientRow(ingredient = ingredient)
                        Spacer(Modifier.height(10.dp))
                    }

                    if (cocktail.instructions.isNotBlank()) {
                        Spacer(Modifier.height(8.dp))
                        HorizontalDivider(color = DividerGold, thickness = 1.dp)
                        Spacer(Modifier.height(28.dp))

                        Text(
                            text = "ПРИГОТОВЛЕНИЕ",
                            style = MaterialTheme.typography.labelLarge,
                            color = GoldAccent,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 2.sp
                        )
                        Spacer(Modifier.height(14.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(16.dp))
                                .background(BgCard)
                                .border(BorderStroke(1.dp, GlassBorder), RoundedCornerShape(16.dp))
                                .padding(20.dp)
                        ) {
                            Text(
                                text = cocktail.instructions,
                                style = MaterialTheme.typography.bodyLarge,
                                color = TextSecondary,
                                lineHeight = 28.sp
                            )
                        }
                    }

                    Spacer(Modifier.height(100.dp))
                }
            }
        }

        // Top overlay: back + favorite
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircleActionButton(onClick = onBack) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Назад",
                    tint = Color.White,
                    modifier = Modifier.size(22.dp)
                )
            }
            CircleActionButton(onClick = onToggleFavorite) {
                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = null,
                    tint = favColor,
                    modifier = Modifier
                        .size(22.dp)
                        .scale(favScale)
                )
            }
        }
    }
}

@Composable
private fun InfoGlassCard(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    valueColor: Color = TextPrimary
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .background(GlassBgGold)
            .border(BorderStroke(1.dp, GlassBorderGold), RoundedCornerShape(14.dp))
            .padding(horizontal = 10.dp, vertical = 12.dp)
    ) {
        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = GoldAccent,
                letterSpacing = 1.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.bodySmall,
                color = valueColor,
                fontWeight = FontWeight.Medium,
                lineHeight = 18.sp
            )
        }
    }
}

@Composable
private fun IngredientRow(ingredient: Ingredient) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(BgCard)
            .border(BorderStroke(1.dp, GlassBorder), RoundedCornerShape(12.dp))
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (ingredient.imageUrl != null) {
            AsyncImage(
                model = ingredient.imageUrl,
                contentDescription = ingredient.name,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(BgSurface),
                contentScale = ContentScale.Crop
            )
        } else {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(BgSurface),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "🍶", fontSize = 18.sp)
            }
        }
        Spacer(Modifier.width(12.dp))
        Text(
            text = ingredient.displayName,
            style = MaterialTheme.typography.bodyLarge,
            color = TextPrimary,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f)
        )
        if (ingredient.measure.isNotBlank()) {
            Text(
                text = ingredient.measure,
                style = MaterialTheme.typography.bodyMedium,
                color = GoldAccent,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun CircleActionButton(onClick: () -> Unit, content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .size(44.dp)
            .clip(CircleShape)
            .background(Color.Black.copy(alpha = 0.45f)),
        contentAlignment = Alignment.Center
    ) {
        IconButton(onClick = onClick) { content() }
    }
}
