package com.jorgemeza.medidaexacta.shoppingCar.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
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
import com.jorgemeza.medidaexacta.core.ui.ButtonComponent
import com.jorgemeza.medidaexacta.core.ui.CircularProgresIndicatorComponent
import com.jorgemeza.medidaexacta.core.ui.TextFieldComponent

@Composable
fun ShopingCarDetailScreen(
    detailId: String?,
    quotationId: String?,
    shoppingCarDetailViewModel: ShoppingCarDetailViewModel = hiltViewModel(),
    onSaved: () -> Unit
) {

    val state = shoppingCarDetailViewModel.state

    val focusManager = LocalFocusManager.current

    LaunchedEffect(detailId) {
        if (detailId != null) {
            shoppingCarDetailViewModel.getProductById(detailId)
        }
    }

    LaunchedEffect(quotationId) {
        if(quotationId != null) {
        shoppingCarDetailViewModel.onEvent(ShoppingCarDetailEvent.OnQuotationIdEvent(quotationId))
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
                value = state.product,
                leadingIcon = Icons.Default.ShoppingCart,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp, horizontal = 8.dp),
                label = stringResource(R.string.Product),
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
                shoppingCarDetailViewModel.onEvent(ShoppingCarDetailEvent.OnProductEvent(it))
            }

            TextFieldComponent(
                value = state.price,
                leadingIcon = Icons.Default.ShoppingCart,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp, horizontal = 8.dp),
                label = stringResource(R.string.Price),
                keyboardOptions = KeyboardOptions(
                    autoCorrect = false,
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onAny = {
                    focusManager.moveFocus(
                        FocusDirection.Next
                    )
                }),
            ) {
                shoppingCarDetailViewModel.onEvent(ShoppingCarDetailEvent.OnPriceEvent(it))
            }

            TextFieldComponent(
                value = state.amount,
                leadingIcon = Icons.Default.ShoppingCart,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp, horizontal = 8.dp),
                label = stringResource(R.string.Amount),
                keyboardOptions = KeyboardOptions(
                    autoCorrect = false,
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onAny = {
                    focusManager.clearFocus(true)
                }),
            ) {
                shoppingCarDetailViewModel.onEvent(ShoppingCarDetailEvent.OnAmountEvent(it))
            }

            Spacer(Modifier.weight(1f))

            ButtonComponent(text = "Save") {
                shoppingCarDetailViewModel.onEvent(ShoppingCarDetailEvent.OnSave)
            }
        }

    }

}