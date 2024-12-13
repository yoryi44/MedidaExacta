package com.jorgemeza.medidaexacta.invoice.ui.detail

sealed interface InvoiceDetailEvent {
    object OnPdf : InvoiceDetailEvent
    object OnDismissDialog : InvoiceDetailEvent
}