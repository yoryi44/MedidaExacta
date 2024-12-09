package com.jorgemeza.medidaexacta.quotation.ui.detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jorgemeza.medidaexacta.core.ext.toPrice
import com.jorgemeza.medidaexacta.quotation.domain.model.DetailModel
import com.jorgemeza.medidaexacta.ui.theme.Danger
import com.jorgemeza.medidaexacta.ui.theme.Success
import com.jorgemeza.medidaexacta.ui.theme.Warning

@Composable
fun ItemQuotationDetailComponent(
    modifier: Modifier = Modifier,
    detail: DetailModel,
    onClickItem: (String) -> Unit,
    onLongClick: () -> Unit
) {

    Card(
        modifier = modifier.padding(5.dp).background(Color.White).fillMaxSize().pointerInput(Unit) {
            detectTapGestures(
                onLongPress = {
                    onLongClick()
                },
                onTap = {
                    onClickItem(detail.product)
                }
            )
        }, colors = CardColors(
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
                tint = Success
            )
            Column(modifier = Modifier.weight(1f)) {

                Text(
                    text = detail.product,
                    modifier = Modifier.padding(horizontal = 10.dp),
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                )
                Text(
                    text = detail.price.toPrice(),
                    modifier = Modifier.padding(horizontal = 10.dp),
                    color = Color.Black
                )
                Text(
                    text = detail.amount,
                    modifier = Modifier.padding(horizontal = 10.dp),
                    color = Color.Black
                )
            }
        }
    }

}