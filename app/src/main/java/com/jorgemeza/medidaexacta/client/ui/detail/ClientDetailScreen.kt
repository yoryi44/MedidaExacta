package com.jorgemeza.medidaexacta.client.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jorgemeza.ui.components.ButtonComponent
import com.jorgemeza.ui.components.CircularProgresIndicatorComponent
import com.jorgemeza.ui.components.TextFieldComponent

@Composable
fun ClientDetailScreen(
    clientId: String?,
    clientDetailViewModel: ClientDetailViewModel = hiltViewModel(),
    onSaved: () -> Unit
) {

    val state = clientDetailViewModel.state

    val focusManager = LocalFocusManager.current

    LaunchedEffect(clientId) {
        if (clientId != null) {
            clientDetailViewModel.getClientById(clientId)
        }
    }

    LaunchedEffect(state.isSaveSuccessful) {
        if (state.isSaveSuccessful) {
            onSaved()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
    ) {

        if (state.isLoading) {
            com.jorgemeza.ui.components.CircularProgresIndicatorComponent()
        } else {
            com.jorgemeza.ui.components.TextFieldComponent(
                value = state.name,
                leadingIcon = Icons.Default.AccountBox,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp, horizontal = 8.dp),
                label = "name",
                keyboardOptions = KeyboardOptions(
                    autoCorrect = false,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onAny = {
                    focusManager.moveFocus(
                        FocusDirection.Next
                    )
                }),
            ) {
                clientDetailViewModel.onEvent(ClientDetailEvent.OnNameChange(it))
            }

            com.jorgemeza.ui.components.TextFieldComponent(
                value = state.address,
                leadingIcon = Icons.Default.Home,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp, horizontal = 8.dp),
                label = "address",
                keyboardOptions = KeyboardOptions(
                    autoCorrect = false,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onAny = {
                    focusManager.moveFocus(
                        FocusDirection.Next
                    )
                }),
            ) {
                clientDetailViewModel.onEvent(ClientDetailEvent.OnAddressChange(it))
            }

            com.jorgemeza.ui.components.TextFieldComponent(
                value = state.phone,
                leadingIcon = Icons.Default.Phone,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp, horizontal = 8.dp),
                label = "phone",
                keyboardOptions = KeyboardOptions(
                    autoCorrect = false,
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onAny = {
                    focusManager.moveFocus(
                        FocusDirection.Next
                    )
                }),
            ) {
                clientDetailViewModel.onEvent(ClientDetailEvent.OnPhoneChange(it))
            }

            com.jorgemeza.ui.components.TextFieldComponent(
                value = state.email ?: "",
                leadingIcon = Icons.Default.Email,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp, horizontal = 8.dp),
                label = "email",
                keyboardOptions = KeyboardOptions(
                    autoCorrect = false,
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onAny = {
                    focusManager.moveFocus(
                        FocusDirection.Next
                    )
                }),
            ) {
                clientDetailViewModel.onEvent(ClientDetailEvent.OnEmailChange(it))
            }

            com.jorgemeza.ui.components.TextFieldComponent(
                value = state.postalCode,
                leadingIcon = Icons.Default.Home,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp, horizontal = 8.dp),
                label = "postal code",
                keyboardOptions = KeyboardOptions(
                    autoCorrect = false,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onAny = {
                    focusManager.moveFocus(
                        FocusDirection.Next
                    )
                }),
            ) {
                clientDetailViewModel.onEvent(ClientDetailEvent.OnPostalCodeChange(it))
            }

            com.jorgemeza.ui.components.TextFieldComponent(
                value = state.cif ?: "",
                leadingIcon = Icons.Default.Edit,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp, horizontal = 8.dp),
                label = "cif",
                keyboardOptions = KeyboardOptions(
                    autoCorrect = false,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onAny = {
                    focusManager.clearFocus()
                }),
            ) {
                clientDetailViewModel.onEvent(ClientDetailEvent.OnCifChange(it))
            }

            Spacer(Modifier.weight(1f))

            com.jorgemeza.ui.components.ButtonComponent(modifier = Modifier, text = "Save") {
                clientDetailViewModel.onEvent(ClientDetailEvent.OnSaveClient)
            }
        }

    }

}