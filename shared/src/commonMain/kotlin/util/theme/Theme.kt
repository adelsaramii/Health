package util.theme

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color

private val LightColorPalette = lightColors(
    primary = primary,
    primaryVariant = primaryVariant,
    secondary = secondary,
    secondaryVariant = secondaryVariant,
    background = background,
    surface = surface,
    error = error,
    onPrimary = onPrimary,
    onSecondary = onSecondary,
    onBackground = onBackground,
    onSurface = onSurface,
    onError = onError,
)
private val DarkColorPalette = darkColors(
    primary = primaryDark,
    primaryVariant = primaryVariantDark,
    secondary = secondaryDark,
    secondaryVariant = secondaryVariantDark,
    background = backgroundDark,
    surface = surfaceDark,
    error = errorDark,
    onPrimary = onPrimaryDark,
    onSecondary = onSecondaryDark,
    onBackground = onBackgroundDark,
    onSurface = onSurfaceDark,
    onError = onErrorDark,
)

enum class UiMode {
    Default,
    Dark;

    fun toggle(): UiMode = when (this) {
        Default -> Dark
        Dark -> Default
    }
}

val LocalUiMode = staticCompositionLocalOf<MutableState<UiMode>> {
    error("UiMode not provided")
}

@Composable
fun HealthTheme(content: @Composable () -> Unit) {
    val isSystemDark = isSystemInDarkTheme()
    val uiMode = remember {
        mutableStateOf(if (isSystemDark) UiMode.Dark else UiMode.Default)
    }
    CompositionLocalProvider(
        LocalUiMode provides uiMode,
        LocalSpacing provides Spacing(),
//        LocalTypography provides LocalTypography,
    ) {
        val colors = LightColorPalette
//            when (LocalUiMode.current.value) {
//            UiMode.Default -> LightColorPalette
//            UiMode.Dark -> DarkColorPalette
//        }
        MaterialTheme(
            colors = animate(colors),
            shapes = shapes,
            content = content,
//            typography = getTypography(),
        )
    }
}

@Composable
private fun animate(colors: Colors): Colors {
    val animSpec = remember {
        spring<Color>(stiffness = 500f)
    }

    @Composable
    fun animateColor(color: Color): Color =
        animateColorAsState(targetValue = color, animationSpec = animSpec).value

    return Colors(
        primary = animateColor(colors.primary),
        primaryVariant = animateColor(colors.primaryVariant),
        secondary = animateColor(colors.secondary),
        secondaryVariant = animateColor(colors.secondaryVariant),
        background = animateColor(colors.background),
        surface = animateColor(colors.surface),
        error = animateColor(colors.error),
        onPrimary = animateColor(colors.onPrimary),
        onSecondary = animateColor(colors.onSecondary),
        onBackground = animateColor(colors.onBackground),
        onSurface = animateColor(colors.onSurface),
        onError = animateColor(colors.onError),
        isLight = colors.isLight,
    )
}

