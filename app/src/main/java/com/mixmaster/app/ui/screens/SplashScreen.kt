package com.mixmaster.app.ui.screens

import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mixmaster.app.ui.theme.BgPrimary
import com.mixmaster.app.ui.theme.BgSecondary
import com.mixmaster.app.ui.theme.GoldAccent
import com.mixmaster.app.ui.theme.GoldLight
import com.mixmaster.app.ui.theme.TextSecondary
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onFinished: () -> Unit) {
    var show by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        show = true
        delay(1800)
        onFinished()
    }

    val alpha by animateFloatAsState(
        targetValue = if (show) 1f else 0f,
        animationSpec = tween(700),
        label = "splashAlpha"
    )
    val translateY by animateFloatAsState(
        targetValue = if (show) 0f else 40f,
        animationSpec = tween(700),
        label = "splashY"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(BgSecondary, BgPrimary, BgPrimary),
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.graphicsLayer {
                this.alpha = alpha
                this.translationY = translateY
            }
        ) {
            Text(
                text = "🍸",
                fontSize = 64.sp
            )
            Spacer(Modifier.height(16.dp))
            Text(
                text = "МиксМастер",
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                fontSize = 38.sp,
                color = GoldAccent,
                letterSpacing = 2.sp,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "ПРЕМИУМ КОКТЕЙЛИ",
                style = MaterialTheme.typography.labelLarge,
                color = TextSecondary,
                letterSpacing = 4.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}
