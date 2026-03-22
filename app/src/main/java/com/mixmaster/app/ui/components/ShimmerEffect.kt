package com.mixmaster.app.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.mixmaster.app.ui.theme.ShimmerBase
import com.mixmaster.app.ui.theme.ShimmerHighlight

@Composable
fun shimmerBrush(): Brush {
    val transition = rememberInfiniteTransition(label = "shimmer")
    val translateAnim by transition.animateFloat(
        initialValue = -600f,
        targetValue = 1400f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmerTranslate"
    )
    return Brush.linearGradient(
        colors = listOf(ShimmerBase, ShimmerHighlight, ShimmerBase),
        start = Offset(translateAnim, 0f),
        end = Offset(translateAnim + 600f, 0f)
    )
}

@Composable
fun ShimmerCocktailCard(modifier: Modifier = Modifier) {
    val brush = shimmerBrush()
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(280.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(brush)
    )
}

@Composable
fun ShimmerListItem(modifier: Modifier = Modifier) {
    val brush = shimmerBrush()
    Column(modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 6.dp)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(brush)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .height(18.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(brush)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth(0.4f)
                .height(14.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(brush)
        )
    }
}
