package com.jorgemeza.medidaexacta.invoice.ui.list

sealed interface InvoiceEvent {
    data class OnSearchQueryChange(val query: String) : InvoiceEvent
}