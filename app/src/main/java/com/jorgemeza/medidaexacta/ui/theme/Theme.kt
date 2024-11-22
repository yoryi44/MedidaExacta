package com.jorgemeza.medidaexacta.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
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
    primary = Coffee80,       // Beige claro para elementos principales
    secondary = CoffeeGrey80, // Gris beige suave para áreas secundarias
    tertiary = CoffeeYellow80, // Amarillo cálido claro como acento
    background = Color(0xFFD7C29F),  // Beige cálido claro (nuevo color de fondo)
    surface = Color(0xFFE8D5A2),     // Beige más claro para superficies
    onPrimary = Color(0xFF3D2F2A),   // Negro suave con matices marrones para texto sobre fondo principal
    onSecondary = Color(0xFF3D2F2A), // Negro suave con matices marrones para texto en áreas secundarias
    onTertiary = Color(0xFF3D2F2A),  // Negro suave con matices marrones para texto en acentos
    onBackground = CoffeeGrey40,     // Gris cálido oscuro para contraste en fondo claro
    onSurface = CoffeeGrey40         // Gris cálido oscuro para texto en superficies
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