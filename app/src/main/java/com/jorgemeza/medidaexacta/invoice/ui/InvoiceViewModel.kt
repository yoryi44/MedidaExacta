package com.jorgemeza.medidaexacta.invoice.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class InvoiceViewModel @Inject constructor() : ViewModel() {

    var state by mutableStateOf(InvoiceState())
        private set

    fun onEvent(event: InvoiceEvent) {
        when (event) {
            is InvoiceEvent.OnSearchQueryChange -> {
                state = state.copy(searchQuery = event.query)
            }
        }
    }
}