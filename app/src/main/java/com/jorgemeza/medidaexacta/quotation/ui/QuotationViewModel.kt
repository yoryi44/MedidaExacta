package com.jorgemeza.medidaexacta.quotation.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class QuotationViewModel @Inject constructor() : ViewModel() {

    var state by mutableStateOf(QuotationState())
        private set

    fun onEvent(event: QuotationEvent) {
        when (event) {
            is QuotationEvent.OnSearchQueryChange -> {
                state = state.copy(searchQuery = event.query)
            }
        }
    }
}