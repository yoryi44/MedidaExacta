package com.jorgemeza.medidaexacta.quotation.ui.list.components

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jorgemeza.medidaexacta.core.ext.toPrice
import com.jorgemeza.medidaexacta.quotation.domain.model.QuotationModel

@Composable
fun QuotationItemComponent(
    modifier: Modifier = Modifier,
    quotation: QuotationModel,
    onClickItem: (String) -> Unit,
    onLongClick: () -> Unit
) {

    Card(
        modifier = modifier.padding(5.dp).pointerInput(Unit) {
            detectTapGestures(
                onLongPress = {
                    onLongClick()
                },
                onTap = {
                    onClickItem(quotation.id)
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
                Icons.Default.DateRange,
                contentDescription = "",
                Modifier.size(80.dp),
                tint = Color.Black
            )
            Column {

                Text(
                    text = quotation.date,
                    modifier = Modifier.padding(horizontal = 10.dp),
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = quotation.client,
                    modifier = Modifier.padding(horizontal = 10.dp),
                    color = Color.Black
                )
                Text(
                    text = quotation.price.toPrice(),
                    modifier = Modifier.padding(horizontal = 10.dp),
                    color = Color.Black
                )
            }
        }
    }

}