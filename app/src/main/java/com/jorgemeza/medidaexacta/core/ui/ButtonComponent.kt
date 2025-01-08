package com.jorgemeza.medidaexacta.core.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun ButtonComponent(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = Color.Black,
    onClick: () -> Unit
) {

    TextButton(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.textButtonColors(
            containerColor = color,
        ),
        onClick = {
            onClick()
        }
    ) {
        Text(
            modifier = modifier.fillMaxWidth(),
            color = Color.White,
            textAlign = TextAlign.Center,
            text = text
        )
    }

}