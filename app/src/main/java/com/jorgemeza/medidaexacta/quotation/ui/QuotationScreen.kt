package com.jorgemeza.medidaexacta.quotation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jorgemeza.medidaexacta.core.ui.TopBarComponent
import com.jorgemeza.medidaexacta.quotation.ui.components.QuotationItemComponent
import com.jorgemeza.medidaexacta.ui.theme.CoffeeYellow40
import com.jorgemeza.medidaexacta.ui.theme.CoffeeYellow80

@Composable
fun QuotationScreen(
    quotationViewModel: QuotationViewModel = hiltViewModel()
) {

    val state = quotationViewModel.state

    val items = List(10) { "Elemento #$it" }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {},
                containerColor = CoffeeYellow40,
                shape = CircleShape
            ) {
                Icon(
                    Icons.Outlined.Add,
                    contentDescription = "add new payment reminder",
                    tint = Color.White
                )
            }
        }
    ) { innerPading ->
        Column(modifier = Modifier.padding().fillMaxSize()
            .background(CoffeeYellow80.copy(alpha = 0.5f))
            .fillMaxSize()) {
            TopBarComponent(value = state.searchQuery)
            {
                quotationViewModel.onEvent(QuotationEvent.OnSearchQueryChange(it))
            }
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(items) { item ->
                    QuotationItemComponent(text = item)
                }
            }
        }
    }
}