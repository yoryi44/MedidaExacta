package com.jorgemeza.medidaexacta.core.ui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.jorgemeza.medidaexacta.R

@Composable
fun AlertDialogComponent(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    icon: ImageVector,
    iconColor: Color = MaterialTheme.colorScheme.primary,
    title: String = "",
    message: String = "",
) {

    AlertDialog(
        containerColor = Color.White,
        textContentColor = Color.Black,
        onDismissRequest = { onDismiss() },
        icon = {
            Icon(icon, contentDescription = "Example Icon", tint = iconColor)
        },
        title = { Text(title, color = Color.Black) },
        text = { Text(message) },
        confirmButton = {
            Button(onClick = { onConfirm() },modifier = modifier) {
                Text(stringResource(R.string.Acept), color = Color.Black)
            }
        },
        dismissButton = {
            Button(onClick = { onDismiss() }) {
                Text(stringResource(R.string.Cancel), color = Color.Black)
            }
        }
    )

}