package com.jorgemeza.medidaexacta.quotation.ui.list

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jorgemeza.medidaexacta.client.ui.list.ClientEvent
import com.jorgemeza.medidaexacta.core.ui.CircularProgresIndicatorComponent
import com.jorgemeza.medidaexacta.core.ui.FloatingActionButtonComponent
import com.jorgemeza.medidaexacta.core.ui.TopBarComponent
import com.jorgemeza.medidaexacta.quotation.ui.list.components.QuotationItemComponent
import com.jorgemeza.medidaexacta.ui.theme.LigthGray

@Composable
fun QuotationScreen(
    quotationViewModel: QuotationViewModel = hiltViewModel(),
    onDetail: (String?) -> Unit
) {

    val state = quotationViewModel.state

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
                TopBarComponent(value = state.searchQuery,
                    onChange = {
                        quotationViewModel.onEvent(QuotationEvent.OnSearchQueryChange(it))
                    },
                    onSearch = {

                    }
                )

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
                        items(state.quotations) { item ->
                            QuotationItemComponent(
                                quotation = item,
                                onClickItem = {
                                    onDetail(it)
                                },
                                onLongClick = {
                                    quotationViewModel.onEvent(QuotationEvent.OnDeleteClient(item.id))
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}