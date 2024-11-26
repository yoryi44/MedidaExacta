package com.jorgemeza.medidaexacta.menu.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jorgemeza.medidaexacta.ui.theme.Coffee40

@Composable
fun MenuItemComponent(
    modifier: Modifier = Modifier,
    item: String,
    onItemSelect: (String) -> Unit
) {

    Card(
        modifier = Modifier
            .size(height = 180.dp, width = 150.dp)
            .padding(16.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = modifier.fillMaxSize().clickable { onItemSelect(item) },
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(Icons.Default.Face, contentDescription = "", modifier = modifier.size(100.dp))
            Text(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .background(Coffee40.copy(alpha = 0.7f)),
                textAlign = TextAlign.Center,
                text = item,
                color = Color.White
            )
        }

    }

}