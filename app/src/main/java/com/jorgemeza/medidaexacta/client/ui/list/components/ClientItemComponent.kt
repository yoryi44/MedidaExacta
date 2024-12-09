package com.jorgemeza.medidaexacta.client.ui.list.components

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
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
import com.jorgemeza.medidaexacta.client.domain.model.ClientModel

@Composable
fun ClientItemComponent(
    modifier: Modifier = Modifier,
    client: ClientModel,
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
                    onClickItem(client.id)
                }
            )
        }, colors = CardColors(
            containerColor = Color.White,
            contentColor = Color.White,
            disabledContainerColor = Color.White,
            disabledContentColor = Color.White,
        )
    )
    {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(120.dp)
                .padding(5.dp)
        ) {

            Icon(
                Icons.Default.AccountCircle,
                contentDescription = "",
                Modifier.size(80.dp),
                tint = Color.Black
            )

            Column {
                Text(
                    text = client.name,
                    modifier = Modifier.padding(horizontal = 10.dp),
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = client.address,
                    modifier = Modifier.padding(horizontal = 10.dp),
                    color = Color.Black,
                )
                Text(
                    text = client.phone,
                    modifier = Modifier.padding(horizontal = 10.dp),
                    color = Color.Black,
                )
            }
        }
    }

}