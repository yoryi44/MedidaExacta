package com.jorgemeza.medidaexacta.quotation.ui.list

import com.jorgemeza.medidaexacta.client.ui.list.ClientEvent

sealed interface QuotationEvent {
    data class OnSearchQueryChange(val query: String) : QuotationEvent
    data class OnDeleteQuotation(val id: String) : QuotationEvent
    object OnDismissDialog : QuotationEvent
    object OnConfirmDialog : QuotationEvent
}