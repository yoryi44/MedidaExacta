package com.jorgemeza.medidaexacta.invoice.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Warning
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
import com.jorgemeza.medidaexacta.core.ui.AlertDialogComponent
import com.jorgemeza.medidaexacta.core.ui.CircularProgresIndicatorComponent
import com.jorgemeza.medidaexacta.core.ui.SelectComponent
import com.jorgemeza.medidaexacta.core.ui.TextFieldComponent
import com.jorgemeza.medidaexacta.quotation.ui.detail.QuotationDetailEvent
import com.jorgemeza.medidaexacta.quotation.ui.detail.components.ListQuotationDetailComponent
import com.jorgemeza.medidaexacta.ui.theme.Danger

@Composable
fun InvoiceDetailScreen(
    quotationId: String?,
    invoiceDetailViewModel: InvoiceDetailViewModel = hiltViewModel()
) {

    val state = invoiceDetailViewModel.state

    val focusManager = LocalFocusManager.current

    LaunchedEffect(quotationId) {
        if (quotationId != null)
            invoiceDetailViewModel.getInvoiceById(quotationId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
    ) {

        if (!state.error.isNullOrBlank()) {
            AlertDialogComponent(
                icon = Icons.Default.Warning,
                title = "Error",
                message = state.error,
                iconColor = Danger,
                onDismiss = {
                    invoiceDetailViewModel.onEvent(InvoiceDetailEvent.OnDismissDialog)
                },
                onConfirm = {
                    invoiceDetailViewModel.onEvent(InvoiceDetailEvent.OnDismissDialog)
                }
            )
        }

        if (state.isLoading) {
            CircularProgresIndicatorComponent()
        } else {
            TextFieldComponent(
                enabled = false,
                value = state.quotationNumber,
                leadingIcon = Icons.Default.Info,
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
                leadingIcon = Icons.Default.DateRange,
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
                enabled = false,
                value = state.price,
                leadingIcon = Icons.Default.ShoppingCart,
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

            SelectComponent(
                options = state.clients.map { it.name },
                selected = state.client,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp, horizontal = 16.dp),
            )
            {
//                quotationDetailViewModel.onEvent(QuotationDetailEvent.OnClientChange(it))
            }

            ListQuotationDetailComponent(modifier = Modifier.weight(1f), state.products)

        }

    }

}