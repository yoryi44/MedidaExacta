package com.jorgemeza.medidaexacta.quotation.ui.detail

sealed interface QuotationDetailEvent {
    data class OnClientChange(val client: String) : QuotationDetailEvent
    object OnSave : QuotationDetailEvent
    object OnPdf : QuotationDetailEvent
    object OnInvoice : QuotationDetailEvent
    object OnSaveInvoice : QuotationDetailEvent
    object OnDismissDialog : QuotationDetailEvent
}