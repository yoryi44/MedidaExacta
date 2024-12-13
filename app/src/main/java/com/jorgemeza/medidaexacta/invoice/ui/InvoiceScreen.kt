package com.jorgemeza.medidaexacta.invoice.ui

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
import com.jorgemeza.medidaexacta.core.ui.FloatingActionButtonComponent
import com.jorgemeza.medidaexacta.core.ui.TopBarComponent
import com.jorgemeza.medidaexacta.invoice.ui.components.InvoiceItemComponent
import com.jorgemeza.medidaexacta.ui.theme.LigthGray
import com.jorgemeza.medidaexacta.ui.theme.WarmGray

@Composable
fun InvoiceScreen(
    invoiceViewModel: InvoiceViewModel = hiltViewModel()
) {

    val state = invoiceViewModel.state

    val items = List(10) { "Elemento #$it" }

    Scaffold(
        floatingActionButton = {
            FloatingActionButtonComponent {

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
                        invoiceViewModel.onEvent(InvoiceEvent.OnSearchQueryChange(it))
                    },
                    onSearch = {

                    }
                )

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(items) { item ->
                        InvoiceItemComponent(text = item)
                    }
                }
            }
        }
    }
}