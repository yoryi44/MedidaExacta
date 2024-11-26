package com.jorgemeza.medidaexacta.quotation.ui

sealed interface QuotationEvent {
    data class OnSearchQueryChange(val query: String) : QuotationEvent
}