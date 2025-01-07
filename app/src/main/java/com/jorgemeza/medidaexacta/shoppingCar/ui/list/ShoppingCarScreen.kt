package com.jorgemeza.medidaexacta.shoppingCar.ui.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jorgemeza.ui.components.AlertDialogComponent
import com.jorgemeza.ui.components.CircularProgresIndicatorComponent
import com.jorgemeza.ui.components.FloatingActionButtonComponent
import com.jorgemeza.medidaexacta.shoppingCar.ui.list.components.ShoppingCardItemComponent
import com.jorgemeza.medidaexacta.ui.theme.Danger
import com.jorgemeza.medidaexacta.ui.theme.LigthGray

@Composable
fun ShoppingCarScreen(
    quotationId: String?,
    shoppingCarViewModel: ShoppingCarViewModel = hiltViewModel(),
    onDetail: (id: String?) -> Unit,
) {

    val state = shoppingCarViewModel.state

    LaunchedEffect(key1 = quotationId) {
        if(quotationId != null) {
            shoppingCarViewModel.getQuotationById(quotationId)
        }
    }

    Scaffold(
        floatingActionButton = {
            com.jorgemeza.ui.components.FloatingActionButtonComponent {
                onDetail(null)
            }
        }
    ) { innerPading ->

        Box(modifier = Modifier.fillMaxSize()) {

            Column(
                modifier = Modifier
                    .padding()
                    .fillMaxSize()
                    .background(LigthGray)
                    .fillMaxSize()
            ) {

                if (!state.idDetailDelete.isNullOrBlank()) {
                    com.jorgemeza.ui.components.AlertDialogComponent(
                        icon = Icons.Default.Warning,
                        title = "Are you sure you want to delete the Product?",
                        message = "Once deleted, you will not be able to recover the information.",
                        iconColor = com.jorgemeza.medidaexacta.ui.theme.Warning,
                        onDismiss = {
                            shoppingCarViewModel.onEvent(ShoppingCarEvent.OnDismissDialog)
                        },
                        onConfirm = {
                            shoppingCarViewModel.onEvent(ShoppingCarEvent.OnConfirmDialog)
                        }
                    )
                }

                if(!state.error.isNullOrBlank()) {
                    com.jorgemeza.ui.components.AlertDialogComponent(
                        icon = Icons.Default.Warning,
                        title = "Error",
                        message = state.error,
                        iconColor = Danger,
                        onDismiss = {
                            shoppingCarViewModel.onEvent(ShoppingCarEvent.OnDismissDialog)
                        },
                        onConfirm = {
                            shoppingCarViewModel.onEvent(ShoppingCarEvent.OnDismissDialog)
                        }
                    )
                }

                if(state.isLoading)
                {
                    com.jorgemeza.ui.components.CircularProgresIndicatorComponent()
                }
                else
                {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp)
                    ) {
                        items(state.products) { item ->
                            ShoppingCardItemComponent(
                                detail = item,
                                onClickItem = {
                                    onDetail(it)
                                },
                                onLongClick = {
                                    shoppingCarViewModel.onEvent(ShoppingCarEvent.OnDelete(item.id))
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}