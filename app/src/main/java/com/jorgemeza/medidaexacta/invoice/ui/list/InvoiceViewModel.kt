package com.jorgemeza.medidaexacta.invoice.ui.list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgemeza.medidaexacta.invoice.domain.usecase.GetAllInvoiceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InvoiceViewModel @Inject constructor(
    private val getAllInvoiceUseCase: GetAllInvoiceUseCase
) : ViewModel() {

    var state by mutableStateOf(InvoiceState())
        private set

    private var invoiceJob: Job? = null

    init {
        getAllInvoice()
    }

    fun onEvent(event: InvoiceEvent) {
        when (event) {
            is InvoiceEvent.OnSearchQueryChange -> {
                state = state.copy(searchQuery = event.query)
            }
        }
    }

    private fun getAllInvoice() {
        invoiceJob?.cancel()
        invoiceJob = viewModelScope.launch {
            state = state.copy(
                isLoading = true
            )

            getAllInvoiceUseCase().collect { invoices ->
                state = state.copy(
                    invoices = invoices,
                    isLoading = false
                )
            }
        }
    }

}