package com.jorgemeza.medidaexacta.quotation.ui.detail

sealed interface QuotationDetailEvent {
    data class OnClientChange(val client: String) : QuotationDetailEvent
    object OnSave : QuotationDetailEvent
}