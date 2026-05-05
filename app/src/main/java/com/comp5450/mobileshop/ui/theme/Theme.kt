package com.comp5450.mobileshop.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColors = lightColorScheme(
    primary = AccentOrange,
    onPrimary = androidx.compose.ui.graphics.Color.White,
    primaryContainer = AccentOrange.copy(alpha = 0.2f),
    secondary = DarkSurface,
    onSecondary = androidx.compose.ui.graphics.Color.White,
    surface = androidx.compose.ui.graphics.Color.White,
    onSurface = DarkSurface,
    background = androidx.compose.ui.graphics.Color.White,
    onBackground = DarkSurface,
)

private val DarkColors = darkColorScheme(
    primary = AccentOrange,
    onPrimary = androidx.compose.ui.graphics.Color.White,
    secondary = androidx.compose.ui.graphics.Color.White,
    onSecondary = DarkSurface,
    surface = DarkSurface,
    onSurface = androidx.compose.ui.graphics.Color.White,
)

@Composable
fun MobileShopTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val scheme = if (darkTheme) DarkColors else LightColors
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = scheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }
    MaterialTheme(
        colorScheme = scheme,
        typography = Typography,
        content = content,
    )
}
