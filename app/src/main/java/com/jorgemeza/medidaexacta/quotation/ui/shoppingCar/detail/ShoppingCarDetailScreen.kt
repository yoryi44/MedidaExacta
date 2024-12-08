package com.jorgemeza.medidaexacta.quotation.ui.shoppingCar.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
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
import com.jorgemeza.medidaexacta.core.ext.toPrice
import com.jorgemeza.medidaexacta.core.ui.ButtonComponent
import com.jorgemeza.medidaexacta.core.ui.CircularProgresIndicatorComponent
import com.jorgemeza.medidaexacta.core.ui.TextFieldComponent
import com.jorgemeza.medidaexacta.quotation.ui.detail.QuotationDetailEvent
import com.jorgemeza.medidaexacta.quotation.ui.detail.QuotationDetailViewModel

@Composable
fun ShopingCarDetailScreen(
    detailId: String?,
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
                label = "Product",
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
                value = state.price.toPrice(),
                leadingIcon = Icons.Default.ShoppingCart,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp, horizontal = 8.dp),
                label = "Price",
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
                value = state.amount,
                leadingIcon = Icons.Default.ShoppingCart,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp, horizontal = 8.dp),
                label = "Amount",
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
//                quotationDetailViewModel.onEvent(QuotationDetailEvent.OnClientChange(it))
            }

            ButtonComponent(text = "Save") {
//                quotationDetailViewModel.onEvent(QuotationDetailEvent.OnSave)
            }
        }

    }

}