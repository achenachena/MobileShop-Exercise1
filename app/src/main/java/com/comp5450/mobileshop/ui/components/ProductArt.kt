package com.comp5450.mobileshop.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ProductHeroArt(
    accent: Color,
    modifier: Modifier = Modifier,
    bottomBias: Float = 0.35f,
) {
    val base = Color(0xFF2A2A2A)
    Box(
        modifier
            .clip(RoundedCornerShape(20.dp))
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        accent.copy(alpha = 0.45f),
                        base,
                        Color(0xFF0D0D0D),
                    ),
                    startY = 0f,
                    endY = 1200f * bottomBias,
                ),
            ),
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .padding(24.dp)
                .background(
                    Brush.radialGradient(
                        colors = listOf(accent.copy(alpha = 0.5f), Color.Transparent),
                        center = androidx.compose.ui.geometry.Offset(120f, 180f),
                        radius = 280f,
                    ),
                ),
        )
        SneakerSilhouette(
            accent = accent,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp, vertical = 8.dp),
        )
    }
}

@Composable
private fun SneakerSilhouette(
    accent: Color,
    modifier: Modifier = Modifier,
) {
    Box(modifier, contentAlignment = Alignment.Center) {
        Box(
            Modifier
                .fillMaxSize(0.88f)
                .clip(RoundedCornerShape(48.dp))
                .background(
                    Brush.linearGradient(
                        colors = listOf(Color(0xFF3D3D3D), Color(0xFF1E1E1E)),
                    ),
                ),
        )
        Box(
            Modifier
                .fillMaxSize(0.55f)
                .align(Alignment.CenterEnd)
                .clip(RoundedCornerShape(topStart = 40.dp, bottomStart = 40.dp))
                .background(accent.copy(alpha = 0.85f)),
        )
    }
}
