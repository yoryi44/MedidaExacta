package com.jorgemeza.medidaexacta.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jorgemeza.medidaexacta.ui.theme.LightGray
import com.jorgemeza.medidaexacta.ui.theme.SoftGray

@Composable
fun TopBarComponent(
    modifier: Modifier = Modifier,
    value: String,
    onChange: (String) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(Color.Black),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextFieldComponent(
            value = value,
            leadingIcon = Icons.Default.Search,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            placeholder = "Search",
        ) {
            onChange(it)
        }
    }
}