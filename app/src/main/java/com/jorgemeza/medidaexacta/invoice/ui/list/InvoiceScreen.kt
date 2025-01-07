package com.jorgemeza.medidaexacta.invoice.ui.list

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
import com.jorgemeza.ui.components.FloatingActionButtonComponent
import com.jorgemeza.ui.components.TopBarComponent
import com.jorgemeza.medidaexacta.invoice.ui.list.components.InvoiceItemComponent
import com.jorgemeza.medidaexacta.ui.theme.LigthGray

@Composable
fun InvoiceScreen(
    invoiceViewModel: InvoiceViewModel = hiltViewModel(),
    onDetail: (String?) -> Unit
) {

    val state = invoiceViewModel.state

    Scaffold(
        floatingActionButton = {
            com.jorgemeza.ui.components.FloatingActionButtonComponent {

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
                com.jorgemeza.ui.components.TopBarComponent(value = state.searchQuery,
                    onChange = {
                        invoiceViewModel.onEvent(InvoiceEvent.OnSearchQueryChange(it))
                    },
                    onSearch = {

                    }
                )

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(state.invoices) { item ->
                        InvoiceItemComponent(invoice = item,
                            onClickItem = {onDetail(item.id)},
                            onLongClick = {}
                        )
                    }
                }
            }
        }
    }
}