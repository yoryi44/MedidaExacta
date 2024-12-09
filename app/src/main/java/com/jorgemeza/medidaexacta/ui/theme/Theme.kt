package com.jorgemeza.medidaexacta.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

//private val DarkColorScheme = darkColorScheme(
//    primary = Purple80,
//    secondary = PurpleGrey80,
//    tertiary = Pink80
//)

private val LightColorScheme = lightColorScheme(
    primary = LightGray,       // Beige claro para elementos principales
    secondary = NeutralGray, // Gris beige suave para áreas secundarias
    tertiary = WarmGray, // Amarillo cálido claro como acento
    background = Color.Black,  // Beige cálido claro convertido a gris más oscuro
    surface = Color(0xFFC5C5C5),     // Beige más claro convertido a gris oscuro
    onPrimary = Color(0xFF1A1A1A),   // Negro suave con matices marrones convertido a un gris casi negro
    onSecondary = Color(0xFF1A1A1A), // Negro suave con matices marrones convertido a un gris casi negro
    onTertiary = Color(0xFF1A1A1A),  // Negro suave con matices marrones convertido a un gris casi negro
    onBackground = Color(0xFF333333), // Gris cálido oscuro convertido a gris más cercano al negro
    onSurface = Color(0xFF333333)    // Gris cálido oscuro convertido a gris más cercano al negro
)

@Composable
fun MedidaExactaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

//        darkTheme -> DarkColorScheme
        darkTheme -> LightColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}