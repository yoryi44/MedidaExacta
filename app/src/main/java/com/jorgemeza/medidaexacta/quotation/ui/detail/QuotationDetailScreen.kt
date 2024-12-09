package com.jorgemeza.medidaexacta.quotation.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ShoppingCart
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
import com.jorgemeza.medidaexacta.core.ui.ButtonComponent
import com.jorgemeza.medidaexacta.core.ui.CircularProgresIndicatorComponent
import com.jorgemeza.medidaexacta.core.ui.TextFieldComponent
import com.jorgemeza.medidaexacta.quotation.ui.detail.components.ListQuotationDetailComponent
import com.jorgemeza.medidaexacta.ui.theme.Danger
import com.jorgemeza.medidaexacta.ui.theme.Success

@Composable
fun QuotationDetailScreen(
    quotationId: String?,
    quotationDetailViewModel: QuotationDetailViewModel = hiltViewModel(),
    onSaved: () -> Unit,
    onShoppingCar: (id: String?) -> Unit
) {

    val state = quotationDetailViewModel.state

    val focusManager = LocalFocusManager.current

    LaunchedEffect(quotationId) {
        if (quotationId != null) {
            quotationDetailViewModel.getQuotationById(quotationId)
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
            CircularProgresIndicatorComponent()
        } else {
            TextFieldComponent(
                enabled = false,
                value = state.quotationNumber,
                leadingIcon = Icons.Default.DateRange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp, horizontal = 8.dp),
                label = "Quotation Number",
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
            ) {}

            TextFieldComponent(
                enabled = false,
                value = state.date,
                leadingIcon = Icons.Default.AccountBox,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp, horizontal = 8.dp),
                label = "Date",
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
            ) {}

            TextFieldComponent(
                value = state.client,
                leadingIcon = Icons.Default.ShoppingCart,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp, horizontal = 8.dp),
                label = "Client",
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
                quotationDetailViewModel.onEvent(QuotationDetailEvent.OnClientChange(it))
            }

            TextFieldComponent(
                enabled = false,
                value = state.price,
                leadingIcon = Icons.Default.Edit,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp, horizontal = 8.dp),
                label = "Price",
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
            ) {}

            ListQuotationDetailComponent(modifier = Modifier.weight(1f), state.products)

            Row {

                ButtonComponent(modifier = Modifier.weight(1f), text = "Save") {
                    quotationDetailViewModel.onEvent(QuotationDetailEvent.OnSave)
                }

                ButtonComponent(modifier = Modifier.weight(1f), text = "Print", color = Danger) {
                    onShoppingCar(state.id)
                }

                ButtonComponent(modifier = Modifier.weight(1f), text = "Shop", color = Success) {
                    onShoppingCar(state.id)
                }
            }
        }

    }

}