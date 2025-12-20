package org.example.mycartcalculator.view

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AppTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = AppTypography,
        shapes = AppShapes,
        content = content
    )
}

val PrimaryBlue = Color(0xFF137FEC)
val PrimaryBlueDark = Color(0xFF0E63B8)

val BackgroundLight = Color(0xFFF9FAFB)
val SurfaceLight = Color(0xFFFFFFFF)

val TextPrimary = Color(0xFF1F2937)   // gris oscuro
val TextSecondary = Color(0xFF6B7280) // gris medio
val DividerColor = Color(0xFFE5E7EB)

val SuccessGreen = Color(0xFF16A34A)
val ErrorRed = Color(0xFFDC2626)

val LightColorScheme = lightColorScheme(
    primary = PrimaryBlue,
    onPrimary = Color.White,

    primaryContainer = PrimaryBlue.copy(alpha = 0.12f),
    onPrimaryContainer = PrimaryBlueDark,

    secondary = TextSecondary,
    onSecondary = Color.White,

    background = BackgroundLight,
    onBackground = TextPrimary,

    surface = SurfaceLight,
    onSurface = TextPrimary,

    surfaceVariant = DividerColor,
    onSurfaceVariant = TextSecondary,

    error = ErrorRed,
    onError = Color.White
)


val AppShapes = Shapes(
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(12.dp),
    large = RoundedCornerShape(16.dp)
)

val AppTypography = Typography(
    titleLarge = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.SemiBold,
        color = TextPrimary
    ),
    bodyLarge = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        color = TextPrimary
    ),
    bodyMedium = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        color = TextSecondary
    ),
    labelLarge = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium
    )
)
