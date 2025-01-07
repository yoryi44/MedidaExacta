package com.jorgemeza.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun AlertDialogComponent(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconColor: Color = MaterialTheme.colorScheme.primary,
    title: String = "",
    message: String = "",
) {

    AlertDialog(
        containerColor = Color.Companion.White,
        textContentColor = Color.Companion.Black,
        onDismissRequest = { onDismiss() },
        icon = {
            Icon(
                icon,
                contentDescription = "Example Icon",
                tint = iconColor
            )
        },
        title = {
            Text(
                title,
                color = Color.Companion.Black
            )
        },
        text = { Text(message) },
        confirmButton = {
            Button(onClick = { onConfirm() }, modifier = modifier) {
                Text(
                    "Acept",
                    color = Color.Companion.Black
                )
            }
        },
        dismissButton = {
            Button(onClick = { onDismiss() }) {
                Text(
                    "Cancel",
                    color = Color.Companion.Black
                )
            }
        }
    )

}