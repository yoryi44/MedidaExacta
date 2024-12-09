package com.jorgemeza.medidaexacta.quotation.ui.detail.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jorgemeza.medidaexacta.quotation.domain.model.DetailModel
import com.jorgemeza.medidaexacta.ui.theme.LigthGray

@Composable
fun ListQuotationDetailComponent(
    modifier: Modifier = Modifier,
    details: List<DetailModel>
) {

    Card(
        modifier = modifier.padding(5.dp), colors = CardColors(
            containerColor = LigthGray,
            contentColor = Color.White,
            disabledContainerColor = Color.White,
            disabledContentColor = Color.White,
        )
    )
    {
        LazyColumn(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 4.dp)
                .clickable(onClick = {

                })
        ) {
            items(details) { detail ->
                ItemQuotationDetailComponent(
                    detail = detail,
                    onClickItem = {},
                    onLongClick = {}
                )
            }
        }
    }

}