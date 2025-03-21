package com.jorgemeza.medidaexacta.quotation.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jorgemeza.medidaexacta.R
import com.jorgemeza.medidaexacta.core.ui.AlertDialogComponent
import com.jorgemeza.medidaexacta.core.ui.ButtonComponent
import com.jorgemeza.medidaexacta.core.ui.CircularProgresIndicatorComponent
import com.jorgemeza.medidaexacta.core.ui.SelectComponent
import com.jorgemeza.medidaexacta.core.ui.TextFieldComponent
import com.jorgemeza.medidaexacta.quotation.ui.detail.components.ListQuotationDetailComponent
import com.jorgemeza.medidaexacta.ui.theme.Danger
import com.jorgemeza.medidaexacta.ui.theme.Success
import com.jorgemeza.medidaexacta.ui.theme.Warning

@Composable
fun QuotationDetailScreen(
    quotationId: String?,
    quotationDetailViewModel: QuotationDetailViewModel = hiltViewModel(),
    onSaved: () -> Unit,
    onShoppingCar: (id: String?, quotation: String) -> Unit
) {

    val state = quotationDetailViewModel.state

    val focusManager = LocalFocusManager.current

    LaunchedEffect(quotationId) {
        if (quotationId != null)
            quotationDetailViewModel.getQuotationById(quotationId)
        else
            quotationDetailViewModel.createQuotation()

    }

    LaunchedEffect(state.isSaveSuccessful) {
        if (state.isSaveSuccessful) {
            onSaved()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState()),
    ) {

        if (!state.error.isNullOrBlank()) {
            AlertDialogComponent(
                icon = Icons.Default.Warning,
                title = "Error",
                message = state.error,
                iconColor = Danger,
                onDismiss = {
                    quotationDetailViewModel.onEvent(QuotationDetailEvent.OnDismissDialog)
                },
                onConfirm = {
                    quotationDetailViewModel.onEvent(QuotationDetailEvent.OnDismissDialog)
                }
            )
        }

        if (state.invoice) {
            AlertDialogComponent(
                icon = Icons.Default.Warning,
                title = "Invoice",
                message = "Are you sure about converting this quote into an invoice?",
                iconColor = Warning,
                onDismiss = {
                    quotationDetailViewModel.onEvent(QuotationDetailEvent.OnDismissDialog)
                },
                onConfirm = {
                    quotationDetailViewModel.onEvent(QuotationDetailEvent.OnSaveInvoice)
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
                label = stringResource(R.string.Quotation_Number),
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
                label = stringResource(R.string.Date),
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
                label = stringResource(R.string.Price),
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
                quotationDetailViewModel.onEvent(QuotationDetailEvent.OnClientChange(it))
            }

            TextFieldComponent(
                value = state.observations ?: "",
                leadingIcon = Icons.Default.Edit,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp, horizontal = 8.dp)
                    .height(120.dp),
                label = stringResource(R.string.Observations),
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
                quotationDetailViewModel.onEvent(QuotationDetailEvent.OnObservationChange(it))
            }

            ListQuotationDetailComponent(modifier = Modifier.weight(1f), state.products)

            Row {

                ButtonComponent(modifier = Modifier.weight(1f), text = stringResource(R.string.Save)) {
                    quotationDetailViewModel.onEvent(QuotationDetailEvent.OnSave)
                }

                if (!state.id.isNullOrEmpty()) {

                    if(state.products.isNotEmpty())
                    {
                        ButtonComponent(
                            modifier = Modifier.weight(1f),
                            text = stringResource(R.string.Print),
                            color = Danger
                        ) {
                            quotationDetailViewModel.onEvent(QuotationDetailEvent.OnPdf)
                        }

                        ButtonComponent(
                            modifier = Modifier.weight(1f),
                            text = stringResource(R.string.Payment),
                            color = Danger
                        ) {
                            quotationDetailViewModel.onEvent(QuotationDetailEvent.OnPdfPayment)
                        }

                        ButtonComponent(modifier = Modifier.weight(1f), text = stringResource(R.string.Invoice), color = Warning) {
                            quotationDetailViewModel.onEvent(QuotationDetailEvent.OnInvoice)
                        }

                    }

                    ButtonComponent(modifier = Modifier.weight(1f), text = stringResource(R.string.Shop), color = Success) {
                        onShoppingCar(state.id, state.quotationNumber)
                    }
                }
            }
        }

    }

}