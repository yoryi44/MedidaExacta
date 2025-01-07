package com.jorgemeza.ui.components

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Icon
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@androidx.compose.runtime.Composable
fun FloatingActionButtonComponent(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    androidx.compose.material3.FloatingActionButton(
        onClick = { onClick() },
        containerColor = Color.Yellow,
        shape = CircleShape
    ) {
        Icon(
            Icons.Outlined.Add,
            contentDescription = "add new payment reminder",
            tint = Color.Companion.Black
        )
    }
}