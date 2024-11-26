package com.jorgemeza.medidaexacta.invoice.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jorgemeza.medidaexacta.ui.theme.CoffeeGrey40

@Composable
fun InvoiceItemComponent(
    modifier: Modifier = Modifier,
    text: String
) {

    Card(
        modifier = modifier.padding(5.dp), colors = CardColors(
            containerColor = Color.White,
            contentColor = Color.White,
            disabledContainerColor = Color.White,
            disabledContentColor = Color.White
        )
    )
    {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(5.dp)
        ) {
            Icon(
                Icons.Default.ShoppingCart,
                contentDescription = "",
                Modifier.size(80.dp),
                tint = CoffeeGrey40
            )
            Text(text = text, modifier = Modifier.padding(horizontal = 10.dp), color = Color.Black)
        }
    }

}