package com.jorgemeza.medidaexacta.quotation.ui.list

import com.jorgemeza.medidaexacta.client.ui.list.ClientEvent

sealed interface QuotationEvent {
    data class OnSearchQueryChange(val query: String) : QuotationEvent
    data class OnDeleteClient(val id: String) : QuotationEvent
}