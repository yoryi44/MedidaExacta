package com.jorgemeza.medidaexacta.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun TopBarComponent(
    modifier: Modifier = Modifier,
    value: String,
    onChange: (String) -> Unit,
    onSearch: () -> Unit
) {

    val focusManager = LocalFocusManager.current

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
                .padding(horizontal = 4.dp),
            placeholder = "Search",
            keyboardActions = KeyboardActions(onAny = {
                onSearch()
                focusManager.clearFocus()
            }),
            keyboardOptions = KeyboardOptions(
                autoCorrectEnabled = false,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            )
        ) {
            onChange(it)
        }
    }
}