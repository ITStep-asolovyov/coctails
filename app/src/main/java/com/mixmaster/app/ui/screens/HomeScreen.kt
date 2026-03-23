package com.mixmaster.app.ui.screens

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.mixmaster.app.data.model.CocktailListItem
import com.mixmaster.app.ui.components.CocktailCard
import com.mixmaster.app.ui.components.ShimmerAlcoholChip
import com.mixmaster.app.ui.components.ShimmerCard
import com.mixmaster.app.ui.components.ShimmerCategoryChip
import com.mixmaster.app.ui.theme.BgCard
import com.mixmaster.app.ui.theme.BgPrimary
import com.mixmaster.app.ui.theme.BgSecondary
import com.mixmaster.app.ui.theme.DividerGold
import com.mixmaster.app.ui.theme.GlassBg
import com.mixmaster.app.ui.theme.GlassBgGold
import com.mixmaster.app.ui.theme.GlassBorder
import com.mixmaster.app.ui.theme.GlassBorderGold
import com.mixmaster.app.ui.theme.GoldAccent
import com.mixmaster.app.ui.theme.GoldDark
import com.mixmaster.app.ui.theme.GoldLight
import com.mixmaster.app.ui.theme.TextHint
import com.mixmaster.app.ui.theme.TextOnGold
import com.mixmaster.app.ui.theme.TextPrimary
import com.mixmaster.app.ui.theme.TextSecondary
import com.mixmaster.app.ui.viewmodel.AlcoholTypeFilter
import com.mixmaster.app.ui.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    bottomPadding: Dp = 0.dp,
    onNavigateToDetail: (id: String) -> Unit,
    viewModel: HomeViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.randomCocktailId) {
        state.randomCocktailId?.let { id ->
            viewModel.clearRandomId()
            onNavigateToDetail(id)
        }
    }

    LaunchedEffect(state.error) {
        state.error?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearError()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = bottomPadding + 16.dp)
        ) {
            // ── Hero Section ────────────────────────────────────────────────
            HeroSection(
                isLoading = state.isLoadingRandom,
                onRandomClick = { viewModel.getRandomCocktail() }
            )

            Spacer(Modifier.height(28.dp))

            // ── Alcohol Type Filter ─────────────────────────────────────────
            Column {
                SectionLabel(
                    text = "ТИП НАПИТКА",
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
                Spacer(Modifier.height(12.dp))
                if (state.isLoadingCategories) {
                    Row(
                        modifier = Modifier
                            .horizontalScroll(rememberScrollState())
                            .padding(horizontal = 20.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        repeat(5) { ShimmerAlcoholChip() }
                    }
                } else {
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 20.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        val filters = AlcoholTypeFilter.values()
                        items(filters.size) { i ->
                            AlcoholCircleChip(
                                filter = filters[i],
                                selected = state.selectedAlcohol == filters[i] && state.selectedCategory == null,
                                onClick = { viewModel.selectAlcohol(filters[i]) }
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(28.dp))

            // ── Category Chips ──────────────────────────────────────────────
            Column {
                SectionLabel(
                    text = "КАТЕГОРИИ",
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
                Spacer(Modifier.height(12.dp))
                if (state.isLoadingCategories) {
                    Row(
                        modifier = Modifier
                            .horizontalScroll(rememberScrollState())
                            .padding(horizontal = 20.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        repeat(6) { ShimmerCategoryChip() }
                    }
                } else {
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 20.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(state.categories.size) { i ->
                            val cat = state.categories[i]
                            CategoryGlassChip(
                                text = cat,
                                selected = state.selectedCategory == cat,
                                onClick = {
                                    viewModel.selectCategory(
                                        if (state.selectedCategory == cat) null else cat
                                    )
                                }
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(32.dp))

            // ── Gold divider ────────────────────────────────────────────────
            Box(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(DividerGold)
            )

            Spacer(Modifier.height(32.dp))

            // ── Popular Section ─────────────────────────────────────────────
            Column {
                SectionLabel(
                    text = "ПОПУЛЯРНЫЕ",
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
                Spacer(Modifier.height(16.dp))

                if (state.isLoadingPopular) {
                    Column(
                        modifier = Modifier.padding(horizontal = 20.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        repeat(3) { ShimmerCard() }
                    }
                } else if (state.popularCocktails.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Нет коктейлей",
                            style = MaterialTheme.typography.bodyLarge,
                            color = TextHint,
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    Column(
                        modifier = Modifier.padding(horizontal = 20.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        state.popularCocktails.forEachIndexed { index, item ->
                            CocktailCard(
                                item = item,
                                onClick = { onNavigateToDetail(item.id) },
                                index = index
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(16.dp))
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = bottomPadding + 8.dp)
        ) { data ->
            Snackbar(
                snackbarData = data,
                containerColor = BgCard,
                contentColor = TextPrimary
            )
        }
    }
}

// ── Hero Section ─────────────────────────────────────────────────────────────

@Composable
private fun HeroSection(isLoading: Boolean, onRandomClick: () -> Unit) {
    val transition = rememberInfiniteTransition(label = "hero")
    val glowOffset by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow"
    )
    val pulseScale by transition.animateFloat(
        initialValue = 1f,
        targetValue = 1.06f,
        animationSpec = infiniteRepeatable(
            animation = tween(900, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(320.dp)
            .background(
                Brush.verticalGradient(listOf(BgSecondary, Color(0xFF0D0820), BgPrimary))
            )
    ) {
        // Animated glow orb
        Box(
            modifier = Modifier
                .size(260.dp)
                .align(Alignment.TopEnd)
                .background(
                    Brush.radialGradient(
                        listOf(
                            GoldAccent.copy(alpha = 0.08f + glowOffset * 0.06f),
                            Color.Transparent
                        )
                    ),
                    CircleShape
                )
        )
        Box(
            modifier = Modifier
                .size(180.dp)
                .align(Alignment.BottomStart)
                .background(
                    Brush.radialGradient(
                        listOf(
                            Color(0x1A7C3ADB).copy(alpha = 0.05f + glowOffset * 0.05f),
                            Color.Transparent
                        )
                    ),
                    CircleShape
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(horizontal = 24.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "🍸", fontSize = 52.sp)
            Spacer(Modifier.height(10.dp))
            Text(
                text = "МиксМастер",
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                fontSize = 34.sp,
                color = TextPrimary,
                letterSpacing = 1.sp
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "Найди свой идеальный коктейль",
                style = MaterialTheme.typography.bodyLarge,
                color = TextSecondary
            )
            Spacer(Modifier.height(24.dp))

            // Pulsing "Мне повезёт" button
            Button(
                onClick = onRandomClick,
                enabled = !isLoading,
                modifier = Modifier
                    .height(52.dp)
                    .scale(if (!isLoading) pulseScale else 1f),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                contentPadding = PaddingValues(0.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.horizontalGradient(listOf(GoldDark, GoldAccent, GoldLight)),
                            RoundedCornerShape(14.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (isLoading) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(18.dp),
                                color = TextOnGold,
                                strokeWidth = 2.dp
                            )
                            Text(
                                "Загрузка...",
                                color = TextOnGold,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )
                        }
                    } else {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            androidx.compose.material3.Icon(
                                Icons.Filled.Casino,
                                contentDescription = null,
                                tint = TextOnGold,
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                "Мне повезёт",
                                color = TextOnGold,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                letterSpacing = 0.5.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

// ── Alcohol Circle Chip ──────────────────────────────────────────────────────

@Composable
private fun AlcoholCircleChip(
    filter: AlcoholTypeFilter,
    selected: Boolean,
    onClick: () -> Unit
) {
    val borderColor = if (selected) GoldAccent else GlassBorder
    val bgColor = if (selected) GlassBgGold else GlassBg
    val textColor = if (selected) GoldAccent else TextSecondary

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(bgColor)
                .border(BorderStroke(1.5.dp, borderColor), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(text = filter.emoji, fontSize = 28.sp)
        }
        Spacer(Modifier.height(6.dp))
        Text(
            text = filter.displayName,
            style = MaterialTheme.typography.labelSmall,
            color = textColor,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
            textAlign = TextAlign.Center,
            maxLines = 1
        )
    }
}

// ── Category Glass Chip ──────────────────────────────────────────────────────

@Composable
private fun CategoryGlassChip(text: String, selected: Boolean, onClick: () -> Unit) {
    val bgColor = if (selected) GlassBgGold else GlassBg
    val borderColor = if (selected) GlassBorderGold else GlassBorder
    val textColor = if (selected) GoldAccent else TextSecondary

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(bgColor)
            .border(BorderStroke(1.dp, borderColor), RoundedCornerShape(20.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            color = textColor,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
        )
    }
}

// ── Section Label ─────────────────────────────────────────────────────────────

@Composable
private fun SectionLabel(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        style = MaterialTheme.typography.labelLarge,
        color = GoldAccent,
        fontWeight = FontWeight.Bold,
        letterSpacing = 2.sp,
        modifier = modifier
    )
}
