package com.jorgemeza.medidaexacta.core.ui

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.jorgemeza.medidaexacta.ui.theme.DarkGray

@Composable
fun FloatingActionButtonComponent(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    FloatingActionButton(
        onClick = { onClick() },
        containerColor = Color.Black,
        shape = CircleShape
    ) {
        Icon(
            Icons.Outlined.Add,
            contentDescription = "add new payment reminder",
            tint = Color.White
        )
    }
}