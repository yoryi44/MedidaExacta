package com.jorgemeza.medidaexacta.invoice.ui

sealed interface InvoiceEvent {
    data class OnSearchQueryChange(val query: String) : InvoiceEvent
}