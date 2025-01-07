package com.jorgemeza.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun TextFieldComponent(
    modifier: Modifier = Modifier,
    isPassword: Boolean = false,
    label: String? = null,
    placeholder: String? = null,
    value: String,
    keyboardType: KeyboardType = KeyboardType.Companion.Text,
    errorMessaje : String? = null,
    leadingIcon: ImageVector?,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    keyboardActions: KeyboardActions = KeyboardActions(),
    enabled: Boolean = true,
    onValueChange: (String) -> Unit
) {
    var passwordVisibility by remember {
        mutableStateOf(
            false
        )
    }

    val passwordVisibilityIcon: ImageVector = if (passwordVisibility) {
        Icons.Default.Clear
    } else {
        Icons.Default.Check
    }

    Column(
        modifier = modifier
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = { onValueChange(it) },
            modifier = modifier,
            enabled = enabled,
            label = { if (!label.isNullOrBlank()) Text(label) },
            placeholder = {
                if (isPassword) Text("********") else if (!placeholder.isNullOrBlank()) Text(
                    placeholder
                )
            },
            leadingIcon = {
                if (leadingIcon != null) Icon(
                    imageVector = leadingIcon,
                    contentDescription = "leadingIcon"
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            visualTransformation = if (!passwordVisibility && isPassword) PasswordVisualTransformation() else VisualTransformation.Companion.None,
            keyboardActions = keyboardActions,
            trailingIcon = {
                if (isPassword) {
                    androidx.compose.material3.IconButton(onClick = {
                        passwordVisibility = !passwordVisibility
                    }) {
                        Icon(
                            passwordVisibilityIcon,
                            contentDescription = null
                        )
                    }
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Companion.Black,
                unfocusedTextColor = Color.Companion.Gray,
                focusedLabelColor = Color.Companion.Black,
                unfocusedLabelColor = Color.Gray.copy(alpha = 0.5f),
                focusedBorderColor = Color.Companion.Black,
                unfocusedBorderColor = Color.Companion.Gray,
                focusedContainerColor = Color.Companion.White,
                unfocusedContainerColor = Color.Companion.White,
                disabledLabelColor = Color.Companion.Gray,
            ),
        )
        if (errorMessaje != null) {
            Text(
                text = errorMessaje,
                color = Color.Companion.Red
            )
        }

    }
}