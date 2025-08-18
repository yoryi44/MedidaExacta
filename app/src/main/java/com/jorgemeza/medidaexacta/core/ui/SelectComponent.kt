package com.jorgemeza.medidaexacta.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jorgemeza.medidaexacta.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectComponent(
    modifier: Modifier = Modifier,
    selected: String? = null,
    options: List<String>,
    onSelected: (String) -> Unit
) {

    var selectedItem by remember { mutableStateOf(selected ?: options.last()) }
    var expanded by remember { mutableStateOf(false) }


    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
    ) {
        OutlinedTextField(
            value = selectedItem,
            onValueChange = {},
            readOnly = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedLabelColor = Color.Black,
                unfocusedLabelColor = Color.Gray,
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Gray,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedTrailingIconColor = Color.DarkGray,
                unfocusedTrailingIconColor = Color.DarkGray,
                disabledLabelColor = Color.Gray,
                disabledTextColor = Color.Gray,
                disabledBorderColor = Color.Gray,
                disabledTrailingIconColor = Color.Black,
            ),
            label = { Text(stringResource(R.string.Choose_an_option)) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = modifier.menuAnchor().fillMaxWidth()
        )
        ExposedDropdownMenu(
            modifier = Modifier.background(Color.White),
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { selectionOption ->
                HorizontalDivider(modifier = Modifier.height(2.dp))
                DropdownMenuItem(
                    text = { Text(selectionOption, color = Color.Black) },
                    onClick = {
                        onSelected(selectionOption)
                        selectedItem = selectionOption
                        expanded = false
                    },
                )
            }
        }
    }
}
