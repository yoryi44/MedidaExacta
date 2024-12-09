package com.jorgemeza.medidaexacta.quotation.ui.shoppingCar.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jorgemeza.medidaexacta.core.ui.CircularProgresIndicatorComponent
import com.jorgemeza.medidaexacta.core.ui.FloatingActionButtonComponent
import com.jorgemeza.medidaexacta.quotation.ui.shoppingCar.list.components.ShoppingCardItemComponent
import com.jorgemeza.medidaexacta.ui.theme.LigthGray

@Composable
fun ShoppingCarScreen(
    quotationId: String?,
    shoppingCarViewModel: ShoppingCarViewModel = hiltViewModel(),
    onSaved: () -> Unit,
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
            FloatingActionButtonComponent {
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

                if(state.isLoading)
                {
                    CircularProgresIndicatorComponent()
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
//                                    shoppingCarViewModel.onEvent(QuotationEvent.OnDeleteClient(item.id))
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}