package com.jorgemeza.medidaexacta.core.ui

import android.media.Image
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun AlertDialogComponent(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    icon: ImageVector,
    title: String = "",
    message: String = "",
) {

    AlertDialog(
        containerColor = Color.White,
        textContentColor = Color.Black,
        onDismissRequest = { onDismiss() },
        icon = {
            Icon(icon, contentDescription = "Example Icon")
        },
        title = { Text(message, color = Color.Black) },
        text = { Text(message) },
        confirmButton = {
            Button(onClick = { onConfirm() },modifier = modifier) {
                Text("Acept", color = Color.Black)
            }
        },
        dismissButton = {
            Button(onClick = { onDismiss() }) {
                Text("Cancel", color = Color.Black)
            }
        }
    )

}